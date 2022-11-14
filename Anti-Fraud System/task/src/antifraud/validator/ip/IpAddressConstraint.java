package antifraud.validator.ip;


import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {IpAddressValidator.class})
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IpAddressConstraint {
    String message() default "Invalid IP Address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
