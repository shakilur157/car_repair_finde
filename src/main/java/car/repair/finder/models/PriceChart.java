package car.repair.finder.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CollectionType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PriceChart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private double price;
    @NotNull
    @Column(name = "service_centre_id")
    private Long serviceCentreId;
    @NotNull
    @Column(name = "service_id")
    private Long serviceId;

    public PriceChart(double price, Long serviceId, Long serviceCentreId) {
        this.price = price;
        this.serviceCentreId = serviceCentreId;
        this.serviceId = serviceId;
    }
}

