package antifraud.validator.ip;


import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

@Pattern(regexp = "((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.){3}(25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)")
@Documented
@Constraint(validatedBy = {})
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IpAddressConstraint {
    String message() default "Invalid IP Address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
