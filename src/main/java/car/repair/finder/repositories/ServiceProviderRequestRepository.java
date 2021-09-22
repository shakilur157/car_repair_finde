package car.repair.finder.repositories;

import car.repair.finder.models.ServiceProviderRequest;
import org.springframework.data.repository.CrudRepository;

public interface ServiceProviderRequestRepository extends CrudRepository<ServiceProviderRequest, Long> {
    boolean existsByServiceCentreIdAndServiceRequestId(Long serviceCentreId, Long serviceRequestId);
}
