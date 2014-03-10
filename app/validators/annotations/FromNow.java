package validators.annotations;

import play.data.Form;
import validators.FromNowValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by danilo on 10/03/14.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FromNowValidator.class)
@Form.Display(name = "validations.fromNow")
public @interface FromNow {
    String message() default "error.fromNow";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
