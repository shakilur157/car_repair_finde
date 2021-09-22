package car.repair.finder.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "requested_services")
public class RequestedService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    @Column(name = "service_id")
    private Long serviceId;

    public RequestedService(Long serviceId) {
        this.serviceId = serviceId;
    }
    public RequestedService() { }
}
