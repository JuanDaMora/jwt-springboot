package judamov.demo_jwt.service.impl;

import judamov.demo_jwt.dto.*;
import judamov.demo_jwt.entity.Role;
import judamov.demo_jwt.entity.TypeDocument;
import judamov.demo_jwt.entity.User;
import judamov.demo_jwt.repository.IRoleRepository;
import judamov.demo_jwt.repository.ITypeDocumentRepository;
import judamov.demo_jwt.repository.IUserRepository;
import judamov.demo_jwt.exceptions.AuthExceptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();
    private final IUserRepository userRepository;
    private final ITypeDocumentRepository typeDocumentRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtServiceImpl jwtServiceImpl;

    public AuthResponse login(LoginRequest request)
    {
        User user = userRepository.findOneByDocumento(request.getDocumento())
                .orElseThrow(() -> new UserNotFoundException(request.getDocumento()));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getDocumento(), request.getPassword()));


        String token = jwtServiceImpl.getToken(user);
        Boolean isFirstLogin = user.getTokenHash() == null;

        return AuthResponse.builder()
                .token(token)
                .isFirstLogin(isFirstLogin)
                .build();
    }
    public RegisterResponse register(RegisterRequest request) {
        TypeDocument typeDocument = typeDocumentRepository.findOneById(request.getIdTipoDocumento())
                .orElseThrow(() -> new TipoDocumentNotFoundException(request.getIdTipoDocumento()));

        Role role = roleRepository.findOneById(request.getIdRol())
                .orElseThrow(() -> new RolNotFoundException(request.getIdRol()));

        // Validaciones previas
        if (userRepository.findOneByDocumento(request.getDocumento()).isPresent()) {
            throw new UserRegistrationException("Ya existe un usuario con el documento: " + request.getDocumento());
        }

        if (userRepository.findOneByEmail(request.getEmail()).isPresent()) {
            throw new UserRegistrationException("Ya existe un usuario con el correo: " + request.getEmail());
        }

        User user = User.builder()
                .documento(request.getDocumento())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .typeDocument(typeDocument)
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .role(role)
                .active(true)
                .build();

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserRegistrationException("Error inesperado al guardar el usuario");
        }

        return RegisterResponse.builder()
                .password(request.getPassword())
                .build();
    }


    public ChangePasswordResponse changePassword(ChangePasswordDTO request){
        User user = userRepository.findOneByDocumento(request.getDocumento())
                .orElseThrow(() -> new UsernameNotFoundException(request.getDocumento()));
        String token = jwtServiceImpl.getToken(user);
        String tokenHash = jwtServiceImpl.hashToken(token);
        user.setTokenHash(tokenHash);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return ChangePasswordResponse.builder()
                .token(token)
                .build();
    }
}
