package pg6100.controller;

import pg6100.ejb.news.NewsEJB;
import pg6100.ejb.news.NewsUtil;
import pg6100.ejb.user.UserEJB;
import pg6100.entity.Comment;
import pg6100.entity.News;
import pg6100.entity.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class NewsController {

    @EJB
    private NewsEJB newsEJB;
    @EJB
    private UserEJB userEJB;

    private NewsUtil newsUtil;

    private News news;


    private long newsId;


    @PostConstruct
    public void init() {
        news = new News();
        newsUtil = new NewsUtil(newsEJB);
    }

    public void initNews() {
        news = newsEJB.getNewsById(newsId);
    }


    public String createNews(String user) {
        news.setUser(userEJB.getUserByUserName(user));

        if (newsEJB.createNews(news) != null) {
            return "frontPage";
        } else {
            return "newsCreate.xhtml";
        }
    }

    public int getNumberOfNews(){
        return getAllNews().size();
    }

    public List<News> getAllNews(){
        return newsEJB.getAllNews();
    }

    public String updateNews() {
        if (news.getName() != null)
            newsEJB.updateNews(news);
        return "newsDetails.jsf";
    }

    public void upVoteNews(String currentUser, News currentNews){
        if(currentUser != null) {
            User user = userEJB.getUserByUserName(currentUser);
            newsUtil.upVote(user, currentNews);
        }
    }

    public void downVoteNews(String currentUser, News currentNews){
        if(currentUser != null) {
            User user = userEJB.getUserByUserName(currentUser);
            newsUtil.downVote(user, currentNews);
        }
    }

    private String commentBody;

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    @Inject
    private CommentController commentController;


    public void addComment(String username){
        initNews();

        User user = userEJB.getUserByUserName(username);

        Comment comment = new Comment();
        comment.setBody(commentBody);
        comment.setUser(user);
        commentController.createComment(comment);

        news.addComment(comment);

        newsEJB.updateNews(news);
    }

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
