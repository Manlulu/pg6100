package com.tomasruud.ruudit.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/*
    AA: Use of @since with a date is questionable, as usually it is used to define
     a released version of a software, and not when the file was created
 */

/**
 * @author Tomas Ruud
 * @since 18.01.16
 */
@Constraint( validatedBy = AgeValidator.class )
@Target( ElementType.TYPE )
@Retention( RetentionPolicy.RUNTIME )
@Documented
public @interface Age {

    String message() default "Age is out of range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /*
        AA: nice. I didn't request it, but it is always good to make things more
         general, reusable and self-documenting
    */

    int min() default 0;
}
