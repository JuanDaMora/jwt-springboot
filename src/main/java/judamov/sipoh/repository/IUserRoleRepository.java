package judamov.sipoh.repository;

import judamov.sipoh.entity.User;
import judamov.sipoh.entity.UserRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRol, Long> {

    boolean findByUser(User user);
    Optional<List<UserRol>> findAllByUser(User user);
}
