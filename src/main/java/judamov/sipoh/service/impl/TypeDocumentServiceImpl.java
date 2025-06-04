package judamov.sipoh.service.impl;

import judamov.sipoh.dto.TypeDocumentDTO;
import judamov.sipoh.entity.TypeDocument;
import judamov.sipoh.repository.ITypeDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeDocumentServiceImpl {
    private final ITypeDocumentRepository typeDocumentRepository;

    public List<TypeDocumentDTO> getAllTypeDocuments(){
        return typeDocumentRepository.findAll()
                .stream()
                .map(typeDocument -> new TypeDocumentDTO(typeDocument.getDescription()))
                .toList();
    }
    public TypeDocument createTypeDocument(TypeDocumentDTO typeDocumentDTO) {
        TypeDocument newTypeDocument= new TypeDocument();
        newTypeDocument.setDescription(typeDocumentDTO.getDescription());
        typeDocumentRepository.save(newTypeDocument);
        return newTypeDocument;
    }
}
