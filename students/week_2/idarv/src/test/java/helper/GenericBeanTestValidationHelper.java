package helper;

import ninja.idar.models.Comment;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
public class GenericBeanTestValidationHelper<T> {
    private ValidatorFactory validatorFactory;

    public GenericBeanTestValidationHelper(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
    }

    public boolean isValid(T entity){
        return validatorFactory.getValidator().validate(entity).size() == 0;
    }



}
