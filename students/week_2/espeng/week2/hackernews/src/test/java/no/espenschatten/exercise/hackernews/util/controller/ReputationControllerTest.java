package no.espenschatten.exercise.hackernews.util.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import no.espenschatten.exercise.hackernews.comment.entity.Comment;
import no.espenschatten.exercise.hackernews.post.entity.Post;
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

@RunWith(MockitoJUnitRunner.class)
public class ReputationControllerTest {

	@InjectMocks
	private ReputationController reputationController;

	@Mock
	private EntityManager entityManager;

	private Comment comment;
	private Post post;
	private ArgumentCaptor<Comment> commentCaptor;
	private ArgumentCaptor<Post> postCaptor;

	@Before
	public void setUp() {
		comment = new Comment(1,1,"Hello World");
		post = new Post(1, "Massive article with many subjects.");
		commentCaptor = ArgumentCaptor.forClass(Comment.class);
		postCaptor = ArgumentCaptor.forClass(Post.class);
	}

	@Test
	public void shouldIncreaseReputationOfComment() throws Exception {
		int votesBeforeReputationIncrease = comment.getVotes();

		reputationController.increaseReputation(comment);

		verify(entityManager, times(1)).refresh(commentCaptor.capture());
		verifyNoMoreInteractions(entityManager);
		assertThat(commentCaptor.getValue().getVotes(), is(votesBeforeReputationIncrease + 1));

	}

	@Test
	public void shouldDecreaseReputationOfComment() throws Exception {
		int votesBeforeReputationDecrease = comment.getVotes();

		reputationController.decreaseReputation(comment);

		verify(entityManager, times(1)).refresh(commentCaptor.capture());
		verifyNoMoreInteractions(entityManager);
		assertThat(commentCaptor.getValue().getVotes(), is(votesBeforeReputationDecrease - 1));

	}

	@Test
	public void shouldIncreaseReputationOfPost() throws Exception {
		int votesBeforeReputationIncrease = post.getVotes();

		reputationController.increaseReputation(post);

		verify(entityManager, times(1)).refresh(postCaptor.capture());
		verifyNoMoreInteractions(entityManager);
		assertThat(postCaptor.getValue().getVotes(), is(votesBeforeReputationIncrease + 1));
	}

	@Test
	public void shouldDecreaseReputationOfPost() throws Exception {
		int votesBeforeReputationDecrease = post.getVotes();

		reputationController.decreaseReputation(post);

		verify(entityManager, times(1)).refresh(postCaptor.capture());
		verifyNoMoreInteractions(entityManager);
		assertThat(postCaptor.getValue().getVotes(), is(votesBeforeReputationDecrease - 1));
	}

	@Test
	public void testGetTopCommentOnPost() throws Exception {
		Post post = new Post(1, "I have many comments on my article!");
		post.setComments(generateComments());

		Comment comment = reputationController.getTopCommentOnPost(post);

		assertThat(comment.getVotes(), is(9));
	}

	private List<Comment> generateComments() {
		List<Comment> comments = new ArrayList<>();
		comments.add(new Comment(124,134,"text"));
		comments.add(commentWithOneVote());
		comments.add(commentWithNineVotes());
		comments.add(new Comment(122,132,"text"));
		return comments;
	}

	private Comment commentWithNineVotes() {
		Comment comment = new Comment(30,20,"text");
		comment.setVotes(9);
		return comment;
	}

	private Comment commentWithOneVote() {
		Comment comment = new Comment(40,40,"text");
		comment.setVotes(1);
		return comment;
	}
}