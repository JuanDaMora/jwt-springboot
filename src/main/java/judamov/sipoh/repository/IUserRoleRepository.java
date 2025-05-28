package judamov.sipoh.repository;

import judamov.sipoh.entity.UserRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRol, Integer> {

}
