package ninja.idar.models.ValidationTests;

import ninja.idar.models.Address;
import ninja.idar.models.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Idar Vassdal on 18.01.2016.
 */
public class CustomerValidationTests {

    private Customer customer;
    private ValidatorFactory validatorFactory;
    private Validator validator;
    @Before
    public void setUp() throws Exception {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        customer = getCustomCustomer();
    }

    @After
    public void tearDown() throws Exception {
        validatorFactory.close();
    }

    @Test
    public void testEmptyCustomer() throws Exception {
        customer = new Customer();

        Set<ConstraintViolation<Customer>> validations = validator.validate(customer);
        assertEquals("4 fields should be @NotNull", 4, validations.size());
    }

    @Test
    public void testFullCustomer() throws Exception {
        Set<ConstraintViolation<Customer>> validations = validator.validate(customer);
        assertEquals("With all fields correctly filled out, no validations should be validated", 0, validations.size());
    }

    @Test
    public void testNullFirstName() throws Exception {
        customer.setFirstName(null);

        Set<ConstraintViolation<Customer>> validations = validator.validate(customer);
        assertEquals("First name can not be null, which will invalidate it", 1, validations.size());
    }

    @Test
    public void testNullMiddleName() throws Exception {
        customer.setMiddleName(null);

        Set<ConstraintViolation<Customer>> validations = validator.validate(customer);
        assertEquals("Middle name can  be null, which will not invalidate it", 0, validations.size());
    }

    @Test
    public void testNullSurName() throws Exception {
        customer.setSurName(null);

        Set<ConstraintViolation<Customer>> validations = validator.validate(customer);
        assertEquals("Surname can not be null, which will invalidate it", 1, validations.size());
    }

    @Test
    public void testNullDOB() throws Exception {
        customer.setDateOfBirth(null);

        Set<ConstraintViolation<Customer>> validations = validator.validate(customer);
        assertEquals("Date of Birth can not be null, which will invalidate it", 1, validations.size());
    }

    @Test
    public void testNullDOR() throws Exception {
        customer.setDateOfRegistration(null);

        Set<ConstraintViolation<Customer>> validations = validator.validate(customer);
        assertEquals("Date of Registration can not be null, which will invalidate it", 1, validations.size());
    }

    @Test
    public void testNullState() throws Exception {
        Address address = customer.getAddress();
        address.setState(null);
        customer.setAddress(address);

        Set<ConstraintViolation<Customer>> validations = validator.validate(customer);
        assertEquals("An addresses state can not be null, which will invalidate it", 1, validations.size());
    }

    @Test
    public void testNullCity() throws Exception {
        customer.getAddress().setCity(null);

        Set<ConstraintViolation<Customer>> validations = validator.validate(customer);
        assertEquals("An addresses city can not be null, which will invalidate it", 1, validations.size());
    }

    @Test
    public void testNullStreet() throws Exception {
        customer.getAddress().setStreet(null);

        Set<ConstraintViolation<Customer>> validations = validator.validate(customer);
        assertEquals("An addresses street can not be null, which will invalidate it", 1, validations.size());
    }

    @Test
    public void testAgeValidation() throws Exception{
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.add( Calendar.YEAR, -18 );
        customer.setDateOfBirth(calendarDate.getTime());

        Set<ConstraintViolation<Customer>> validations = validator.validate(customer);
        assertEquals("A customer can be 18 years of age", 0, validations.size());

        calendarDate = Calendar.getInstance();
        calendarDate.add( Calendar.YEAR, -17 );
        customer.setDateOfBirth(calendarDate.getTime());

        validations = validator.validate(customer);
        assertEquals("A customer can not be under 18 years of age", 1, validations.size());

        calendarDate = Calendar.getInstance();
        calendarDate.add( Calendar.YEAR, -19 );
        customer.setDateOfBirth(calendarDate.getTime());

        validations = validator.validate(customer);
        assertEquals("A customer can be over 18 years of age", 0, validations.size());
    }

    /**
     *
     * @return a valid Customer object
     */
    private Customer getCustomCustomer(){
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.add( Calendar.YEAR, - 21 );

        Date dateOfReg = new Date();
        Date dateOfBirth = calendarDate.getTime();
        Address address = new Address("countty", "state", "city", "street");

        return new Customer("FirstName", "MName", "LastName", dateOfBirth, dateOfReg, address);
    }
}