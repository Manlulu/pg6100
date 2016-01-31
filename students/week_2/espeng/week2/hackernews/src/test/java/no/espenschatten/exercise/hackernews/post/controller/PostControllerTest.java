package no.espenschatten.exercise.hackernews.post.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import no.espenschatten.exercise.hackernews.post.entity.Post;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.persistence.EntityManager;

public class PostControllerTest {

	@InjectMocks
	private PostController postController;

	@Mock
	private EntityManager entityManager;

	private ArgumentCaptor<Post> postCaptor;

	@Before
	public void setUp() {
		postCaptor = ArgumentCaptor.forClass(Post.class);
	}

	@Test
	public void shouldCreateNewPost() throws Exception {
		int userId = 1;
		String postText = "Hello Post text";

		postController.createNewPost(1, postText);

		verify(entityManager, times(1)).persist(postCaptor.capture());
		assertThat(postCaptor.getValue().getUserId(), is(userId));
		assertThat(postCaptor.getValue().getContent(), is(postText));
	}
}