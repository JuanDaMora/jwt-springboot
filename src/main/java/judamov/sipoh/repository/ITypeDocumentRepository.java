package judamov.sipoh.repository;

import judamov.sipoh.entity.TypeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITypeDocumentRepository extends JpaRepository<TypeDocument, Long> {
    Optional<TypeDocument> findOneById(Integer id);

}
