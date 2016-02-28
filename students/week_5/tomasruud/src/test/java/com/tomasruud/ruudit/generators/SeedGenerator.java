package com.tomasruud.ruudit.generators;

import com.tomasruud.ruudit.cases.ContainerTestCase;
import com.tomasruud.ruudit.models.Post;
import com.tomasruud.ruudit.models.User;
import com.tomasruud.ruudit.persistence.repositories.CommentRepository;
import com.tomasruud.ruudit.persistence.repositories.PostRepository;
import com.tomasruud.ruudit.persistence.repositories.UserRepository;

import java.util.List;
import java.util.Random;

public class SeedGenerator extends ContainerTestCase {

    private static final String VALID_PASSWORD = "password";

    private static final Random RANDOM = new Random();

    private static UserRepository userRepository;
    private static PostRepository postRepository;
    private static CommentRepository commentRepository;

    public static void seed() {

        setRepositories();

        createUsers();
        createPosts();
    }

    private static void createPosts() {

        final List<User> users = userRepository.getAll();

        for( int id = 0; id < 10; id++ ) {

            Post post = new Post();
            post.setContent( "Random content for post #" + id );
            post.setTitle( "This is post #" + id );

            post.setCreated( DateGenerator.randomDay() );

            post.setCreator( users.get( RANDOM.nextInt( users.size() ) ) );

            postRepository.create( post );
        }
    }

    private static void createUsers() {

        final String[] names = {
                "Kramer",
                "Jerry",
                "Elaine",
                "George"
        };

        for( String name : names ) {

            User user = new User();
            user.setUsername( name );
            user.setPassword( VALID_PASSWORD );

            userRepository.create( user );
        }
    }

    private static void setRepositories() {

        userRepository = getManagedClass( UserRepository.class );
        postRepository = getManagedClass( PostRepository.class );
        commentRepository = getManagedClass( CommentRepository.class );
    }

    public static void reset() {

    }
}
