package judamov.demo_jwt.controllers;
import judamov.demo_jwt.dto.ChangePasswordDTO;
import judamov.demo_jwt.dto.ChangePasswordResponse;
import judamov.demo_jwt.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthPrivateController {

    private final AuthServiceImpl authService;

    @PostMapping("resetPassword")
    public ResponseEntity<ChangePasswordResponse> resetPassword(@RequestHeader String token, @RequestBody ChangePasswordDTO resetpassword) {
        return ResponseEntity.ok(authService.changePassword(resetpassword));

    }
}
