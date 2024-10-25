create database web_custom

use web_custom

create table users(
	id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(30),
    phone_number VARCHAR(30),
    user_password VARCHAR(30) NOT NULL,
    profile_picture VARCHAR(200),
    create_time DATETIME NOT NULL,
    PRIMARY KEY(ID)
);

CREATE TABLE boards(
	id INT NOT NULL auto_increment,
    user_id INT NOT NULL,
    board_name VARCHAR(20) NOT NULL,
    board_description VARCHAR(300),
    create_time datetime NOT NULL,
    PRIMARY KEY(id)
);


CREATE TABLE pictures(
	ID INT NOT NULL AUTO_INCREMENT,
    BOARD_ID INT NOT NULL,
    USER_ID INT NOT NULL,
    IMAGE_URL VARCHAR(100) NOT NULL,
    TILTLE VARCHAR(20),
    CREATE_TIME DATETIME NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE reacts(
	id INT NOT NULL auto_increment,
    user_id INT NOT NULL,
    picture_id INT NOT NULL,
    type_react VARCHAR(10) NOT NULL,
    content VARCHAR(300),
    create_time DATETIME NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE followers(
	id INT NOT NULL AUTO_INCREMENT,
    follower_id INT NOT NULL,
    following_id INT NOT NULL,
    followed_at DATETIME NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE notifications(
	id INT NOT NULL auto_increment,
    user_id INT NOT NULL,
    type_react VARCHAR(30) NOT NULL,
    reference_id INT NOT NULL,
    message VARCHAR(30) NOT NULL,
    create_time DATETIME NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE tags(
	id INT NOT NULL AUTO_INCREMENT,
    tag_name VARCHAR(15),
    PRIMARY KEY(id)
);

CREATE TABLE picture_tag(
	picture_id INT NOT NULL,
    tag_id INT NOT NULL
);

ALTER TABLE users ADD COLUMN email VARCHAR(30);

ALTER TABLE boards
ADD CONSTRAINT FK_boards_users FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE pictures 
ADD CONSTRAINT FK_pictures_boards FOREIGN KEY (board_id) REFERENCES boards(id);
ALTER TABLE pictures
ADD CONSTRAINT FK_pictures_users FOREIGN KEY (user_id) REFERENCES USERS(id);

ALTER TABLE reacts
ADD CONSTRAINT FK_reacts_users FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE reacts 
ADD CONSTRAINT FK_reacts_pictures foreign key (picture_id) REFERENCES pictures(id);

ALTER TABLE followers
ADD CONSTRAINT FK_followers_users FOREIGN KEY (follower_id) REFERENCES users(id);
ALTER TABLE followers
ADD CONSTRAINT FK_following_users FOREIGN KEY (following_id) REFERENCES users(id);

ALTER TABLE notifications
ADD CONSTRAINT FK_notifications_users FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE notifications
ADD CONSTRAINT FK_notifications_users2 FOREIGN KEY (reference_id) REFERENCES users(id);
-- ALTER TABLE notifications
-- ADD CONSTRAINT FK_notifications_reacts FOREIGN KEY (TYPE_REACT) REFERENCES REACTS(TYPE_REACT);

ALTER TABLE picture_tag
ADD CONSTRAINT FK_pictag_picture FOREIGN KEY (picture_id) REFERENCES pictures(id);
ALTER TABLE picture_tag
ADD CONSTRAINT FK_pictag_tag FOREIGN KEY (tag_id) REFERENCES tags(id);

ALTER TABLE pictures
ADD COLUMN image_description varchar(300);

