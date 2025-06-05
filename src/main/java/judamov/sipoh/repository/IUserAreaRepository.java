package judamov.sipoh.repository;

import judamov.sipoh.entity.UserArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserAreaRepository extends JpaRepository<UserArea,Integer> {

}
