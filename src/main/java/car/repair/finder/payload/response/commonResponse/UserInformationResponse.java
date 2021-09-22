package car.repair.finder.payload.response.commonResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInformationResponse {
    private Long userInformationId;
    private String address;
    private String name;
    private String sex;

    public UserInformationResponse(Long userInformationId, String address, String name, String sex) {
        this.userInformationId = userInformationId;
        this.address = address;
        this.name = name;
        this.sex = sex;
    }
    public UserInformationResponse(){}
}
