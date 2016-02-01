package no.espenschatten.exercise.hackernews.comment.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import no.espenschatten.exercise.hackernews.comment.entity.Comment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@RunWith(MockitoJUnitRunner.class)
public class CommentControllerTest {

	@InjectMocks
	private CommentController commentController;

	@Mock
	private EntityManager entityManager;

	@Mock
	private Query t;

	private List<Comment> comments;
	private List<Comment> emptyList;

	private ArgumentCaptor<Integer> argumentCaptor;
	private ArgumentCaptor<String> namedQueryIdCaptor;
	private ArgumentCaptor<String> queryParameterCaptor;
	private ArgumentCaptor<Comment> commentArgumentCaptor;

	@Before
	public void setUp() {
		comments = generateComments();
		emptyList = new ArrayList<>();
		argumentCaptor = ArgumentCaptor.forClass(Integer.class);
		namedQueryIdCaptor = ArgumentCaptor.forClass(String.class);
		queryParameterCaptor = ArgumentCaptor.forClass(String.class);
		commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
	}

	private List<Comment> generateComments() {
		ArrayList<Comment> comments = new ArrayList<>();

		comments.add(new Comment(1, 1, "Hello World"));
		comments.add(new Comment(1, 2, "Hello World 2"));
		comments.add(new Comment(2, 2, "Hello World 3"));

		return comments;
	}

	@Test
	public void shouldFindAllCommentsByUserId() throws Exception {
		int userId = 1;
		String namedQueryIdParameter = "userId";

		when(entityManager.createNativeQuery(anyString(), any(Class.class))).thenReturn(t);
		when(t.getResultList()).thenReturn(comments);

		commentController.findCommentsByUserId(userId);

		verify(t, times(1)).setParameter(argumentCaptor.capture(), namedQueryIdCaptor.capture());
		verify(t, times(1)).getResultList();
		assertThat(argumentCaptor.getValue(), is(userId));
		assertThat(namedQueryIdCaptor.getValue(), is(namedQueryIdParameter));
		verifyNoMoreInteractions(t);

		verify(entityManager, times(1)).createNativeQuery(queryParameterCaptor.capture(), any(Class.class));
		assertThat(queryParameterCaptor.getValue(), is(Comment.FIND_COMMENTS_BY_USER_ID));
		verifyNoMoreInteractions(entityManager);
	}

	/**
	 * A new comment should be returned if no comments were actually found.
	 *
	 * @throws Exception
	 */
	@Test
	public void shouldReturnNewCommentIfNoCommentWasFoundForCommentId() throws Exception {
		int commentId = 1;
		String namedQueryIdParameter = "commentId";

		when(entityManager.createNativeQuery(anyString(), any(Class.class))).thenReturn(t);
		when(t.getResultList()).thenReturn(emptyList);

		Comment comment = commentController.findCommentById(commentId);

		verify(t, times(1)).setParameter(argumentCaptor.capture(), namedQueryIdCaptor.capture());
		verify(t, times(1)).getResultList();
		assertThat(argumentCaptor.getValue(), is(commentId));
		assertThat(namedQueryIdCaptor.getValue(), is(namedQueryIdParameter));
		verifyNoMoreInteractions(t);

		verify(entityManager, times(1)).createNativeQuery(queryParameterCaptor.capture(), any(Class.class));
		assertThat(queryParameterCaptor.getValue(), is(Comment.FIND_COMMENT_BY_COMMENT_ID));
		verifyNoMoreInteractions(entityManager);

		assertThat(comment.getUserId(), is(0));
		assertThat(comment.getCommentId(), is(0));
		assertThat(comment.getCommentText(), is(nullValue()));
	}

	@Test
	public void shouldReturnCommentById() throws Exception {
		int commentId = 1;
		String namedQueryIdParameter = "commentId";

		when(entityManager.createNativeQuery(anyString(), any(Class.class))).thenReturn(t);
		when(t.getResultList()).thenReturn(comments);

		Comment comment = commentController.findCommentById(commentId);

		verify(t, times(1)).setParameter(argumentCaptor.capture(), namedQueryIdCaptor.capture());
		verify(t, times(1)).getResultList();
		assertThat(argumentCaptor.getValue(), is(commentId));
		assertThat(namedQueryIdCaptor.getValue(), is(namedQueryIdParameter));
		verifyNoMoreInteractions(t);

		verify(entityManager, times(1)).createNativeQuery(queryParameterCaptor.capture(), any(Class.class));
		assertThat(queryParameterCaptor.getValue(), is(Comment.FIND_COMMENT_BY_COMMENT_ID));
		verifyNoMoreInteractions(entityManager);

		assertThat(comment.getUserId(), is(comments.get(0).getUserId()));
		assertThat(comment.getCommentId(), is(comments.get(0).getCommentId()));
		assertThat(comment.getCommentText(), is(comments.get(0).getCommentText()));
	}

	@Test
	public void shouldFindAllCommentsByPostId() throws Exception {
		int postId = 1;
		String namedQueryIdParameter = "postId";

		when(entityManager.createNativeQuery(anyString(), any(Class.class))).thenReturn(t);
		when(t.getResultList()).thenReturn(comments);

		List<Comment> commentsReturned = commentController.findCommentsByPostId(postId);

		verify(t, times(1)).setParameter(argumentCaptor.capture(), namedQueryIdCaptor.capture());
		verify(t, times(1)).getResultList();
		assertThat(argumentCaptor.getValue(), is(postId));
		assertThat(namedQueryIdCaptor.getValue(), is(namedQueryIdParameter));
		verifyNoMoreInteractions(t);

		verify(entityManager, times(1)).createNativeQuery(queryParameterCaptor.capture(), any(Class.class));
		assertThat(queryParameterCaptor.getValue(), is(Comment.FIND_COMMENTS_BY_POST_ID));
		verifyNoMoreInteractions(entityManager);

		assertThat(commentsReturned.size(), is(comments.size()));
		assertThat(commentsReturned.get(0).getCommentId(), is(comments.get(0).getCommentId()));
	}

	@Test
	public void shouldCreateNewCommentBasedOnInput() throws Exception {
		int userId = 1;
		int postId = 1;
		String text = "New Comment!";

		commentController.createNewComment(userId, postId, text);

		verify(entityManager, times(1)).persist(commentArgumentCaptor.capture());
		verifyNoMoreInteractions(entityManager);
		assertThat(commentArgumentCaptor.getValue().getUserId(), is(userId));
		assertThat(commentArgumentCaptor.getValue().getPostId(), is(postId));
		assertThat(commentArgumentCaptor.getValue().getCommentText(), is(text));
	}
}