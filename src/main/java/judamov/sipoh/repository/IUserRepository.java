package judamov.sipoh.repository;

import judamov.sipoh.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(attributePaths = {"userRoles", "userRoles.role"})
    Optional<User> findOneByDocumento(String documento);

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneById(Integer id);
}
