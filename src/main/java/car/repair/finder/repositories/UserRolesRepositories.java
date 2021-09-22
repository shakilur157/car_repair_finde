package car.repair.finder.repositories;

import car.repair.finder.models.UserRoles;
import car.repair.finder.user_type.UserType;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRolesRepositories extends CrudRepository<UserRoles, Long> {

    Optional<UserRoles> findByUserType(UserType role);
}
