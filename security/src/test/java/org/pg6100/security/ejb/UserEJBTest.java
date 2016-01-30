package org.pg6100.security.ejb;

import org.junit.*;
import org.pg6100.security.data.UserDetails;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class UserEJBTest {

    private static EJBContainer ec;
    private static Context ctx;

    private static UserEJB userEJB;

    @BeforeClass
    public static void initContainer() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put(EJBContainer.MODULES,  new File("target/classes"));
        ec = EJBContainer.createEJBContainer(properties);
        ctx = ec.getContext();


        //as it is @Stateless, can init just once before running the test suite
        userEJB = (UserEJB) ctx.lookup("java:global/classes/"+UserEJB.class.getSimpleName()+"!"+UserEJB.class.getName());
    }

    @AfterClass
    public static void closeContainer() throws Exception {
        if (ctx != null)
            ctx.close();
        if (ec != null)
            ec.close();
    }

    @Before @After
    public void emptyDatabase(){
        //this is quicker than re-initialize the whole DB / EJB container
        userEJB.deleteAllUsers();
    }


    @Test
    public void testCanCreateAUser(){

        String user = "user";
        String password = "password";

        boolean created = userEJB.createUser(user,password);
        assertTrue(created);
    }

    @Test
    public void testDelete(){

        userEJB.deleteAllUsers();
        int n = 10;
        for(int i=0; i<n; i++){
            userEJB.createUser("foo"+i, "p");
        }

        int deleted = userEJB.deleteAllUsers();
        assertEquals(n, deleted);
    }


    @Test
    public void testNoTwoUsersWithSameId(){

        String user = "user";

        boolean created = userEJB.createUser(user,"a");
        assertTrue(created);

        created = userEJB.createUser(user,"b");
        assertFalse(created);
    }

    @Test
    public void testSamePasswordLeadToDifferentHashAndSalt(){

        String password = "password";
        String first = "first";
        String second = "second";

        userEJB.createUser(first,password);
        userEJB.createUser(second,password); //same password

        UserDetails f = userEJB.getUser(first);
        UserDetails s = userEJB.getUser(second);

        //those are EXTREMELY unlikely to be equal, although not impossible...
        //however, likely more chances to get hit in the head by a meteorite...
        assertNotEquals(f.getHash(), s.getHash());
        assertNotEquals(f.getSalt(), s.getSalt());
    }

    @Test
    public void testVerifyPassword(){

        String user = "user";
        String correct = "correct";
        String wrong = "wrong";

        userEJB.createUser(user, correct);

        boolean  canLogin = userEJB.validateLogin(user, correct);
        assertTrue(canLogin);

        canLogin = userEJB.validateLogin(user, wrong);
        assertFalse(canLogin);
    }

    @Test
    public void testBeSurePasswordIsNotStoredInPlain(){

        String user = "user";
        String password = "password";

        userEJB.createUser(user, password);

        UserDetails entity = userEJB.getUser(user);
        assertNotEquals(password, entity.getUserId());
        assertNotEquals(password, entity.getHash());
        assertNotEquals(password, entity.getSalt());
    }

}