package judamov.demo_jwt.repository;

import judamov.demo_jwt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findOneByDocumento(String documento); // Devuelve Optional
    Optional<User> findOneByEmail(String email); // Devuelve Optional
}
