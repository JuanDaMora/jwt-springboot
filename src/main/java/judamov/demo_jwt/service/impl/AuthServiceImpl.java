package judamov.demo_jwt.service.impl;

import judamov.demo_jwt.dto.AuthResponse;
import judamov.demo_jwt.dto.LoginRequest;
import judamov.demo_jwt.dto.RegisterRequest;
import judamov.demo_jwt.entity.TypeDocument;
import judamov.demo_jwt.enums.Role;
import judamov.demo_jwt.entity.User;
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
public class AuthService {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 10;
    private static final SecureRandom random = new SecureRandom();
    private final IUserRepository userRepository;
    private final ITypeDocumentRepository typeDocumentRepository;
    private final JwtService jwrService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(request.getUsername()));

        String token = jwtService.getToken(user);
        String tokenHash = jwtService.hashToken(token);
        Boolean isFirstLogin;
        if(user.getTokenHash().isEmpty()){
            isFirstLogin = true;
        }else {
            isFirstLogin = false;
        }
        user.setTokenHash(tokenHash);
        userRepository.save(user);  // Actualiza el usuario con el hash del token

        return AuthResponse.builder()
                .token(token)
                .isFirstLogin(isFirstLogin)
                .build();
    }
    public String  register(RegisterRequest request)
    {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        TypeDocument typeDocument = typeDocumentRepository.findById(request.getIdTipoDocumento())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));
        Role role;
        if(request.getIsProfesor()){
             role = Role.PROFESOR;
        }else {
             role = Role.ADMINISTRADOR;
        }
        User user = User.builder()
                .documento(request.getDocumento())
                .password(sb.toString())
                .typeDocument(typeDocument)
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .role(Role.DIRECTOR)
                .build();
        userRepository.save(user);
        return sb.toString();
    }
}
