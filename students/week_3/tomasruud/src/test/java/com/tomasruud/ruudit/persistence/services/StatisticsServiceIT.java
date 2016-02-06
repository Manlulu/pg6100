package com.tomasruud.ruudit.persistence.services;

import com.tomasruud.ruudit.cases.EntityManagerTestCase;
import com.tomasruud.ruudit.persistence.repositories.CommentRepository;
import com.tomasruud.ruudit.persistence.repositories.PostRepository;
import com.tomasruud.ruudit.persistence.repositories.UserRepository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatisticsServiceIT extends EntityManagerTestCase<StatisticsService> {
    
    public StatisticsServiceIT() {

        super( StatisticsService.class );
    }

    @Test
    public void getCommentCountReturnsAllComments() throws Exception {

        CommentRepository repository = new CommentRepository();
        repository.setManager( getManager() );

        long realCount = repository.getAll().size();

        assertEquals( realCount, getPersistence().getTotalComments() );
    }

    @Test
    public void getNumberOfPostsReturnsNumberOfPosts() throws Exception {

        PostRepository repository = new PostRepository();
        repository.setManager( getManager() );

        long realCount = repository.getAll().size();

        assertEquals( realCount, getPersistence().getTotalPosts() );
    }

    @Test
    public void getNumberOfUsersReturnsNumberOfUsers() throws Exception {

        UserRepository repository = new UserRepository();
        repository.setManager( getManager() );

        long realCount = repository.getAll().size();

        assertEquals( realCount, getPersistence().getTotalUsers() );
    }

    @Test
    public void getAveragePostsPerUserReturnsActualAveragePostsPerUser() throws Exception {

        UserRepository userRepository = new UserRepository();
        PostRepository postRepository = new PostRepository();

        userRepository.setManager( getManager() );
        postRepository.setManager( getManager() );

        double realAverage = (double) postRepository.getAll().size() / userRepository.getAll().size();

        assertEquals( realAverage, getPersistence().getAverageNumberOfPostsPerUser(), 0 );
    }
}