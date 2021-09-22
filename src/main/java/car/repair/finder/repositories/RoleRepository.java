package car.repair.finder.repositories;

import car.repair.finder.models.UserRoles;
import car.repair.finder.user_type.UserType;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<UserRoles, String> {

}
