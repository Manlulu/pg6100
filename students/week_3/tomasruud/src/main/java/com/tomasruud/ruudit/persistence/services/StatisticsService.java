package com.tomasruud.ruudit.persistence.services;

import com.tomasruud.ruudit.persistence.contracts.EntityManagerManaged;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class StatisticsService implements EntityManagerManaged {

    @Inject
    private EntityManager manager;

    public long getTotalComments() {

        return manager.
                createQuery( "SELECT SUM(p.comments.size) FROM Post p", Long.class ).
                getSingleResult();
    }

    public long getTotalPosts() {

        return manager.
                createQuery( "SELECT COUNT(p) FROM Post p", Long.class ).
                getSingleResult();
    }

    public long getTotalUsers() {

        return manager.
                createQuery( "SELECT COUNT(u) FROM User u", Long.class ).
                getSingleResult();
    }

    public double getAverageNumberOfPostsPerUser() {

        return (getTotalPosts() * 1.0) / getTotalUsers();
    }

    @Override
    public void setManager( EntityManager manager ) {

        this.manager = manager;
    }
}
