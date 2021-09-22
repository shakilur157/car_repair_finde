package car.repair.finder.models;

import car.repair.finder.user_type.UserType;
import car.repair.finder.validator.annotation.UserInformationAn;
import car.repair.finder.validator.validator.UserInformationVa;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;

@Entity
@Getter
@Setter
@Table(name = "user_information")
@UserInformationAn
public class UserInformation implements Annotation {

    @Id
    @Column(name = "user_info_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userInfoId;

    @NotNull(message = "authentication failed")
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "user_Type")
    private String userType;

    @NotNull(message = "name must not be null")
    @Column(name = "full_name")
    private String fullName;

    @NotNull(message = "address must not be null")
    private String address;

    private String sex;

    public UserInformation(Long userId, String fullName, String address, String sex, String userType) {
        this.userId = userId;
        this.fullName = fullName;
        this.address = address;
        this.sex = sex;
        this.userType = userType;
    }

    public UserInformation() { }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
