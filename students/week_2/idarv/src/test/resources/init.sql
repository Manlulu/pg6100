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