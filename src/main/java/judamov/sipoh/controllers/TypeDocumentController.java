package judamov.sipoh.controllers;

import judamov.sipoh.dto.TypeDocumentDTO;
import judamov.sipoh.entity.TypeDocument;
import judamov.sipoh.service.impl.TypeDocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tipoDocumento")
@RequiredArgsConstructor
public class TypeDocumentController {

    private final TypeDocumentServiceImpl typeDocumentService;

    @RequestMapping("/create")
    public ResponseEntity<TypeDocument> createTypeDocument(@RequestBody TypeDocumentDTO request) {
        return ResponseEntity.ok(typeDocumentService.createTypeDocument(request));
    }
}
