package judamov.sipoh.mappers;

import judamov.sipoh.dto.UserBasicUpdateDTO;
import judamov.sipoh.dto.UserDTO;
import judamov.sipoh.entity.TypeDocument;
import judamov.sipoh.entity.User;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO userToUserDTO(User user) {
        if (user == null) return null;

        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .idTipoDocumento(user.getTypeDocument().getId())
                .documento(user.getDocumento())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isActive(user.getActive())
                .idsRoles(user.getUserRoles().stream()
                        .map(userRol -> userRol.getRole().getId())
                        .toList())
                .rolesDescriptions(user.getUserRoles()
                        .stream()
                        .map(userRol -> userRol.getRole().getName())
                        .collect(Collectors.toList()))
                .createAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static User userDTOtoUser(UserDTO dto, TypeDocument typeDocument) {
        if (dto == null) return null;

        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .typeDocument(typeDocument)
                .documento(dto.getDocumento())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .active(dto.getIsActive())
                .build();
    }

    public static void updateUserBasicFields(User user, UserBasicUpdateDTO dto, TypeDocument typeDocument) {
        if (user == null || dto == null || typeDocument == null) return;

        user.setEmail(dto.getEmail());
        user.setTypeDocument(typeDocument);
        user.setDocumento(dto.getDocumento());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
    }
}
