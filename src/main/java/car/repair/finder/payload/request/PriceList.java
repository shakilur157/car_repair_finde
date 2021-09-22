package car.repair.finder.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PriceList {
    private double price;
    private Long serviceId;
    public PriceList(double price, Long serviceId) {
        this.price = price;
        this.serviceId = serviceId;
    }
}
