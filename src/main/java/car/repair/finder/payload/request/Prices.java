package car.repair.finder.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Prices {
    private List<PriceList> priceList;
}
