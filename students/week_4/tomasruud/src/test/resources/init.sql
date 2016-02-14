INSERT INTO post_votes(post_id, upvote, user_id) VALUES(70, true, 60);
INSERT INTO post_votes(post_id, upvote, user_id) VALUES(70, true, 61);
INSERT INTO post_votes(post_id, upvote, user_id) VALUES(70, false, 62);
INSERT INTO post_votes(post_id, upvote, user_id) VALUES(71, false, 60);
INSERT INTO post_votes(post_id, upvote, user_id) VALUES(71, true, 62);
INSERT INTO post_votes(post_id, upvote, user_id) VALUES(72, true, 62);
INSERT INTO post_votes(post_id, upvote, user_id) VALUES(72, true, 61);
INSERT INTO post_votes(post_id, upvote, user_id) VALUES(72, true, 60);

INSERT INTO comment(id, creator_id, parentPost_id, content, created) VALUES(80, 60, 70, 'Text', '2015-12-24 19:00:00.000');
INSERT INTO comment(id, creator_id, parentPost_id, content, created) VALUES(81, 61, 70, 'Text', '2015-12-24 19:00:00.000');
INSERT INTO comment(id, creator_id, parentPost_id, content, created) VALUES(82, 60, 72, 'Text', '2015-12-24 19:00:00.000');
INSERT INTO comment(id, creator_id, parentPost_id, content, created) VALUES(83, 60, 72, 'Text', '2015-12-24 19:00:00.000');
INSERT INTO comment(id, creator_id, parentPost_id, content, created) VALUES(84, 60, 72, 'Text', '2015-12-24 19:00:00.000');
INSERT INTO comment(id, creator_id, parentPost_id, content, created) VALUES(85, 60, 72, 'Text', '2015-12-24 19:00:00.000');

INSERT INTO comment_votes(comment_id, upvote, user_id) VALUES(80, false, 60);
INSERT INTO comment_votes(comment_id, upvote, user_id) VALUES(80, true, 61);
