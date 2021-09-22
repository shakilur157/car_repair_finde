package car.repair.finder.repositories;

import car.repair.finder.models.RequestedService;
import org.springframework.data.repository.CrudRepository;

public interface RequestedServiceRepository extends CrudRepository<RequestedService, Long> {
}
