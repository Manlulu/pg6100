package no.espenschatten.exercise.hackernews.comment.boundary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import no.espenschatten.exercise.hackernews.comment.controller.CommentController;
import no.espenschatten.exercise.hackernews.comment.entity.Comment;
import no.espenschatten.exercise.hackernews.util.controller.ReputationController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommentBoundaryTest {

	@InjectMocks
	private CommentBoundary commentBoundary;

	@Mock
	private CommentController commentController;

	@Mock
	private ReputationController reputationController;

	private ArgumentCaptor<Comment> commentCaptor;

	@Before
	public void setUp() {
		commentCaptor = ArgumentCaptor.forClass(Comment.class);
	}

	@Test
	public void shouldAddNewComment() throws Exception {
		ArgumentCaptor<Integer> userIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<Integer> postIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
		ArgumentCaptor<String> textArgumentCaptor = ArgumentCaptor.forClass(String.class);
		int userId = 1;
		int postId = 2;
		String text = "hello comment";

		commentBoundary.putComment(userId, postId, text);
		verify(commentController, times(1)).createNewComment(userIdArgumentCaptor.capture(), postIdArgumentCaptor.capture(), textArgumentCaptor.capture());
		verifyNoMoreInteractions(commentController);
		assertThat(userIdArgumentCaptor.getValue(), is(userId));
		assertThat(postIdArgumentCaptor.getValue(), is(postId));
		assertThat(textArgumentCaptor.getValue(), is(text));
	}

	@Test
	public void shouldVoteUpComment() throws Exception {
		int commentId = 1;
		int userId = 1;
		Comment comment = new Comment(userId,1, "hello comment");
		when(commentController.findCommentById(anyInt())).thenReturn(comment);

		commentBoundary.voteUpComment(commentId);

		verify(reputationController, times(1)).increaseReputation(commentCaptor.capture());
		verify(commentController, times(1)).findCommentById(commentId);
		verifyNoMoreInteractions(reputationController);
		verifyNoMoreInteractions(commentController);

		assertThat(commentCaptor.getValue().getUserId(), is(userId));

	}

	@Test
	public void shouldVoteDownComment() throws Exception {
		int commentId = 1;
		int userId = 1;
		Comment comment = new Comment(userId,1, "hello comment");
		when(commentController.findCommentById(anyInt())).thenReturn(comment);

		commentBoundary.voteDownComment(commentId);

		verify(reputationController, times(1)).decreaseReputation(commentCaptor.capture());
		verify(commentController, times(1)).findCommentById(commentId);
		verifyNoMoreInteractions(reputationController);
		verifyNoMoreInteractions(commentController);

		assertThat(commentCaptor.getValue().getUserId(), is(userId));
	}

	@Test
	public void shouldGetAllCommentsByUser() throws Exception {
		ArgumentCaptor<Integer> userIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
		int userId = 1;
		commentBoundary.getCommentsByUserId(userId);
		verify(commentController, times(1)).findCommentsByUserId(userIdArgumentCaptor.capture());
		verifyNoMoreInteractions(commentController);
		assertThat(userIdArgumentCaptor.getValue(), is(userId));
	}

	@Test
	public void shouldGetAllCommentsByPostId() throws Exception {
		ArgumentCaptor<Integer> userIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
		int postId = 1;
		commentBoundary.getCommentsByPostId(postId);
		verify(commentController, times(1)).findCommentsByPostId(userIdArgumentCaptor.capture());
		verifyNoMoreInteractions(commentController);
		assertThat(userIdArgumentCaptor.getValue(), is(postId));
	}
}