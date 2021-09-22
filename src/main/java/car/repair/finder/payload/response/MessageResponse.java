package car.repair.finder.payload.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageResponse {
	private String message;
	private int statusCode;

	public MessageResponse(String message) {
	    this.message = message;
	}
	public MessageResponse(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;
	}
}
