package com.tomasruud.ruudit.models;

import com.tomasruud.ruudit.generators.StringGenerator;
import com.tomasruud.ruudit.cases.ValidationTestCase;
import org.junit.Test;

public class CommentTest extends ValidationTestCase {

    @Test
    public void contentCannotBeLongerThan2048Chars() throws Exception {

        Comment comment = new Comment();
        comment.setContent( "Heyeyey" );

        propertyValidationShouldNotFail( "content", comment );

        comment.setContent( StringGenerator.randomString( 2049 ) );

        propertyValidationShouldFail( "content", comment );
    }

    @Test
    public void nullableFieldsCanBeNull() throws Exception {

        Comment comment = new Comment();
        comment.setCreator( null );
        comment.setVotes( null );

        propertyValidationShouldNotFail( "creator", comment );
        propertyValidationShouldNotFail( "votes", comment );
    }

    @Test
    public void nonNullableCannotBeNull() throws Exception {

        Comment comment = new Comment();
        comment.setCreated( null );
        comment.setParentPost( null );
        comment.setContent( null );

        propertyValidationShouldFail( "created", comment );
        propertyValidationShouldFail( "parentPost", comment );
        propertyValidationShouldFail( "content", comment );
    }
}