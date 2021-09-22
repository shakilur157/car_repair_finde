package car.repair.finder.models;

import car.repair.finder.status.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Table(name = "service_requests")
@Entity
@Getter
@Setter
@NoArgsConstructor

public class ServiceRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "car_owner_id")
    private Long carOwnerID;

    @OneToMany(fetch= FetchType.LAZY, cascade=CascadeType.ALL, targetEntity = RequestedService.class)
    @JoinColumn(name = "car_owner_id", referencedColumnName = "id")
    @Column(name = "requested_service_list_id")
    private List<RequestedService> servicesId;

    @Column(name = "additional_message")
    private String additionalMessage;

    @NotNull
    private String location;

    private String status = "PENDING";

    @Column(name = "accepted_service_centre_id")
    private Long serviceCentreId = -1L;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "DATE DEFAULT CURRENT_DATE",name = "created_at", updatable = false)
    private Date createdAt;

    public ServiceRequest(Long carOwnerID, List<RequestedService> servicesId, String additionalMessage, String location) {
        this.carOwnerID = carOwnerID;
        this.servicesId = servicesId;
        this.additionalMessage = additionalMessage;
        this.location = location;
    }

    public ServiceRequest(Long id, Long carOwnerID, List<RequestedService> servicesId, String additionalMessage, String location, String status, Long serviceCentreId, Date createdAt) {
        this.id = id;
        this.carOwnerID = carOwnerID;
        this.servicesId = servicesId;
        this.additionalMessage = additionalMessage;
        this.location = location;
        this.status = status;
        this.serviceCentreId = serviceCentreId;
        this.createdAt = createdAt;
    }
}
