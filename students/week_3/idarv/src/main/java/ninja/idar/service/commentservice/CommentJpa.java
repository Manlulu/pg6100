package ninja.idar.service.commentservice;

import ninja.idar.models.Comment;
import ninja.idar.service.base.BaseDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by Idar Vassdal on 01.02.2016.
 */
public class CommentJpa implements BaseDao<Comment>, CommentDao {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager persister;

    public CommentJpa() {
    }

    public CommentJpa(EntityManager persister) {
        this.persister = persister;
    }

    @Override
    public List<Comment> getAll() {
        return persister.createNamedQuery(Comment.COMMENT_ALL, Comment.class).getResultList();
    }

    @Override
    public Comment update(Comment entity) {
        return persister.merge(entity);
    }

    @Override
    public Comment getById(int id) {
        return persister.find(Comment.class, id);
    }

    @Override
    public void deleteById(int id) {
        deleteByObject(getById(id));
    }

    @Override
    public void deleteByObject(Comment entity) {
        persister.remove(entity);
    }

    @Override
    public void persist(Comment entity) {
        persister.persist(entity);
    }

    @Override
    public void close() {
        persister.close();
    }
}
