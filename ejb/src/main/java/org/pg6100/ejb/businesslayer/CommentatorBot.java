package org.pg6100.ejb.businesslayer;


import org.pg6100.ejb.datalayer.News;

import javax.ejb.*;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
@Startup
@DependsOn("DatabaseInitializer")
public class CommentatorBot {

    public final static String INJECTED_COMMENTATOR = "injectedCommentator";
    public final static String JNDI_COMMENTATOR = "jndiCommentator";

    private final AtomicInteger counter = new AtomicInteger(0);

    @Inject
    private NewsEJB newsEJB;

    @Inject
    private CommentEJB commentEJB;

    @Schedule(second = "*/2", minute="*", hour="*", persistent=false)
    public void injectedCommentator(){
        if(counter.get() > 5){
            return;
        }
        counter.incrementAndGet();

        List<News> news = newsEJB.getAllNews();
        news.stream().forEach(n -> {
            commentEJB.save(n.getId(), INJECTED_COMMENTATOR, "A comment made by injectedCommentator()");
        });
    }


    @Schedule(second = "*/2", minute="*", hour="*", persistent=false)
    public void jndiCommentator(){
        if(counter.get() > 5){
            return;
        }
        counter.incrementAndGet();

        final CommentEJB ejb;
        try {
            Context cx = new InitialContext();
            ejb = (CommentEJB) cx.lookup("java:global/ejb-0.0.1-SNAPSHOT/"+CommentEJB.class.getSimpleName());
        } catch (NamingException e) {
            return;
        }

        List<News> news = newsEJB.getAllNews();
        news.stream().forEach(n -> {
            ejb.save(n.getId(), JNDI_COMMENTATOR, "A comment made by jndiCommentator()");
        });
    }


}
