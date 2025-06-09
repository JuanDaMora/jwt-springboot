package judamov.sipoh.repository;

import judamov.sipoh.entity.UserArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserAreaRepository extends JpaRepository<UserArea,Long> {
    List<UserArea> findByUserId(Long userId);

}
