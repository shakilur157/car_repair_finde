package car.repair.finder.utils;

import car.repair.finder.user_type.UserType;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

public class CommonUtils {
    public Boolean findValidUser(List<String> roles, String userType){
        for (String role : roles) {
            if (role.toLowerCase().equals(userType.toLowerCase())) {
                return true;
            }
        }
        return  false;
    }
    public String getJWTFromHeader(Map<String, String> headers){
        String jwt = "";
        int size = headers.size();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            if(entry.getKey().equals("authorization")){
                if(entry.getValue().startsWith("Bearer ")){
                    jwt = getJWT(entry.getValue());
                    return jwt;
                }
            }
        }

        return null;
    }
    public String getJWT(String bearer){
        return bearer.substring(7, bearer.length());
    }

    public UserType getUserRoleFromUserType(String type){
        String uType = type.toUpperCase();
        return UserType.valueOf(type);
    }
}
