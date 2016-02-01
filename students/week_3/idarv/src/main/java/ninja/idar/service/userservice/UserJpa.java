package ninja.idar.service.userservice;

import ninja.idar.models.User;
import ninja.idar.service.base.BaseDao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by Idar Vassdal on 01.02.2016.
 */
@Stateless
public class UserJpa implements BaseDao<User>, UserDao{
    private EntityManagerFactory entityManagerFactory;
    private EntityManager persister;

    public UserJpa() {
    }

    public UserJpa(EntityManager persister) {
        this.persister = persister;
    }

    @Override
    public List<User> getAll() {
        return persister.createNamedQuery(User.USERS_ALL, User.class).getResultList();
    }

    @Override
    public User update(User user) {
        return persister.merge(user);
    }

    @Override
    public User getById(int id) {
        return persister.find(User.class, id);
    }

    @Override
    public void deleteById(int id) {
         deleteByObject(getById(id));
    }

    @Override
    public void deleteByObject(User user) {
         persister.remove(user);
    }

    @Override
    public void persist(User user) {
        persister.persist(user);
    }

    @Override
    public void close() {
        persister.close();
    }
}
