package org.pg6100.ul.controllers;


import org.pg6100.ul.ejb.PostEJB;
import org.pg6100.ul.entities.Post;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class PostController implements Serializable{

    @Inject
    private PostEJB postEJB;

    private String formText;


    public String doPostText(String formAuthor){

        boolean posted = postEJB.post(formText,formAuthor);

        if(posted){
            formText = "";
        }

        return "home.xhtml";
    }

    public String getFormText() {
        return formText;
    }

    public void setFormText(String formText) {
        this.formText = formText;
    }

    public String deletePost(long id){
        postEJB.delete(id);
        return "home.xhtml";
    }

    public List<Post> getAllPosts(){
        return postEJB.getAllPosts();
    }
}
