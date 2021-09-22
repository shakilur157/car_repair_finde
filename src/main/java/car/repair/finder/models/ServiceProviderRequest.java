package car.repair.finder.models;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "service_provider_request")
@Validated
@Getter
@Setter
@NoArgsConstructor
public class ServiceProviderRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "service_request_id")
    private Long serviceRequestId;

    @NotNull
    @Column(name = "service_centre_id")
    private Long serviceCentreId;

    @ColumnDefault("0.00")
    private double discount;

    @NotNull
    private double repairTime;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "DATE DEFAULT CURRENT_DATE",name = "created_at", updatable = false)
    private Date createdAt;

    public ServiceProviderRequest(Long serviceRequestId, Long serviceCentreId, double discount, double repairTime) {
        this.serviceRequestId = serviceRequestId;
        this.serviceCentreId = serviceCentreId;
        this.discount = discount;
        this.repairTime = repairTime;
    }
    public ServiceProviderRequest(Long serviceRequestId,  double discount, double repairTime) {
        this.serviceRequestId = serviceRequestId;
        this.discount = discount;
        this.repairTime = repairTime;
    }
    public ServiceProviderRequest(Long serviceRequestId, double repairTime) {
        this.serviceRequestId = serviceRequestId;
        this.repairTime = repairTime;
    }

}
