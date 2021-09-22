package car.repair.finder.repositories;

import car.repair.finder.models.Services;
import org.springframework.data.repository.CrudRepository;

public interface ServicesRepository extends CrudRepository<Services, Long> {
    boolean existsByName(String name);
}
