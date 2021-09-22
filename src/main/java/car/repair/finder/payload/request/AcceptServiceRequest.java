package car.repair.finder.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class AcceptServiceRequest {
    @NotNull
    private Long serviceCentreId;
    @NotNull
    private Long serviceRequestId;
}
