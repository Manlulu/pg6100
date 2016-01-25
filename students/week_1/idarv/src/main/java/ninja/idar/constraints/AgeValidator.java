package ninja.idar.constraints;

import ninja.idar.models.Customer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Idar Vassdal on 25.01.2016.
 * Inspired by @tomasruud (GitHub)
 */
public class AgeValidator implements ConstraintValidator<Age, Customer>{
    private int min;

    @Override
    public void initialize(Age age) {
        min = age.min();
    }

    @Override
    public boolean isValid(Customer customer, ConstraintValidatorContext context) {
        if(customer == null || customer.getDateOfBirth() == null){
            return true;
        }

        LocalDateTime dateMinYearsAgo = LocalDateTime.now().minusYears(min);
        return dateMinYearsAgo.getYear() >= getYearOfDate(customer.getDateOfBirth());
    }

    private int getYearOfDate(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).getYear();
    }
}
