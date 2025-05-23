package judamov.sipoh.controllers;
import judamov.sipoh.dto.ChangePasswordDTO;
import judamov.sipoh.dto.ChangePasswordResponse;
import judamov.sipoh.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthPrivateController {

    private final AuthServiceImpl authService;

    @PostMapping("changePassword")
    public ResponseEntity<ChangePasswordResponse> changePassword(
        @RequestBody ChangePasswordDTO resetpassword) {
        return ResponseEntity.ok(authService.changePassword(resetpassword));

    }
}
