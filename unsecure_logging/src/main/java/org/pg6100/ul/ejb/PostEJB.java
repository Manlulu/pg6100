package org.pg6100.ul.ejb;


import org.pg6100.ul.entities.Post;
import org.pg6100.ul.entities.UserDetails;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Stateless
public class PostEJB implements Serializable{

    @EJB
    private UserEJB userEJB;

    @PersistenceContext(name = "DefaultUnit")
    private EntityManager em;


    public boolean post(String text, String author) {

        if (text == null || text.isEmpty()) {
            return false;
        }

        try {
            Post post = new Post();
            post.setCreationTime(new Date());
            post.setText(text);
            UserDetails userDetails = userEJB.getUser(author);
            post.setAuthor(userDetails);

            em.persist(post);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to post: "+e.toString(),
                    "Error msg: "+e.toString()));
            return false;
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Posted by "+author, ""));

        return true;
    }

    public List<Post> getAllPosts() {
        Query query = em.createNamedQuery(Post.GET_ALL);
        return query.getResultList();
    }

    public void delete(long id) {
        Query query = em.createNamedQuery(Post.DELETE_POST);
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
