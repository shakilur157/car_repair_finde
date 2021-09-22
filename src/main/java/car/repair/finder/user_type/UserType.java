package car.repair.finder.user_type;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

public enum UserType {
    CAR_OWNER,
    SERVICE_CENTRE,
    ADMIN
}
