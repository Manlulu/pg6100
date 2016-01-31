package ninja.idar.constraints.validators;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
@Constraint( validatedBy = VoteValidator.class )
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@Documented
public @interface ValidVote {
    String message() default "Vote is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}