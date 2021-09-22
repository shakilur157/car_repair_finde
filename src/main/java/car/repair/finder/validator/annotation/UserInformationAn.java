package car.repair.finder.validator.annotation;

import car.repair.finder.validator.validator.UserInformationVa;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserInformationVa.class)
public @interface UserInformationAn {
    String message() default "null is not acceptable";
    Class <?>[] groups() default {};
    public abstract Class<? extends Payload>[] payload() default {};
}
