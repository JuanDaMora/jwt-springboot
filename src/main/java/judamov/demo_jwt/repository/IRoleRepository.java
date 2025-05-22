package judamov.demo_jwt.repository;

import judamov.demo_jwt.entity.Role;
import judamov.demo_jwt.entity.TypeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findOneById(Integer id);
}
