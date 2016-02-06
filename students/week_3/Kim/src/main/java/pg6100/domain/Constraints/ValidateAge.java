package pg6100.domain.Constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeValidator.class)
public @interface ValidateAge {
    String message() default "Dette er en default message fra ValidateAge.java";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min () default 0;

}
