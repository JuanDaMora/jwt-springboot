package judamov.sipoh.repository;

import judamov.sipoh.entity.Role;
import judamov.sipoh.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISemesterRepository extends JpaRepository<Semester, Long> {
    Optional<Semester> findOneById(Long id);
    Optional<Semester> findOneByDescription(String description);
}
