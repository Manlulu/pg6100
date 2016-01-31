package no.ingesen.martin.ejb;

import no.ingesen.martin.entity.User;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class UserBeanIT extends ITBase{
    @Test
    public void testPersist() throws Exception {
        User user = new User();

        user.setEmail("email@yolo.com");
        user.setPassword("Learn2Hash. Do you even MD5?");
        user.setType(User.Type.USER);

        entityManager.getTransaction().begin();
        userBean.persist(user);
        entityManager.getTransaction().commit();

        assertTrue(user.getId() > 0);
    }

    @Test
    public void testFindById() throws Exception {
        User user = userBean.findById(1);
        assertTrue(user.getId() == 1);
    }
}
