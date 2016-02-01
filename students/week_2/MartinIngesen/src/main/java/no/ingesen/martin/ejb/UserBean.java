package no.ingesen.martin.ejb;

import no.ingesen.martin.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserBean {

    @PersistenceContext(unitName = "SecurityNews")
    private EntityManager entityManager;

    public UserBean(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public User persist(User user){
        entityManager.persist(user);
        return user;
    }

    public User findById(int id){
        return entityManager.find(User.class, id);
    }
}
