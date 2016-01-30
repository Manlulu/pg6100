package com.tomasruud.ruudit.models;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    // This should be optional to allow users to delete their account
    // without deleting content they have created
    @ManyToOne
    private User creator;

    @NotNull
    @Size( max = 2048 )
    private String content;

    @NotNull
    @Temporal( TemporalType.TIMESTAMP )
    private Date created;

    @NotNull
    @ManyToOne
    private Post parentPost;

    @Embedded
    @Valid
    private VoteCollection votes;

    @PrePersist
    private void generateCreatedDate() {

        if( created == null )
            created = Calendar.getInstance().getTime();
    }

    public Long getId() {

        return id;
    }

    public void setId( Long id ) {

        this.id = id;
    }

    public User getCreator() {

        return creator;
    }

    public void setCreator( User creator ) {

        this.creator = creator;
    }

    public String getContent() {

        return content;
    }

    public void setContent( String content ) {

        this.content = content;
    }

    public Date getCreated() {

        return created;
    }

    public void setCreated( Date created ) {

        this.created = created;
    }

    public VoteCollection getVotes() {

        return votes;
    }

    public void setVotes( VoteCollection votes ) {

        this.votes = votes;
    }

    public Post getParentPost() {

        return parentPost;
    }

    public void setParentPost( Post parent ) {

        this.parentPost = parent;
    }
}
