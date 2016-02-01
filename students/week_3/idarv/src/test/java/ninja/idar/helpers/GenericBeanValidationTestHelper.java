package ninja.idar.helpers;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
public class GenericBeanValidationTestHelper<T> {
    private static ValidatorFactory validatorFactory;

    public GenericBeanValidationTestHelper() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
    }

    /**
     * Validates an object
     * @param object Object to validate
     * @return boolean, true if object doesn't have any violations
     */
    public boolean isValid(T object) {
        return getValidator().validate(object).size() == 0;
    }

    /**
     * Validates a property within an object
     * @param object Object to validate
     * @param property String name of property to validate
     * @return boolean, true if property doesn't have any violations
     */
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
