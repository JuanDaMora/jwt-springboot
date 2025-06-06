package judamov.sipoh.repository;

import judamov.sipoh.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAreaRepository  extends JpaRepository<Area, Long> {
    Optional<Area> findOneByDescription(String description);

    Optional<Area>findOneById(Long id);
}
