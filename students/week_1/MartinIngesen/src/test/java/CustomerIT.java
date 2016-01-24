
/*
    AA: tests for a class org.foo.X should be in the same package as X,
    eg org.foo.XTest.

    Why? not only easier to find and better organization, but also then
    the unit tests would be able to call protected/package level methods in X
 */


import no.ingesen.martin.dto.Address;
import no.ingesen.martin.dto.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CustomerIT {
    private EntityManagerFactory factory;
    private EntityManager entityManager;


    @Before
    public void setUp() throws Exception{
        factory = Persistence.createEntityManagerFactory("pg6100-week-1");
        entityManager = factory.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        entityManager.close();
        factory.close();
    }

    @Test
    public void testThatThereAreThreeNorwegian() throws Exception {
        List norwegians = entityManager.createNamedQuery("Customer.allNorwegian").getResultList();
        assertTrue("allNorwegian should return three.", norwegians.size() == 3);
    }

    @Test
    public void testThatThereAreTwoUsersWhoLiveInOslo() throws Exception {
        List oslos = entityManager.createNamedQuery("Customer.allFromOslo").getResultList();
        assertTrue("allFromOslo should return two.", oslos.size() == 2);

    }
    
    @Test
    public void testPersist() throws Exception {
        Customer customer = new Customer();
        customer.setName("Ola");
        customer.setMiddleName("Bola");
        customer.setSurname("Dola");
        customer.setDateOfBirth(LocalDate.now().minusYears(20));
        customer.setDateOfRegistration(LocalDate.now());

        Address address = new Address();
        address.setCountry("Norway");
        address.setStreet("Polarbear St.");
        address.setState("Svalbard");
        address.setCity("Iceberg");

        customer.setAddress(address);

        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();

        assertTrue(customer.getCustomerId() > 0);
    }
}
