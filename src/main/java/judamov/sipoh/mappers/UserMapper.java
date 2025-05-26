package judamov.sipoh.mappers;

import judamov.sipoh.dto.UserDTO;
import judamov.sipoh.entity.Role;
import judamov.sipoh.entity.TypeDocument;
import judamov.sipoh.entity.User;

public class UserMapper {

    public static UserDTO userToUserDTO(User user) {
        if (user == null) return null;

        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .id_type_document(user.getTypeDocument().getId())
                .documento(user.getDocumento())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isActive(user.getActive())
                .id_Role(user.getRole().getId())
                .createAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static User userDTOtoUser(UserDTO dto, TypeDocument typeDocument, Role role) {
        if (dto == null) return null;

        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .typeDocument(typeDocument)
                .documento(dto.getDocumento())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .active(dto.getIsActive())
                .role(role)
                // omitir password y tokenHash si no vienen desde el DTO
                .build();
    }
}
