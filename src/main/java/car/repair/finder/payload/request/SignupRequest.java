package car.repair.finder.payload.request;

import car.repair.finder.user_type.UserType;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    private String name;
    private String phone;
    private Set<String> role;

    private String password;

    public SignupRequest(String name, String phone, Set<String> role, String password) {
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.password = password;
    }
}
