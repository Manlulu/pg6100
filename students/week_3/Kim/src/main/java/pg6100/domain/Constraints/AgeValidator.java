package pg6100.domain.Constraints;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;

public class AgeValidator implements ConstraintValidator<ValidateAge, Date> {

    private Date currentDate;
    private ValidateAge validateAge;

    public void initialize(ValidateAge validateAge) {
        currentDate = new Date();
        this.validateAge = validateAge;
    }

    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {

        if (date == null)
            return true;

        Calendar calOther = Calendar.getInstance();
        Calendar calThis = Calendar.getInstance();

        calOther.setTime(date);
        calThis.setTime(currentDate);

        int yearOther = calOther.get(Calendar.YEAR);
        int yearThis = calThis.get(Calendar.YEAR);

        int monthOther = calOther.get(Calendar.MONTH) + 1;
        int monthThis = calThis.get(Calendar.MONTH) + 1;

        int dayOther = calOther.get(Calendar.DAY_OF_MONTH);
        int dayThis = calThis.get(Calendar.DAY_OF_MONTH);

        int years = yearThis - yearOther;

        if (monthOther > monthThis)
            years -= 1;

        if (monthOther == monthThis)
            if (dayOther > dayThis)
                years -= 1;


        System.out.println("Regnestykke 2: " + years);

        // TODO - Gjør 18 om til en variabel som du tar inn via annotasjonen
        if (years >= validateAge.min())
            return true;
        return false;
    }
}
