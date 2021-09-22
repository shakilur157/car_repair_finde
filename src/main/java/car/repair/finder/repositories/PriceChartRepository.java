package car.repair.finder.repositories;

import car.repair.finder.models.PriceChart;
import org.springframework.data.repository.CrudRepository;

public interface PriceChartRepository extends CrudRepository<PriceChart, Long> {
}
