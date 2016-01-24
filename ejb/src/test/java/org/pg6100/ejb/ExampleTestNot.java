package org.pg6100.ejb;

import org.junit.Test;
import org.pg6100.ejb.businesslayer.AuthorBot;
import org.pg6100.ejb.businesslayer.CommentatorBot;
import org.pg6100.ejb.businesslayer.NewsEJB;
import org.pg6100.ejb.businesslayer.RemoteNews;
import org.pg6100.ejb.datalayer.News;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExampleTestNot {  //used "Not" (or any other suffix) to avoid Maven executing it,
                               // otherwise you would need -DskipTests when doing "mvn package"

    /*
        Note: it requires the packaged jar to be deployed on GlassFish.
        Cannot use an embedded EJB container as they do not support scheduled services.
        See book at page 231
     */

    @Test
    public void test() throws Exception{

        Context ctx = new InitialContext();

        Thread.sleep(2_000);

        RemoteNews newsEJB = (RemoteNews) ctx.lookup("java:global/ejb-0.0.1-SNAPSHOT/"
                + NewsEJB.class.getSimpleName()+"!"+ RemoteNews.class.getName());

        List<News> news = newsEJB.getAllNews();

        assertFalse(news.isEmpty());
        assertTrue(news.stream().anyMatch(n -> n.getAuthor().equals(AuthorBot.POST_CONSTRUCT)));
        assertTrue(news.stream().anyMatch(n -> n.getAuthor().equals(AuthorBot.BAR)));

        if(LocalDate.now().getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            assertTrue(news.stream().anyMatch(n -> n.getAuthor().equals(AuthorBot.MON)));
            assertFalse(news.stream().anyMatch(n -> n.getAuthor().equals(AuthorBot.SUN)));
        }
        if(LocalDate.now().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            assertTrue(news.stream().anyMatch(n -> n.getAuthor().equals(AuthorBot.SUN)));
            assertFalse(news.stream().anyMatch(n -> n.getAuthor().equals(AuthorBot.MON)));
        }


        long a = news.stream().flatMap(n -> n.getComments().stream())
                .filter(c -> c.getAuthor().equals(CommentatorBot.INJECTED_COMMENTATOR)).count();
        long b = news.stream().flatMap(n -> n.getComments().stream())
                .filter(c -> c.getAuthor().equals(CommentatorBot.JNDI_COMMENTATOR)).count();

        assertTrue(a>0);
        assertTrue(b>0);


        if (ctx != null) {
            ctx.close();
        }
    }
}
