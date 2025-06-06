package judamov.sipoh.service.interfaces;

import judamov.sipoh.dto.TypeDocumentDTO;
import judamov.sipoh.entity.TypeDocument;

import java.util.List;
public interface ITypeDocumentService {
    List<TypeDocumentDTO> getAllTypeDocuments();

    Boolean createTypeDocument(TypeDocumentDTO typeDocumentDTO);

    Boolean updateTypeDocument(Long id, TypeDocumentDTO typeDocumentDTO);
}
