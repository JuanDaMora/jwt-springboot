package judamov.sipoh.repository;

import judamov.sipoh.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISemesterRepository extends JpaRepository<Semester, Integer> {
}
