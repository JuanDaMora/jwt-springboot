package judamov.sipoh.service.impl;

import judamov.sipoh.dto.*;
import judamov.sipoh.entity.Role;
import judamov.sipoh.entity.TypeDocument;
import judamov.sipoh.entity.User;
import judamov.sipoh.entity.UserRol;
import judamov.sipoh.exceptions.GenericAppException;
import judamov.sipoh.mappers.UserMapper;
import judamov.sipoh.repository.IRoleRepository;
import judamov.sipoh.repository.ITypeDocumentRepository;
import judamov.sipoh.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final IUserRepository userRepository;
    private final ITypeDocumentRepository typeDocumentRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtServiceImpl;


    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::userToUserDTO) // usar el mapper aquí
                .collect(Collectors.toList());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findOneByDocumento(request.getDocumento())
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con documento: " + request.getDocumento()));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getDocumento(), request.getPassword()));
        } catch (Exception e) {
            throw new GenericAppException(HttpStatus.UNAUTHORIZED, "La contraseña ingresada es incorrecta");
        }
        Boolean forcePasswordReset;
        String token = jwtServiceImpl.getToken(user);
        if(user.getTokenHash()==null){
            forcePasswordReset= true;
        }else{
            forcePasswordReset = false;
            String tokenHash = jwtServiceImpl.hashToken(token);

            user.setTokenHash(tokenHash);
            userRepository.save(user);
        }

        return AuthResponse.builder()
                .token(token)
                .forcePasswordReset(forcePasswordReset)
                .build();
    }

    public RegisterResponse register(RegisterRequest request) {
        TypeDocument typeDocument = typeDocumentRepository.findOneById(request.getIdTipoDocumento())
                .orElseThrow(() -> new GenericAppException(HttpStatus.BAD_REQUEST,
                        "Tipo de documento no encontrado con id: " + request.getIdTipoDocumento()));

        userRepository.findOneByDocumento(request.getDocumento()).ifPresent(u -> {
            throw new GenericAppException(HttpStatus.BAD_REQUEST,
                    "Ya existe un usuario con el documento: " + request.getDocumento());
        });

        userRepository.findOneByEmail(request.getEmail()).ifPresent(u -> {
            throw new GenericAppException(HttpStatus.BAD_REQUEST,
                    "Ya existe un usuario con el correo: " + request.getEmail());
        });

        User user = User.builder()
                .documento(request.getDocumento())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .typeDocument(typeDocument)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .active(true)
                .build();

        List<UserRol> userRoles = request.getIdsRoles().stream().map(roleId -> {
            Role role = roleRepository.findOneById(roleId)
                    .orElseThrow(() -> new GenericAppException(HttpStatus.BAD_REQUEST,
                            "Rol no encontrado con id: " + roleId));
            return new UserRol(null, user, role, null, null);
        }).collect(Collectors.toList());

        user.setUserRoles(userRoles);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new GenericAppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al guardar el usuario");
        }

        return RegisterResponse.builder()
                .password(request.getPassword())
                .build();
    }

    public ChangePasswordResponse changePassword(ChangePasswordDTO request) {
        User user = userRepository.findOneByDocumento(request.getDocumento())
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con documento: " + request.getDocumento()));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getDocumento(), request.getLastPassword()));
        } catch (Exception e) {
            throw new GenericAppException(HttpStatus.UNAUTHORIZED, "La contraseña ingresada es incorrecta");
        }

        String token = jwtServiceImpl.getToken(user);
        String tokenHash = jwtServiceImpl.hashToken(token);

        user.setTokenHash(tokenHash);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return ChangePasswordResponse.builder()
                .token(token)
                .build();
    }
    public UserDTO getUserById(Integer id){
        User user = userRepository.findOneById(id)
                .orElseThrow(()-> new GenericAppException(HttpStatus.NOT_FOUND, "Usuaio no encontrado"));
        if(user==null){
            throw new GenericAppException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
        UserDTO userDTO= UserMapper.userToUserDTO(user);
        return userDTO;
    }
}
