package ninja.idar.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Idar Vassdal on 25.01.2016.
 */
@Constraint( validatedBy = AgeValidator.class )
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@Documented
public @interface Age {

    String message() default "Age is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 0;
}
