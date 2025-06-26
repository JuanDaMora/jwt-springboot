package judamov.sipoh.controllers;

import judamov.sipoh.dto.GroupCreateDTO;
import judamov.sipoh.dto.GroupDTO;
import judamov.sipoh.dto.GroupUpdateDTO;
import judamov.sipoh.service.interfaces.IGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final IGroupService groupService;

    @GetMapping("/by-semesters")
    public ResponseEntity<List<GroupDTO>> getAllBySemesters(
            @RequestParam Long idSemester,
            @RequestHeader Long userId) {
        List<GroupDTO> groups = groupService.getAllBySemester(idSemester, userId);
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/by-levels")
    public ResponseEntity<List<GroupDTO>> getAllByLevels(
            @RequestParam List<Long> idLevels,
            @RequestHeader Long userId) {
        List<GroupDTO> groups = groupService.getAllByLevels(idLevels, userId);
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/by-subject")
    public ResponseEntity<List<GroupDTO>> getAllBySubject(
            @RequestParam Long idSubject,
            @RequestHeader Long userId) {
        List<GroupDTO> groups = groupService.getAllBySubject(idSubject, userId);
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/by-docente")
    public ResponseEntity<List<GroupDTO>> getAllByDocente(
            @RequestParam Long idDocente,
            @RequestHeader Long userId) {
        List<GroupDTO> groups = groupService.getAllByDocente(idDocente, userId);
        return ResponseEntity.ok(groups);
    }

    @PutMapping("/{groupId}")
    public ResponseEntity<GroupDTO> updateGroup(
            @PathVariable Long groupId,
            @RequestBody GroupUpdateDTO dto,
            @RequestHeader Long userId) {
        GroupDTO updated = groupService.updateGroup(groupId, dto, userId);
        return ResponseEntity.ok(updated);
    }
    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(
            @RequestBody GroupCreateDTO dto,
            @RequestHeader Long userId) {
        GroupDTO created = groupService.createGroup(dto, userId);
        return ResponseEntity.status(201).body(created);
    }

}
