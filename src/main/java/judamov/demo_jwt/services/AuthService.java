package judamov.demo_jwt.services;

import judamov.demo_jwt.Auth.AuthResponse;
import judamov.demo_jwt.Auth.LoginRequest;
import judamov.demo_jwt.Auth.RegisterRequest;
import judamov.demo_jwt.User.Role;
import judamov.demo_jwt.User.User;
import judamov.demo_jwt.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwrService;
    public AuthResponse login(LoginRequest request)
    {
        return null;
    }
    public AuthResponse register(RegisterRequest request)
    {
        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .country(request.getCountry())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return AuthResponse.builder()
                .token(jwrService.getToken(user))
                .build();
    }
}
