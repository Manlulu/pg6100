package com.tomasruud.ruudit.models;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    // Creator field should be optional, because we don't want
    // to delete posts just because a user deletes his/her account
    @ManyToOne
    private User creator;

    @NotNull
    @Temporal( TemporalType.TIMESTAMP )
    private Date created;

    @NotNull
    @Size( max = 256 )
    private String title;

    @NotNull
    @Size( max = 2048 )
    private String content;

    @OneToMany( cascade = CascadeType.REMOVE, mappedBy = "parentPost" )
    private List<Comment> comments;

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

    public Date getCreated() {

        return created;
    }

    public void setCreated( Date created ) {

        this.created = created;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle( String title ) {

        this.title = title;
    }

    public String getContent() {

        return content;
    }

    public void setContent( String content ) {

        this.content = content;
    }

    public List<Comment> getComments() {

        return comments;
    }

    public void setComments( List<Comment> comments ) {

        this.comments = comments;
    }

    public VoteCollection getVotes() {

        return votes;
    }

    public void setVotes( VoteCollection votes ) {

        this.votes = votes;
    }
}
