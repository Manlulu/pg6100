package no.ingesen.martin.dto.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class NotInFutureValidator implements ConstraintValidator<NotInFuture, LocalDate> {
    public void initialize(NotInFuture constraintAnnotation) {
    }

    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if(date == null) return false;

        return !date.isAfter(LocalDate.now());
    }
}
