package judamov.sipoh.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import judamov.sipoh.dto.EmailRequestDTO;
import judamov.sipoh.entity.EmailTemplate;
import judamov.sipoh.entity.User;
import judamov.sipoh.exceptions.GenericAppException;
import judamov.sipoh.notifications.TemplateProcessor;
import judamov.sipoh.repository.IEmailRepository;
import judamov.sipoh.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
@RequiredArgsConstructor
public class EmailServiceImpl {

    @Value("${email.fake.destination}")
    private String fakeDestination;

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender mailSender;
    private final UserRolServiceImpl userRolService;
    private final IEmailRepository emailRepository;
    private final IUserRepository userRepository;

    public Boolean sendEmail(EmailRequestDTO emailRequestDTO) {
        EmailTemplate template = emailRepository.findByCode("credenciales_acceso")
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Plantilla 'credenciales_acceso' no encontrada."));

        Map<String, String> variables = Map.of(
                "nombre_destinatario", emailRequestDTO.getNombre(),
                "documento_destinatario", emailRequestDTO.getDocumento(),
                "password_destinatario", emailRequestDTO.getPassword()
        );

        String subject = TemplateProcessor.render(template.getSubject(), variables);
        String body = TemplateProcessor.render(template.getBody(), variables);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String destinoFinal = emailRequestDTO.getFake() ? fakeDestination : emailRequestDTO.getEmail();

            String contenido = emailRequestDTO.getFake()
                    ? "<p><b>[FAKE EMAIL]</b><br>Simulando envío a: " + emailRequestDTO.getEmail() + "</p><hr>" + body
                    : body;

            helper.setTo(destinoFinal);
            helper.setSubject(subject);
            helper.setText(contenido, true);
            helper.setFrom(from);

            mailSender.send(message);
            return true;

        } catch (MessagingException e) {
            throw new GenericAppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al enviar el correo: " + e.getMessage());
        }
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }
}
