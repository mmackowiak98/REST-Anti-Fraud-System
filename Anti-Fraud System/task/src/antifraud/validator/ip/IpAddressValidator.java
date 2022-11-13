package antifraud.validator.ip;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpAddressValidator implements ConstraintValidator<IpAddressConstraint,String> {
    private static final String IPV4_PATTERN_ALLOW_LEADING_ZERO =
            "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";


    @Override
    public void initialize(IpAddressConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String ipAddress, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(IPV4_PATTERN_ALLOW_LEADING_ZERO);
        if(ipAddress!=null) {
            final String trimmedIpAddress = ipAddress.trim();
            Matcher matcher = pattern.matcher(trimmedIpAddress);
            return matcher.matches();
        }
        return false;
    }

}
