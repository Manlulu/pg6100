package no.ingesen.martin.dto.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotInFutureValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotInFuture {
    String message() default "Date is in future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
