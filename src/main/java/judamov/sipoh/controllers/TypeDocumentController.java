package judamov.sipoh.controllers;

import judamov.sipoh.dto.SiglaDTO;
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
    public ResponseEntity<List<TypeDocumentDTO>> getAllTypeDocuments (){
        return ResponseEntity.ok(typeDocumentService.getAllTypeDocuments());
    }
    @PostMapping
    public ResponseEntity<Boolean> createTypeDocument(@RequestBody TypeDocumentDTO request) {
        return ResponseEntity.ok(typeDocumentService.createTypeDocument(request));
    }
    @GetMapping("/sigla")
    public ResponseEntity<List<SiglaDTO>> getAllSiga(){

        return ResponseEntity.ok(typeDocumentService.getAllSiglas());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateTypeDocument(@PathVariable Long id,@RequestBody TypeDocumentDTO typeDocumentDTO){
        return  ResponseEntity.ok(typeDocumentService.updateTypeDocument(id,typeDocumentDTO));
    }


}
