package judamov.sipoh.repository;

import judamov.sipoh.entity.Sigla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface ISiglaRepository extends JpaRepository<Sigla, Long> {
    Optional<Sigla> findOneById(Long id);

    Optional<Sigla> findOneBySigla(String sigla);
}
