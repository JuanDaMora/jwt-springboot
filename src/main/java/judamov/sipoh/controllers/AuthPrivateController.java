package judamov.sipoh.controllers;
import judamov.sipoh.dto.ChangePasswordDTO;
import judamov.sipoh.dto.ChangePasswordResponse;
import judamov.sipoh.dto.UserDTO;
import judamov.sipoh.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthPrivateController {

    private final AuthServiceImpl authService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(authService.getAllUsers());
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(authService.getUserById(id));
    }
    @PostMapping("/changePassword")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordDTO resetpassword) {
        return ResponseEntity.ok(authService.changePassword(resetpassword));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(authService.updateUser(id, userDTO));
    }
}
