package car.repair.finder.validator.validator;

import car.repair.finder.models.UserInformation;
import car.repair.finder.validator.annotation.UserInformationAn;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserInformationVa implements ConstraintValidator<UserInformationAn, UserInformation> {
    @Override
    public void initialize(UserInformationAn constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserInformation userInformation, ConstraintValidatorContext constraintValidatorContext) {
        if(userInformation.getAddress() == null || userInformation.getFullName() == null){
            return false;
        }
        return true;
    }
}
