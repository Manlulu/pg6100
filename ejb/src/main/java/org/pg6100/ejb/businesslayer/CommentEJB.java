package org.pg6100.ejb.businesslayer;

import org.pg6100.ejb.datalayer.Comment;
import org.pg6100.ejb.datalayer.News;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;


@Stateless
public class CommentEJB {

    @Inject
    private EntityManager em;


    @Remove
    public void save(@NotNull Long newsId, @NotNull String author, @NotNull String draft){

        News news = em.find(News.class, newsId);
        if(news == null){
            return;
        }

        Comment comment = new Comment(null, draft, author);
        em.persist(comment);

        news.getComments().add(comment);

        em.merge(news);
    }
}
