package pg6100.repository.News;


import pg6100.domain.News;
import pg6100.util.DatabaseConfig;
import pg6100.util.SortUtils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class JpaNewsDao {

    @PersistenceContext(unitName = DatabaseConfig.database_name)
    private EntityManager entityManager;

    public JpaNewsDao(){
    }
    public JpaNewsDao(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public News createNews(News news) {
        try {
            entityManager.persist(news);
            return news;
        } catch (Exception e) {
            return null;
        }
    }

    public News getNewsById(long id) {
        try {
            Query findById = entityManager.createNamedQuery(News.query_getNewsById, News.class).setParameter("id", id);
            return (News) findById.getResultList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public List<News> getAllNews() {
        try {
            Query findById = entityManager.createNamedQuery(News.query_getAllNews, News.class);
            return findById.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public News getNewsByName(String name) {
        try {
            Query findById = entityManager.createNamedQuery(News.query_getNewsByName    , News.class).setParameter("name", name);
            return (News) findById.getResultList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteNews(News news) {
        try {
            entityManager.remove(entityManager.contains(news) ? news : entityManager.merge(news));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public News updateNews(News news) {
        entityManager.merge(news);
        return news;
    }

    public List<News> getAllNewsSortedOnVotesEver(){
        List<News> newsList = getAllNews();
        newsList.sort((o1, o2) -> SortUtils.sort(o1.getVotes(), o2.getVotes()));
        return newsList;
    }

    public List<News> getAllNewsSortedOnDate(){
        List<News> newsList = getAllNews();
        newsList.sort((o1, o2) -> SortUtils.sortDate(o1.getCreatedAt(), o2.getCreatedAt()));
        return newsList;
    }

    public List<News> getAllNewsSortedOnRatings(){
        List<News> newsList = getAllNews();
        newsList.sort((o1, o2) -> SortUtils.sort(o1.getRating(), o2.getRating()));
        return newsList;
    }
}
