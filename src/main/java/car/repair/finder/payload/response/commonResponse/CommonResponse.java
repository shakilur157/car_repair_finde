package car.repair.finder.payload.response.commonResponse;

import car.repair.finder.models.UserInformation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class CommonResponse <T>{
    private int statusCode;
    private String message;
    private T body;



    public CommonResponse(int statusCode, String message, T userInformation) {
        this.statusCode = statusCode;
        this.message = message;
        this.body = userInformation;
    }
}
