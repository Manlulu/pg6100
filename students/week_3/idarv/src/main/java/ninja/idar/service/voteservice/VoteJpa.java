package ninja.idar.service.voteservice;

import ninja.idar.models.Vote;
import ninja.idar.service.base.BaseDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by Idar Vassdal on 05.02.2016.
 */
public class VoteJpa implements BaseDao<Vote>, VoteDao{
    private EntityManagerFactory entityManagerFactory;
    private EntityManager persister;

    public VoteJpa() {
    }

    public VoteJpa(EntityManager persister) {
        this.persister = persister;
    }

    @Override
    public List<Vote> getAll() {
        return persister.createNamedQuery(Vote.VOTE_ALL, Vote.class).getResultList();
    }

    @Override
    public Vote update(Vote entity) {
        return persister.merge(entity);
    }

    @Override
    public Vote getById(int id) {
        return persister.find(Vote.class, id);
    }

    @Override
    public void deleteById(int id) {
        deleteByObject(getById(id));
    }

    @Override
    public void deleteByObject(Vote entity) {
        persister.remove(entity);
    }

    @Override
    public void persist(Vote entity) {
        persister.persist(entity);
    }

    @Override
    public void close() {
        persister.close();
    }
}
