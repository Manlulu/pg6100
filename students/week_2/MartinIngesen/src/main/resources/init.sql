INSERT INTO User (id, email, password, type) VALUES (1, 'test@test.test', 'testABC123123', 'ADMIN');
INSERT INTO User (id, email, password, type) VALUES (2, 'test1@test.test', 'testABC123123', 'USER');
INSERT INTO User (id, email, password, type) VALUES (3, 'test2@test.test', 'testABC123123', 'USER');
INSERT INTO User (id, email, password, type) VALUES (4, 'test3@test.test', 'testABC123123', 'USER');
INSERT INTO User (id, email, password, type) VALUES (5, 'test4@test.test', 'testABC123123', 'USER');

INSERT INTO Post (id, posted, content, title, type, FK_POSTER) VALUES (1, CURRENT_TIMESTAMP, 'This is a post!', 'Welcome to the first post', 'TEXT', 1);
INSERT INTO Post (id, posted, content, title, type, FK_POSTER) VALUES (2, CURRENT_TIMESTAMP, 'This is another post!', 'Post contnet!', 'TEXT', 2);
INSERT INTO Post (id, posted, content, title, type, FK_POSTER) VALUES (3, CURRENT_TIMESTAMP, 'This is a link post!', 'http://google.com', 'LINK', 3);
INSERT INTO Post (id, posted, content, title, type, FK_POSTER) VALUES (4, CURRENT_TIMESTAMP, 'This is yet another post!', 'Post content!', 'TEXT', 4);
INSERT INTO Post (id, posted, content, title, type, FK_POSTER) VALUES (5, CURRENT_TIMESTAMP, 'Ze link!!', 'http://google.com', 'LINK', 5);

INSERT INTO Comment (id, commented, content, FK_COMMENTER, FK_PARENT, FK_POST) VALUES (1, CURRENT_TIMESTAMP , 'This is a comment!', 1, null, 1);
INSERT INTO Comment (id, commented, content, FK_COMMENTER, FK_PARENT, FK_POST) VALUES (2, CURRENT_TIMESTAMP, 'This is a sub comment!', 2, 1, 1);
INSERT INTO Comment (id, commented, content, FK_COMMENTER, FK_PARENT, FK_POST) VALUES (3, CURRENT_TIMESTAMP, 'This is a new comment!', 3, null, 1);
INSERT INTO Comment (id, commented, content, FK_COMMENTER, FK_PARENT, FK_POST) VALUES (4, CURRENT_TIMESTAMP, 'This is a comment!', 4, null, 2);
INSERT INTO Comment (id, commented, content, FK_COMMENTER, FK_PARENT, FK_POST) VALUES (5, CURRENT_TIMESTAMP, 'This is a comment!', 5, null, 3);

INSERT INTO Vote (id, type, FK_COMMENT, FK_POST, FK_USER) VALUES (1, 'UP',   null, 1,    1);
INSERT INTO Vote (id, type, FK_COMMENT, FK_POST, FK_USER) VALUES (2, 'UP',   1,    null, 2);
INSERT INTO Vote (id, type, FK_COMMENT, FK_POST, FK_USER) VALUES (3, 'DOWN', null, 1,    3);
INSERT INTO Vote (id, type, FK_COMMENT, FK_POST, FK_USER) VALUES (4, 'DOWN', 2,    null, 3);
INSERT INTO Vote (id, type, FK_COMMENT, FK_POST, FK_USER) VALUES (5, 'UP',   null, 5,    5);