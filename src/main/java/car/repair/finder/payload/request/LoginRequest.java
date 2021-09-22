package car.repair.finder.payload.request;

import car.repair.finder.user_type.UserType;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginRequest {
	@NotNull
	private String phone;

	@NotNull
	private String pin;

	@NotNull
	private String userType;
}
