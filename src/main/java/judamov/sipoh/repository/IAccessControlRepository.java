package judamov.sipoh.repository;

import judamov.sipoh.entity.AccessControl;
import judamov.sipoh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccessControlRepository extends JpaRepository<AccessControl, Integer> {
    Optional<AccessControl> findOneByUser(User user);
}
