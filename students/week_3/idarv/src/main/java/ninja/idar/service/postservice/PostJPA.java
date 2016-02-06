package ninja.idar.service.postservice;

import ninja.idar.models.Post;
import ninja.idar.service.base.BaseDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Idar Vassdal on 01.02.2016.
 */
public class PostJpa implements BaseDao<Post>, PostDao{
    private EntityManagerFactory entityManagerFactory;
    private EntityManager persister;

    public PostJpa() {
    }

    public PostJpa(EntityManager persister) {
        this.persister = persister;
    }

    @Override
    public List<Post> getAll() {
        return persister.createNamedQuery(Post.POST_ALL, Post.class).getResultList();
    }

    @Override
    public Post update(Post entity) {
        return persister.merge(entity);
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

    @Override
    public List getTodaysPosts() {
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        return persister.createNamedQuery(Post.POST_ALL, Post.class).getResultList()
                .stream()
                .filter(e -> e.getPublishedDate().getYear() == now.getYear())
                .filter(e -> e.getPublishedDate().getDayOfYear() == now.getDayOfYear())
                .filter(e -> e.getPublishedDate().getDayOfYear() == now.getDayOfYear())
                .collect(Collectors.toList());
    }
}
