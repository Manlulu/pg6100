package no.ingesen.martin.dto.constraint;

import no.ingesen.martin.dto.Customer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class OldEnoughValidator implements ConstraintValidator<OldEnough, Customer> {
    public void initialize(OldEnough constraintAnnotation) {
    }

    public boolean isValid(Customer customer, ConstraintValidatorContext context) {
        if(customer.getDateOfBirth() == null) return false;
        if(customer.getDateOfRegistration() == null) return false;

        LocalDate dob = customer.getDateOfBirth();
        LocalDate reg = customer.getDateOfRegistration();

        Period period = Period.between(dob, reg);


        return(period.getYears() >= 18);
    }
}
