import no.ingesen.martin.dto.Address;
import no.ingesen.martin.dto.Customer;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private Validator validator;
    private Customer customer;

    @Before
    public void setUp() throws Exception {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        customer = new Customer();
        customer.setName("Mrtn");
        customer.setMiddleName("Ntrm");
        customer.setSurname("Igsn");
        customer.setDateOfBirth(LocalDate.of(1990, Month.JANUARY, 1));
        customer.setDateOfRegistration(LocalDate.now());

        Address address = new Address();
        address.setCity("Oslo");
        address.setState("Oslo");
        address.setStreet("Oslogate");
        address.setCountry("Norway");

        customer.setAddress(address);
    }

    @Test
    public void testThatAllTheStringsCantBeLongerThan64Chars(){
        String sixtyfive = new String(new char[65]).replace('\0', 'A');

        customer.setName(sixtyfive);
        customer.setMiddleName(sixtyfive);
        customer.setSurname(sixtyfive);

        Address address = customer.getAddress();

        address.setStreet(sixtyfive);
        address.setState(sixtyfive);
        address.setCountry(sixtyfive);
        address.setCity(sixtyfive);

        customer.setAddress(address);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertEquals("should not validate when strings are longer than 64 chars", 7, violations.size());
    }

    @Test
    public void testOnlyOptionalFieldsShouldBeNullable() throws Exception {
        Customer customer = new Customer();

        Address address = new Address();

        customer.setAddress(address);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertEquals("Eight of the nine fields should not validate. OldEnough and NotInFuture should not validate. Only middlename is nullable", 11, violations.size());
    }

    @Test
    public void testDobAndRegistrationCannotBeInFuture() throws Exception {
        customer.setDateOfBirth(LocalDate.now().plusDays(1));
        customer.setDateOfRegistration(LocalDate.now().plusYears(19));

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertEquals("date of birth and customer registration cannot be in the future", 2, violations.size());
    }

    @Test
    public void testCustomerShouldBeEighteenWhenRegistering() throws Exception {
        customer.setDateOfBirth(LocalDate.now().minusYears(17));
        customer.setDateOfRegistration(LocalDate.now());

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertEquals("when the customer registered, s/he should had been at least 18 years old", 1, violations.size());

        customer.setDateOfBirth(LocalDate.now().minusYears(18));
        customer.setDateOfRegistration(LocalDate.now());

        violations = validator.validate(customer);
        assertEquals("when the customer registered, s/he should had been at least 18 years old", 0, violations.size());

    }
}