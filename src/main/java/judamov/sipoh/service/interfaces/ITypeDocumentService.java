package judamov.sipoh.service.interfaces;

import judamov.sipoh.dto.TypeDocumentDTO;
import judamov.sipoh.entity.TypeDocument;

import java.util.List;
public interface ITypeDocumentService {
    public List<TypeDocumentDTO> getAllTypeDocuments();

    public Boolean createTypeDocument(TypeDocumentDTO typeDocumentDTO);
}
