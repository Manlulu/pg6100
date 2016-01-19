package no.ingesen.martin.dto.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = OldEnoughValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OldEnough {
    String message() default "Not old enough to register";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
