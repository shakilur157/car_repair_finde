package car.repair.finder.repositories;

import car.repair.finder.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByPhone(String phone);

    Boolean existsByPhone(String phone);

}
