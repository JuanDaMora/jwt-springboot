package judamov.sipoh.repository;

import judamov.sipoh.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEmailRepository extends JpaRepository<EmailTemplate, Long > {
    Optional<EmailTemplate> findByCode(String code);
}
