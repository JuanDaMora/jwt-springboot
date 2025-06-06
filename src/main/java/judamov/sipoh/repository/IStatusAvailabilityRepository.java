package judamov.sipoh.repository;

import judamov.sipoh.entity.StatusAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusAvailabilityRepository extends JpaRepository<StatusAvailability, Long> {
}
