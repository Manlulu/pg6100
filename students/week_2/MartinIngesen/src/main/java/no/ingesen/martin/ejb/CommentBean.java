package no.ingesen.martin.ejb;

import no.ingesen.martin.entity.Comment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CommentBean {

    @PersistenceContext(unitName = "SecurityNews")
    private EntityManager entityManager;

    public CommentBean(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Comment persist(Comment comment){
        entityManager.persist(comment);
        return comment;
    }

    public Comment findById(int id){
        return entityManager.find(Comment.class, id);
    }

}
