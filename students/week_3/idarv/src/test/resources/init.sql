-- noinspection SqlNoDataSourceInspectionForFile

INSERT INTO User (id, username, email, hash, salt) VALUES(1001, 'user01', 'user01@email.com', '0b7d34e578e8a3bbf5ede7bc3660a88e9c21e5d33004e85e983ef731f464771a', '_!dlaoul3u4fs3s56ao07iifrfmq');
INSERT INTO User (id, username, email, hash, salt) VALUES(1002, 'user02', 'user02@email.com', '0b7d34e578e8a3bbf5ede7bc3660a88e9c21e5d33004e85e983ef731f464771a', '_!dlaoul3u4fs3s56ao07iifrfmq');
INSERT INTO User (id, username, email, hash, salt) VALUES(1003, 'user03', 'user03@email.com', '0b7d34e578e8a3bbf5ede7bc3660a88e9c21e5d33004e85e983ef731f464771a', '_!dlaoul3u4fs3s56ao07iifrfmq');
INSERT INTO User (id, username, email, hash, salt) VALUES(1004, 'user04', 'user04@email.com', '0b7d34e578e8a3bbf5ede7bc3660a88e9c21e5d33004e85e983ef731f464771a', '_!dlaoul3u4fs3s56ao07iifrfmq');

INSERT INTO Post (id, title, contents, publishedDate) VALUES (1001, 'title01', 'contents with text of post number 01', '2015-12-05 12:00:00.000');
INSERT INTO Post (id, title, contents, publishedDate) VALUES (1002, 'title02', 'contents with text of post number 02', '2016-01-05 12:00:00.000');
INSERT INTO Post (id, title, contents, publishedDate) VALUES (1003, 'title03', 'contents with text of post number 03', '2014-07-23 12:00:00.000');
INSERT INTO Post (id, title, contents, publishedDate) VALUES (1004, 'title04', 'contents with text of post number 04', '2016-01-24 12:00:00.000');

INSERT INTO Comment (id, contents) VALUES (1001, 'some great comment');
INSERT INTO Comment (id, contents) VALUES (1002, 'another great comment');
INSERT INTO Comment (id, contents) VALUES (1003, 'a third great comment');
INSERT INTO Comment (id, contents) VALUES (1004, 'a fourth even greater comment');

-- (id integer not null, vote boolean not null, COMMENT_ID integer, POST_ID integer, USER_ID integer not null, primary key (id))
INSERT INTO Vote (id, vote, COMMENT_ID, POST_ID, USER_ID) VALUES (1001, 1, 1001, NULL , 1002);
INSERT INTO Vote (id, vote, COMMENT_ID, POST_ID, USER_ID) VALUES (1005, 1, 1001, NULL , 1002);
INSERT INTO Vote (id, vote, COMMENT_ID, POST_ID, USER_ID) VALUES (1006, 1, 1001, NULL , 1002);
INSERT INTO Vote (id, vote, COMMENT_ID, POST_ID, USER_ID) VALUES (1002, -1, 1003, NULL , 1002);
INSERT INTO Vote (id, vote, COMMENT_ID, POST_ID, USER_ID) VALUES (1003, -1, NULL , 1001, 1002);
INSERT INTO Vote (id, vote, COMMENT_ID, POST_ID, USER_ID) VALUES (1004, 1, NULL , 1003, 1002);