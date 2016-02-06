package pg6100.repository.News;

import pg6100.domain.News;
import pg6100.domain.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Map;

@Stateless
public class NewsUtil {

    @Inject
    private JpaNewsDao jpaNewsDao;

    private Map<User, Integer> votes;

    protected void setJpaNewsDao(JpaNewsDao jpaNewsDao){
        this.jpaNewsDao = jpaNewsDao;
    }

    public void upVote(User user, News news) {
        votes = news.getUserVotes();
        if (votes.containsKey(user) && votes.get(user) == 1) {
            votes.remove(user);
        } else {
            votes.put(user, 1);
        }
        news.setUserVotes(votes);

        jpaNewsDao.updateNews(news);
    }

    public void downVote(User user, News news) {
        votes = news.getUserVotes();
        if (votes.containsKey(user) && votes.get(user) == -1) {
            votes.remove(user);
        } else {
            votes.put(user, -1);
        }
        news.setUserVotes(votes);
    }
}
