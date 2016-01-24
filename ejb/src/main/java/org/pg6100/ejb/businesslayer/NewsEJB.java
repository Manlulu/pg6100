package org.pg6100.ejb.businesslayer;


import org.pg6100.ejb.datalayer.News;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Stateless
@LocalBean
public class NewsEJB implements RemoteNews{


    @Inject
    private EntityManager em;


    //@RolesAllowed("author") // Roles are set at the Web layer
    public void createNews(String author, String text){
        News news = new News(null, text, author);
        em.persist(news);
    }

    @Override
    public List<News> getAllNews(){
        return em.createNamedQuery(News.GET_ALL).getResultList();
    }

}
