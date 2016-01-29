package helpers;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
public class GenericBeanTestValidationHelper<T> {
    private static ValidatorFactory validatorFactory;

    public GenericBeanTestValidationHelper() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
    }

    public boolean isValid(T object) {
        return getValidator().validate(object).size() == 0;
    }

    public boolean isValidProperty(T object, String property) {
        return getValidator().validateProperty(object, property).size() == 0;
    }

    public static void closeValidationFactory(){
        validatorFactory.close();
    }
    private Validator getValidator() {
        return validatorFactory.getValidator();
    }


}
