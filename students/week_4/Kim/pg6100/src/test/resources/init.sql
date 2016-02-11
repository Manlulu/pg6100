

INSERT INTO USER(userId, userName, country, hash, salt) VALUES (1, 'testUser1', 'NORWAY', 'abc', 'def');
INSERT INTO USER(userId, userName, country, hash, salt) VALUES (2, 'testUser2', 'NORWAY', 'aab', 'def');
INSERT INTO USER(userId, userName, country, hash, salt) VALUES (3, 'testUser3', 'OTHER', 'fdert', 'def');




INSERT INTO COMMENT(commentId, name, body, votes, CREATEDAT, FK_USER) VALUES (1, 'commentName1', 'Body1', 0, '2004-11-11 11:20:23.0', 1);
INSERT INTO COMMENT(commentId, name, body, votes, CREATEDAT, FK_USER) VALUES (2, 'commentName2', 'Body2', 0, '2004-11-11 11:20:23.0', 2);
INSERT INTO COMMENT(commentId, name, body, votes, CREATEDAT, FK_USER) VALUES (3, 'commentName3', 'Body3', 0, '2004-11-11 11:20:23.0', 3);





INSERT INTO NEWS(newsId, name, article, votes, CREATEDAT, rating) VALUES (1, 'newsName1', 'Article1', 0, '2004-11-11 11:20:23.0', 0);
INSERT INTO NEWS(newsId, name, article, votes, CREATEDAT, rating) VALUES (2, 'newsName2', 'Article2', 0, '2004-11-11 11:20:23.0', 0);
INSERT INTO NEWS(newsId, name, article, votes, CREATEDAT, rating) VALUES (3, 'newsName3', 'Article2', 0, '2004-11-11 11:20:23.0', 0);
