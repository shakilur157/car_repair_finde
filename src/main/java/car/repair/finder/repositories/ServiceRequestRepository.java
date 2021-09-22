package car.repair.finder.repositories;

import car.repair.finder.models.ServiceRequest;
import org.springframework.data.repository.CrudRepository;

public interface ServiceRequestRepository extends CrudRepository<ServiceRequest, Long> {
}
