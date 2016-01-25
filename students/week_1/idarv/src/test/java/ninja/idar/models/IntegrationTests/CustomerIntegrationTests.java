package ninja.idar.models.IntegrationTests;

import ninja.idar.models.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Idar Vassdal on 18.01.2016.
 */
public class CustomerIntegrationTests {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testLoadsInitScript() throws Exception {
        List<Customer> resultList = entityManager.createQuery("SELECT e FROM Customer e", Customer.class).getResultList();
        assertTrue("init.sql should populate the customer database", 1 < resultList.size());
    }

    @Test
    public void testNamedQueries() throws Exception {
        List<Customer> norwegianCustomers = entityManager.createNamedQuery(Customer.ALL_NORWEGIAN, Customer.class).getResultList();
        assertEquals("init.sql should import 3 Norwegian customers", 3, norwegianCustomers.size());

        List<Customer> osloCustomers = entityManager.createNamedQuery(Customer.ALL_FROM_OSLO, Customer.class).getResultList();
        assertEquals("init.sql should import 3 customers from Oslo", 2, osloCustomers.size());
    }
}