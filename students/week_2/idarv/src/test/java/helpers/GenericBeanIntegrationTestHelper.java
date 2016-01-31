package helpers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Idar Vassdal on 29.01.2016.
 */
public class GenericBeanIntegrationTestHelper<T> {
    private static  EntityManagerFactory entityManagerFactory;
    private static  EntityManager persister;

    public GenericBeanIntegrationTestHelper() {
        entityManagerFactory = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        persister = entityManagerFactory.createEntityManager();
    }

    public boolean isTablePopulated(String tablename){
        return persister.createQuery("SELECT e FROM " + tablename + " e").getResultList().size() > 0;
    }

    public void persistEntity(T entity){
        persister.persist(entity);
    }

    public EntityManager getPersister(){
        return persister;
    }

    public static void closePersister(){
        persister.close();
        entityManagerFactory.close();
    }
}
