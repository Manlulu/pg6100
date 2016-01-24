package no.ingesen.martin.dto.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
    AA: correct. However, as the term "OldEnough" might be ambiguous, could
    had been best to add a short JavaDoc comment like:

    Check if a customer was at least 18 years old when registered
 */

@Constraint(validatedBy = OldEnoughValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OldEnough {
    String message() default "Not old enough to register";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
