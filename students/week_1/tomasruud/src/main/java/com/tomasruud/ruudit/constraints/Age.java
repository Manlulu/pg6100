package com.tomasruud.ruudit.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

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

    int min() default 0;
}
