package no.ingesen.martin.ejb;

import no.ingesen.martin.entity.Post;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PostBean {

    @PersistenceContext(unitName = "SecurityNews")
    private EntityManager entityManager;

    public PostBean(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Post persist(Post post){
        entityManager.persist(post);
        return post;
    }

    public Post findById(int id){
        return entityManager.find(Post.class, id);
    }
}
