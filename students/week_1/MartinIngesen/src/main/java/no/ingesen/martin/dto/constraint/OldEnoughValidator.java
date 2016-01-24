package no.ingesen.martin.dto.constraint;

import no.ingesen.martin.dto.Customer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class OldEnoughValidator implements ConstraintValidator<OldEnough, Customer> {

    /*
        AA: all overridden methods should be marked with @Override.
        Not a huge problem when implementing an interface like here, but
        would have definitively marked it down if it was extending a class
     */

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
