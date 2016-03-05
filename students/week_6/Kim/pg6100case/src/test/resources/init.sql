INSERT INTO USER(userId, userName, country, hash, salt, firstName, lastName, state, city, street) VALUES (1, 'testUser1', 'NORWAY', 'abc', 'def', 'Ola', 'Normann', 'Norway', 'Årdal', 'Hydrovegen');
INSERT INTO USER(userId, userName, country, hash, salt, firstName, lastName, state, city, street) VALUES (2, 'testUser2', 'NORWAY', 'aab', 'def', 'Kari', 'Normann', 'Norway', 'Bergen', 'Fisketorget');
INSERT INTO USER(userId, userName, country, hash, salt, firstName, lastName, state, city, street) VALUES (3, 'testUser3', 'OTHER', 'fdert', 'def', 'Fornavn', 'Etternavn', 'Denmark', 'Somewhere', 'Danmark Street');

INSERT INTO CUSTOMER (customerId, firstName, middleName, surName, dateOfBirth, dateOfRegistration, state, city, street) VALUES(3, 'Ola', 'mitt', 'Normann', '1953-04-12 00:00:00.0', '2016-01-18', 'Norway', 'Oslo', 'Karl Johansgate');



INSERT INTO NEWS(newsId, name, article, votes, CREATEDAT, rating) VALUES (1, 'newsName1', 'Article1', 0, '2004-11-11 11:20:23.0', 0);
INSERT INTO NEWS(newsId, name, article, votes, CREATEDAT, rating) VALUES (2, 'newsName2', 'Article2', 0, '2004-11-11 11:20:23.0', 0);
INSERT INTO NEWS(newsId, name, article, votes, CREATEDAT, rating) VALUES (3, 'newsName3', 'Article2', 0, '2004-11-11 11:20:23.0', 0);




INSERT INTO COMMENT(commentId, body, createdAt, updatedAt, votes, news_newsId, user_userId, FK_COMMENT, FK_COMMENT_COMMENT) VALUES (1, 'Body1', '2004-11-11 11:20:23.0','2004-11-11 11:20:35.0', 0, 1, 1, null, null);
INSERT INTO COMMENT(commentId, body, createdAt, updatedAt, votes, news_newsId, user_userId, FK_COMMENT, FK_COMMENT_COMMENT) VALUES (2, 'Body2', '2006-11-11 11:20:23.0','2007-11-11 11:20:35.0', 0, 2, 2, 1, null);
INSERT INTO COMMENT(commentId, body, createdAt, updatedAt, votes, news_newsId, user_userId, FK_COMMENT, FK_COMMENT_COMMENT) VALUES (3, 'Body3', '2008-11-11 11:20:23.0','2009-11-11 11:20:35.0', 0, 3, 3, 2, null);