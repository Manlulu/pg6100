package com.tomasruud.ruudit.constraints;

import com.tomasruud.ruudit.models.Customer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Tomas Ruud
 * @since 18.01.16
 */
public class AgeValidator implements ConstraintValidator<Age, Customer> {

    private int min;

    @Override
    public void initialize( Age age ) {

        min = age.min();
    }

    @Override
    public boolean isValid( Customer customer, ConstraintValidatorContext context ) {

        if( customer == null )
            return true;

        LocalDate registered = dateToLocalDate( customer.getRegistered() );
        LocalDate birthday = dateToLocalDate( customer.getBirthday() );

        Period timespan = Period.between( birthday, registered );

        if( timespan.getYears() >= min )
            return true;

        context.buildConstraintViolationWithTemplate( context.getDefaultConstraintMessageTemplate() )
                .addPropertyNode( "birthday" ).addConstraintViolation();

        return false;
    }

    private LocalDate dateToLocalDate( Date date ) {

        return date.toInstant().atZone( ZoneId.systemDefault() ).toLocalDate();
    }

    private String accessor( String property ) {

        return "get" + Character.toUpperCase( property.charAt( 0 ) ) + property.substring( 1 );
    }
}
