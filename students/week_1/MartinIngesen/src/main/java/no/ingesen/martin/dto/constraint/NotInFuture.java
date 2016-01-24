package no.ingesen.martin.dto.constraint;

/*
    AA: why ".dto."? I guess it stands for "Data Transfer Object".
    As it is confusing here, I might have marked it down
 */


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/*
    AA: this is correct, and I wouldn't have marked it as error.
    However, it is best to reuse existing standard libraries, eg
     javax.validation.constraints.Past
 */



@Constraint(validatedBy = NotInFutureValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotInFuture {
    String message() default "Date is in future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
