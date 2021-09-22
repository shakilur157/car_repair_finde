package car.repair.finder.payload.response.commonResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommonErrorResponse{
    private int statusCode;
    private String message;

    public CommonErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}