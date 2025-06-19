package judamov.sipoh.controllers;


import judamov.sipoh.dto.EmailRequestDTO;
import judamov.sipoh.repository.IEmailRepository;
import judamov.sipoh.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailServiceImpl emailService;
    private final IEmailRepository ee;

    @PostMapping("/send-credentials")
    public ResponseEntity<Boolean> sendCredentials(
            @RequestHeader Long userId,
            @RequestBody EmailRequestDTO request) {
        return ResponseEntity.ok(emailService.sendEmail(userId, request));
    }
}
