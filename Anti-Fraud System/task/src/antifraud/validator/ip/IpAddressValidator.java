package antifraud.validator.ip;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpAddressValidator implements ConstraintValidator<IpAddressConstraint,String> {


    @Override
    public void initialize(IpAddressConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String ipAddress, ConstraintValidatorContext context) {
        if (ipAddress==null || !ipAddress.matches("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$") ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }else return true;
    }

}
