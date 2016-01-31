package no.ingesen.martin.ejb;

import no.ingesen.martin.entity.Vote;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class VoteBean {

    @PersistenceContext(unitName = "SecurityNews")
    private EntityManager entityManager;

    public VoteBean(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Vote persist(Vote vote){
        entityManager.persist(vote);
        return vote;
    }

    public Vote findById(int id){
        return entityManager.find(Vote.class, id);
    }
}
