package car.repair.finder.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
	private int statusCode;
	private String message;
	private String token;
	private String type = "Bearer";
	private Long id;
	private String name;
	private String phone;
	private List<String> roles;

	public JwtResponse(int statusCode, String message, String accessToken, Long id, String name, String phone, List<String> roles) {
		this.statusCode = statusCode;
		this.message = message;
		this.token = accessToken;
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.roles = roles;
	}

}
