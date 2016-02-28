package com.tomasruud.ruudit;

import com.tomasruud.ruudit.controllers.PostController;
import com.tomasruud.ruudit.models.Post;
import com.tomasruud.ruudit.models.User;
import com.tomasruud.ruudit.persistence.repositories.PostRepository;
import com.tomasruud.ruudit.persistence.repositories.UserRepository;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.logging.Logger;

@Singleton
@Startup
public class Seeder {

    @Inject
    private UserRepository userRepository;

    @Inject
    private PostRepository postRepository;

    @Inject
    private Logger logger;

    @PostConstruct
    public void setup() {

        User user = new User();
        user.setPassword( "p" );
        user.setUsername( "p" );

        userRepository.create( user );

        PostController postController = new PostController();
        postController.setRepository( postRepository );

        for( int i = 0; i < 5; i++ ) {

            Post post = new Post();

            post.setTitle( "This is a test #" + i );
            post.setContent( "something" );

            postController.setPost( post );

            postController.create( user );
        }

        logger.info( "Database seeded" );
    }
}
