package judamov.demo_jwt.controllers;

import judamov.demo_jwt.dto.AuthResponse;
import judamov.demo_jwt.dto.LoginRequest;
import judamov.demo_jwt.dto.RegisterRequest;
import judamov.demo_jwt.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    @PostMapping(value="login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authServiceImpl.login(request));
    }

    @PostMapping(value="register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authServiceImpl.register(request));
    }
}
