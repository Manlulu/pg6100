package no.espenschatten.exercise.hackernews.util.comparator;

import no.espenschatten.exercise.hackernews.comment.entity.Comment;

import java.util.Comparator;

public class ReputationComparator implements Comparator<Comment> {
	@Override public int compare(Comment o1, Comment o2) {
		if(o2.getVotes() > o1.getVotes()){
			return 1;
		} else if(o2.getVotes() < o1.getVotes()) {
			return -1;
		}
		return 0;
	}
}
