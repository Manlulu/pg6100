package pg6100.repository.News;

import pg6100.domain.Comment;
import pg6100.domain.Enum.Country;
import pg6100.domain.News;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

@Stateless
public class NewsStatistics {

    @Inject
    private JpaNewsDao jpaNewsDao;

    protected void setJpaNewsDao(JpaNewsDao jpaNewsDao) {
        this.jpaNewsDao = jpaNewsDao;
    }

    public int getNumberOfTotalNews() {
        return jpaNewsDao.getAllNews().size();
    }

    public int getNumberOfNewsThisDay() {
        List<News> newsList = jpaNewsDao.getAllNews();

        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        List<News> newsThisDay = new ArrayList<>();

        for (int i = 0; i < newsList.size(); i++) {
            if (newsList.get(i).getCreatedAt().after(today.getTime())) {
                newsThisDay.add(newsList.get(i));
            }
        }
        return newsThisDay.size();
    }

    public int getNumberOfCountriesOnOneNews(News news) {
        int numberOfCountries = 0;
        Set<Country> countries = new HashSet<>();

        List<Comment> commentList = news.getComments();

        for (int i = 0; i < commentList.size(); i++) {

                Country country = commentList.get(i).getUser().getCountry();

                if (!countries.contains(country)) {
                    countries.add(country);
                    numberOfCountries++;
                }
        }
        return numberOfCountries;
    }
}
