package judamov.demo_jwt.service.impl;

import judamov.demo_jwt.dto.*;
import judamov.demo_jwt.entity.Role;
import judamov.demo_jwt.entity.TypeDocument;
import judamov.demo_jwt.entity.User;
import judamov.demo_jwt.repository.IRoleRepository;
import judamov.demo_jwt.repository.ITypeDocumentRepository;
import judamov.demo_jwt.repository.IUserRepository;
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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getDocumento(), request.getPassword()));
        User user = userRepository.findOneByDocumento(request.getDocumento())
                .orElseThrow(() -> new UsernameNotFoundException(request.getDocumento()));

        String token = jwtServiceImpl.getToken(user);
        Boolean isFirstLogin;
        if(user.getTokenHash()== null){
            isFirstLogin = true;
        }else {
            isFirstLogin = false;
        }
        return AuthResponse.builder()
                .token(token)
                .isFirstLogin(isFirstLogin)
                .build();
    }
    public RegisterResponse register(RegisterRequest request)
    {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        TypeDocument typeDocument = typeDocumentRepository.findById(request.getIdTipoDocumento())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));

        Role role=roleRepository.findOneById(request.getIdRol());
        User user = User.builder()
                .documento(request.getDocumento())
                .password(passwordEncoder.encode(sb.toString()))
                .email(request.getEmail())
                .typeDocument(typeDocument)
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .role(role)
                .active(true)
                .build();
        userRepository.save(user);
        return RegisterResponse.builder()
                .password(sb.toString())
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
