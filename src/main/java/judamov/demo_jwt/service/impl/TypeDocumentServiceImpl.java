package judamov.demo_jwt.service.impl;

import judamov.demo_jwt.dto.TypeDocumentDTO;
import judamov.demo_jwt.entity.TypeDocument;
import judamov.demo_jwt.repository.ITypeDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeDocumentServiceImpl {
    private final ITypeDocumentRepository typeDocumentRepository;

    public TypeDocument createTypeDocument(TypeDocumentDTO typeDocumentDTO) {
        TypeDocument newTypeDocument= new TypeDocument();
        newTypeDocument.setDescription(typeDocumentDTO.getDescription());
        typeDocumentRepository.save(newTypeDocument);
        return newTypeDocument;
    }
}
