package judamov.sipoh.service.impl;

import judamov.sipoh.dto.RoleDTO;
import judamov.sipoh.entity.Role;
import judamov.sipoh.repository.IRoleRepository;
import judamov.sipoh.service.interfaces.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final IRoleRepository roleRepository;
    @Override
    public List<RoleDTO> getAllRoles(){
        return roleRepository.findAll()
                .stream()
                .map(role -> new RoleDTO(role.getId(), role.getName()))
                .toList();
    }
}
