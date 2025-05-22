package judamov.demo_jwt.repository;

import judamov.demo_jwt.entity.TypeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITypeDocumentRepository extends JpaRepository<TypeDocument, Long> {
    TypeDocument findOneById(Integer id);
}
