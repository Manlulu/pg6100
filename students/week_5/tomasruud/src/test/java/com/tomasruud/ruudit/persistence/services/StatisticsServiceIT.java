package com.tomasruud.ruudit.persistence.services;

import com.tomasruud.ruudit.cases.ContainerTestCase;
import com.tomasruud.ruudit.persistence.repositories.CommentRepository;
import com.tomasruud.ruudit.persistence.repositories.PostRepository;
import com.tomasruud.ruudit.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatisticsServiceIT extends ContainerTestCase {
    
    private StatisticsService service;
    
    @Before
    public void setRepository() throws Exception {
        
        service = getManagedClass( StatisticsService.class );
    }

    @Test
    public void getCommentCountReturnsAllComments() throws Exception {

        CommentRepository repository = getManagedClass( CommentRepository.class );

        long realCount = repository.getAll().size();

        assertEquals( realCount, service.getTotalComments() );
    }

    @Test
    public void getNumberOfPostsReturnsNumberOfPosts() throws Exception {

        PostRepository repository = getManagedClass( PostRepository.class );

        long realCount = repository.getAll().size();

        assertEquals( realCount, service.getTotalPosts() );
    }

    @Test
    public void getNumberOfUsersReturnsNumberOfUsers() throws Exception {

        UserRepository repository = getManagedClass( UserRepository.class );

        long realCount = repository.getAll().size();

        assertEquals( realCount, service.getTotalUsers() );
    }

    @Test
    public void getAveragePostsPerUserReturnsActualAveragePostsPerUser() throws Exception {

        UserRepository userRepository = getManagedClass( UserRepository.class );
        PostRepository postRepository = getManagedClass( PostRepository.class );

        double realAverage = (double) postRepository.getAll().size() / userRepository.getAll().size();

        assertEquals( realAverage, service.getAverageNumberOfPostsPerUser(), 0 );
    }
}