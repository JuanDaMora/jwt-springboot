package judamov.sipoh.controllers;

import judamov.sipoh.dto.RoleDTO;
import judamov.sipoh.service.impl.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RolesController {

    private final RoleServiceImpl roleService;

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles(){
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}
