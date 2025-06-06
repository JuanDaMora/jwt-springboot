package judamov.sipoh.service.impl;

import judamov.sipoh.dto.UserDTO;
import judamov.sipoh.entity.Role;
import judamov.sipoh.entity.User;
import judamov.sipoh.entity.UserRol;
import judamov.sipoh.exceptions.GenericAppException;
import judamov.sipoh.repository.IUserRepository;
import judamov.sipoh.repository.IUserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRolServiceImpl {
    private final IUserRoleRepository userRoleRepository;
    private final IUserRepository userRepository;

    public List<Role> getRolListFromUser(User user){
        List<UserRol> userRolList = userRoleRepository.findAllByUser(user)
                .orElseThrow(()-> new GenericAppException(HttpStatus.BAD_REQUEST,
                        "No se encontro ningun userRol para el usuario con id: "+ user.getId()));
        List<Role> roleList = null;
        for(UserRol userRole : userRolList) {
            roleList.add(userRole.getRole());
        }
        return roleList;
    }
}
