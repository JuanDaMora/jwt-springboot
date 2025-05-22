package judamov.demo_jwt.controllers;

import judamov.demo_jwt.dto.LoginRequest;
import judamov.demo_jwt.dto.TypeDocumentDTO;
import judamov.demo_jwt.entity.TypeDocument;
import judamov.demo_jwt.service.impl.TypeDocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tipoDocumento")
@RequiredArgsConstructor
public class TypeDocumentController {

    private final TypeDocumentServiceImpl typeDocumentService;

    @RequestMapping("/create")
    public ResponseEntity<TypeDocument> createTypeDocument(@RequestBody TypeDocumentDTO request) {
        return ResponseEntity.ok(typeDocumentService.createTypeDocument(request));
    }
}
