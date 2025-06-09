package judamov.sipoh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private Long id_type_document;
    private String documento;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private List<Long> idsRoles;
    private List<String> rolesDescriptions;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;
    private Date lastLogin;
    private List<Long> idsAreas;
}
