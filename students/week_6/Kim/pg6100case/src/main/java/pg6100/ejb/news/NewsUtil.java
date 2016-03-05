package pg6100.ejb.news;

import pg6100.entity.News;
import pg6100.entity.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class NewsUtil {

    @Inject
    private NewsEJB newsEJB;

    public NewsUtil() {
    }

    public NewsUtil(NewsEJB newsEJB) {
        this.newsEJB = newsEJB;
    }

    protected void setNewsEJB(NewsEJB newsEJB) {
        this.newsEJB = newsEJB;
    }

    public void upVote(User user, News news) {
        if (user == null)
            return;

        List<User> usersUpVote = news.getUsersUpVote();
        List<User> userDownVote = news.getUsersDownVote();

        int points = news.getVotes();

        if (userDownVote.contains(user)) {
            userDownVote.remove(user);
            news.setUsersDownVote(userDownVote);
            points = points + 1;
        }

        if (usersUpVote.remove(user)) {
            points = points - 1;

            news.setUsersUpVote(usersUpVote);
            news.setVotes(points);

            newsEJB.updateNews(news);
            return;
        }


        usersUpVote.add(user);

        points = points + 1;

        news.setUsersUpVote(usersUpVote);
        news.setVotes(points);

        newsEJB.updateNews(news);
    }

    public void downVote(User user, News news) {
        if (user == null)
            return;

        List<User> usersDownVote = news.getUsersDownVote();
        List<User> usersUpVote = news.getUsersUpVote();

        int points = news.getVotes();
        if (usersUpVote.contains(user)) {
            usersUpVote.remove(user);
            news.setUsersUpVote(usersUpVote);
            points = points - 1;
        }

        if (usersDownVote.remove(user)) {
            points = points + 1;

            news.setUsersDownVote(usersDownVote);
            news.setVotes(points);

            newsEJB.updateNews(news);
            return;
        }


        usersDownVote.add(user);

        points = points - 1;

        news.setUsersDownVote(usersDownVote);
        news.setVotes(points);

        newsEJB.updateNews(news);
    }
}
