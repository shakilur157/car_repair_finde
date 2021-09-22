package car.repair.finder.repositories;
import car.repair.finder.models.UserInformation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserInformationRepository extends CrudRepository<UserInformation, Long> {
    Optional<List<UserInformation>> findAllByUserIdAndUserType(Long userId, String role);
}
