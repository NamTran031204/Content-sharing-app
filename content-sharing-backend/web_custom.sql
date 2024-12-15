create DATABASE web_custom;

create TABLE boards(
    id int AUTO_INCREMENT PRIMARY KEY,
    user_id int NOT NULL,
    board_name varchar(30) NOT NULL,
    board_description varchar(100) default "",
    create_time datetime
);

create table followers(
    id int AUTO_INCREMENT PRIMARY KEY,
    follower_id int NOT NULL,
    following_id int NOT NULL,
    followed_at datetime
 );
 
 create table pictures(
     id int AUTO_INCREMENT PRIMARY KEY,
     user_id int NOT NULL,
     board_id int default 0,
     image_url varchar(300) default "",
     title varchar(200) default "",
     image_description varchar(300) default "",
     create_time datetime
);

create table picture_tag(
    id int AUTO_INCREMENT PRIMARY KEY,
    picture_id int NOT NULL,
    tag_id INT NOT NULL
);

create table reacts(
    id int AUTO_INCREMENT PRIMARY KEY,
    user_id int NOT NULL,
    picture_id int NOT NULL,
    type_react varchar(10) NOT NULL,
    content varchar(100) default "",
    create_time datetime
);

create table tags(
    id int AUTO_INCREMENT PRIMARY KEY,
    tag_name varchar(30)
);

create table users(
    id int AUTO_INCREMENT PRIMARY KEY,
    username varchar(100) NOT NULL,
    email varchar(100),
    phone_number varchar(100),
    name varchar(100) default "",
    description varchar(100),
    user_password varchar(300),
    profile_picture varchar(100),
    create_time datetime
);

alter table boards
add constraint fk_boards_users FOREIGN key (user_id) REFERENCES users(id);
alter table boards
add constraint fk_boards_pictures FOREIGN key (picture_id) REFERENCES pictures(id);

alter table followers
add constraint fk_followers_users FOREIGN key (follower_id) REFERENCES users(id);
alter table followers
add constraint fk_following_users FOREIGN key (following_id) REFERENCES users(id);

alter table picture_tag
add constraint fk_pictag_tag FOREIGN key (tag_id) REFERENCES tags(id);
alter table picture_tag
add constraint fk_pictag_pictures FOREIGN key (picture_id) REFERENCES pictures(id);

alter table reacts
add constraint fk_react_users FOREIGN key (user_id) REFERENCES users(id);
alter table reacts
add constraint fk_react_pictures FOREIGN key (picture_id) REFERENCES pictures(id);

ALTER TABLE users ADD COLUMN role_id INT;
CREATE TABLE roles(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL
);
ALTER TABLE users ADD FOREIGN KEY (role_id) REFERENCES roles(id);

CREATE TABLE tokens(
    id INT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(255) UNIQUE NOT NULL,
    token_type VARCHAR(50) NOT NULL,
    expiration_date DATETIME,
    revorked TINYINT(1) NOT NULL,
    expired TINYINT(1) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);