package com.tomasruud.ruudit.models;

import com.tomasruud.ruudit.generators.StringGenerator;
import com.tomasruud.ruudit.cases.ValidationTestCase;
import org.junit.Test;

public class PostTest extends ValidationTestCase {

    @Test
    public void allNonNullableFieldsCannotBeNull() throws Exception {

        Post post = new Post();

        post.setCreated( null );
        post.setTitle( null );
        post.setContent( null );

        propertyValidationShouldFail( "created", post );
        propertyValidationShouldFail( "title", post );
        propertyValidationShouldFail( "content", post );
    }

    @Test
    public void allNullableFieldsCanBeNull() throws Exception {

        Post post = new Post();

        post.setCreator( null );
        post.setVotes( null );

        propertyValidationShouldNotFail( "creator", post );
        propertyValidationShouldNotFail( "votes", post );
    }

    @Test
    public void titleLength() throws Exception {

        Post post = new Post();

        post.setTitle( StringGenerator.randomString( 257 ) );

        propertyValidationShouldFail( "title", post );

        post.setTitle( StringGenerator.randomString( 256 ) );

        propertyValidationShouldNotFail( "title", post );
    }

    @Test
    public void contentLength() throws Exception {

        Post post = new Post();

        post.setContent( StringGenerator.randomString( 2049 ) );

        propertyValidationShouldFail( "content", post );

        post.setContent( StringGenerator.randomString( 2048 ) );

        propertyValidationShouldNotFail( "content", post );
    }
}