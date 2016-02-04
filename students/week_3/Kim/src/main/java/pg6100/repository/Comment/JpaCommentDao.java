package pg6100.repository.Comment;


import pg6100.domain.Comment;
import pg6100.domain.News;
import pg6100.util.DatabaseConfig;
import pg6100.util.SortUtils;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class JpaCommentDao {

    @PersistenceContext(unitName = DatabaseConfig.database_name)
    private EntityManager entityManager;

    public JpaCommentDao(){
    }
    public JpaCommentDao(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public Comment createComment(Comment comment) {
        try {
            entityManager.persist(comment);
            return comment;
        } catch (Exception e) {
            return null;
        }
    }

    public Comment getCommentById(long id) {
        try {
            Query findById = entityManager.createNamedQuery(Comment.query_getCommentById, Comment.class).setParameter("id", id);
            return (Comment) findById.getResultList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Comment> getAllComment() {
        try {
            Query findById = entityManager.createNamedQuery(Comment.query_getAllComments, Comment.class);
            return findById.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public Comment getCommentByName(String name) {
        try {
            Query findById = entityManager.createNamedQuery(Comment.query_getCommentByName, Comment.class).setParameter("name", name);
            return (Comment) findById.getResultList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteComment(Comment comment) {
        try {
            entityManager.remove(entityManager.contains(comment) ? comment : entityManager.merge(comment));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Comment updateComment(Comment comment) {
        entityManager.merge(comment);
        return comment;
    }

    public List<Comment> getAllCommentsSortedOnVotesEver(){
        List<Comment> commentList = getAllComment();
        commentList.sort((c1, c2) -> SortUtils.sort(c1.getVotes(), c2.getVotes()));
        return commentList;
    }

    public List<Comment> getAllCommentsSortedOnDate(){
        List<Comment> commentList = getAllComment();
        commentList.sort((o1, o2) -> SortUtils.sortDate(o1.getCreatedAt(), o2.getCreatedAt()));
        return commentList;
    }
}
