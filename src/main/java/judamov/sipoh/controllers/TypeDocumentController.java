package judamov.sipoh.controllers;

import judamov.sipoh.dto.TypeDocumentDTO;
import judamov.sipoh.entity.TypeDocument;
import judamov.sipoh.service.impl.TypeDocumentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipoDocumento")
@RequiredArgsConstructor
public class TypeDocumentController {

    private final TypeDocumentServiceImpl typeDocumentService;

    @GetMapping
    public ResponseEntity<List<TypeDocument>> getAllTypeDocuments (){
        return ResponseEntity.ok(typeDocumentService.getAllTypeDocuments());
    }
    @PostMapping
    public ResponseEntity<TypeDocument> createTypeDocument(@RequestBody TypeDocumentDTO request) {
        return ResponseEntity.ok(typeDocumentService.createTypeDocument(request));
    }

}
