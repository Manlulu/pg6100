-- noinspection SqlNoDataSourceInspectionForFile

INSERT INTO User (id, username, email, password) VALUES(1001, 'user01', 'user01@email.com', 'password_user01_');
INSERT INTO User (id, username, email, password) VALUES(1002, 'user02', 'user02@email.com', 'password_user02_');
INSERT INTO User (id, username, email, password) VALUES(1003, 'user03', 'user03@email.com', 'password_user03_');
INSERT INTO User (id, username, email, password) VALUES(1004, 'user04', 'user04@email.com', 'password_user04_');

INSERT INTO Post (id, title, contents) VALUES (1001, 'title01', 'contents with text of post number 01');
INSERT INTO Post (id, title, contents) VALUES (1002, 'title02', 'contents with text of post number 02');
INSERT INTO Post (id, title, contents) VALUES (1003, 'title03', 'contents with text of post number 03');
INSERT INTO Post (id, title, contents) VALUES (1004, 'title04', 'contents with text of post number 04');

INSERT INTO Comment (id, contents) VALUES (1001, 'some great comment');
INSERT INTO Comment (id, contents) VALUES (1002, 'another great comment');
INSERT INTO Comment (id, contents) VALUES (1003, 'a third great comment');

-- (id integer not null, vote boolean not null, COMMENT_ID integer, POST_ID integer, USER_ID integer not null, primary key (id))
INSERT INTO Vote (id, vote, COMMENT_ID, POST_ID, USER_ID) VALUES (1001, 1, 1001, NULL , 1002);
INSERT INTO Vote (id, vote, COMMENT_ID, POST_ID, USER_ID) VALUES (1002, 0, 1003, NULL , 1002);
INSERT INTO Vote (id, vote, COMMENT_ID, POST_ID, USER_ID) VALUES (1003, 0, NULL , 1001, 1002);
INSERT INTO Vote (id, vote, COMMENT_ID, POST_ID, USER_ID) VALUES (1004, 1, NULL , 1003, 1002);