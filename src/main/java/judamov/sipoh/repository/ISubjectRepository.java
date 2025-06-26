package judamov.sipoh.repository;

import judamov.sipoh.entity.LevelSubject;
import judamov.sipoh.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISubjectRepository extends JpaRepository<Subject,Long> {
    Optional<List<Subject>> findByLevelSubject(LevelSubject levelSubject);
}
