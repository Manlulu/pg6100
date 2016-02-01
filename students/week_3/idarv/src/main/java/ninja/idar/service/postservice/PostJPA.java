package ninja.idar.service.postservice;

import ninja.idar.models.Post;
import ninja.idar.service.base.BaseDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by Idar Vassdal on 01.02.2016.
 */
public class PostJPA implements BaseDao<Post>, PostDao{
    private EntityManagerFactory entityManagerFactory;
    private EntityManager persister;

    public PostJPA() {
    }

    public PostJPA(EntityManager persister) {
        this.persister = persister;
    }

    @Override
    public List getAll() {
        return persister.createNamedQuery(Post.POST_ALL).getResultList();
    }

    @Override
    public Post update(Post entity) {
        return null;
    }

    @Override
    public Post getById(int id) {
        return persister.find(Post.class, id);
    }

    @Override
    public void deleteById(int id) {
        persister.remove(getById(id));
    }

    @Override
    public void deleteByObject(Post entity) {
        deleteById(entity.getId());
    }

    @Override
    public void persist(Post entity) {
        persister.persist(entity);
    }

    @Override
    public void close() {
        persister.close();
    }
}
