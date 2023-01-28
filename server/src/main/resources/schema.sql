CREATE TABLE IF NOT NULL EXISTS ANSWER (
       ANSWER_ID bigint NOT NULL AUTO_INCREMENT,
        CREATE_AT datetime(6),
        LAST_MODIFIED_at datetime(6),
        ANSWER_USER_ID bigint,
        CONTENT varchar(255) NOT NULL,
        CHAT_ID bigint,
        PRIMARY KEY (ANSWER_ID)
);


CREATE TABLE IF NOT NULL EXISTS calendar (
       calendar_id bigint NOT NULL auto_increment,
        created_at datetime(6),
        last_modified_at datetime(6),
        date datetime(6) NOT NULL,
        title varchar(100) NOT NULL,
        study_id bigint,
        PRIMARY KEY (calendar_id)
);


CREATE TABLE IF NOT NULL EXISTS chat (
       chat_id bigint NOT NULL auto_increment,
        created_at datetime(6),
        last_modified_at datetime(6),
        chat_user_id bigint NOT NULL,
        content longtext,
        is_closed_chat bit NOT NULL,
        study_id bigint,
        PRIMARY KEY (chat_id)
);


CREATE TABLE IF NOT NULL EXISTS message (
       message_id bigint NOT NULL auto_increment,
        created_at datetime(6),
        last_modified_at datetime(6),
        content varchar(300) NOT NULL,
        date_time datetime(6) NOT NULL,
        message_user_id bigint NOT NULL,
        study_id bigint,
        PRIMARY KEY (message_id)
);


CREATE TABLE IF NOT NULL EXISTS participant (
       participant_id bigint NOT NULL auto_increment,
        created_at datetime(6),
        last_modified_at datetime(6),
        join_state varchar(255),
        user_id bigint NOT NULL,
        username varchar(255) NOT NULL,
        calendar_id bigint,
        PRIMARY KEY (participant_id)
);


CREATE TABLE IF NOT NULL EXISTS STUDY (
       STUDY_ID bigint NOT NULL auto_increment,
       CREATED_AT datetime(6),
       LAST_MODIFIED_AT datetime(6),
       CONTENT varchar(255) NOT NULL,
       IMAGE varchar(255),
       LEADER_ID bigint NOT NULL,
       NOTICE varchar(255),
       OPEN_CLOSE bit,
       'procedure' bit,
       START_DATE date,
       SUMMARY varchar(255) NOT NULL,
       TEAM_NAME varchar(255) NOT NULL,
       WANT integer,
       PRIMARY KEY (STUDY_ID)
);


CREATE TABLE IF NOT NULL EXISTS study_day_of_week (
       study_study_id bigint NOT NULL,
        day_of_week varchar(255)
);


CREATE TABLE IF NOT NULL EXISTS study_requester (
       study_study_id bigint NOT NULL,
        requester bigint
);


CREATE TABLE IF NOT NULL EXISTS tag (
       tag_id bigint NOT NULL auto_increment,
        created_at datetime(6),
        last_modified_at datetime(6),
        name varchar(50) NOT NULL,
        PRIMARY KEY (tag_id)
);


CREATE TABLE IF NOT NULL EXISTS tag_study (
       tag_study_id bigint NOT NULL auto_increment,
        created_at datetime(6),
        last_modified_at datetime(6),
        study_id bigint,
        tag_id bigint,
        PRIMARY KEY (tag_study_id)
);


CREATE TABLE IF NOT NULL EXISTS tree (
       tree_id bigint NOT NULL auto_increment,
        created_at datetime(6),
        last_modified_at datetime(6),
        make_month integer,
        tree_image varchar(255),
        tree_point integer,
        study_id bigint,
        PRIMARY KEY (tree_id)
);


CREATE TABLE IF NOT NULL EXISTS user_study (
       user_study_id bigint NOT NULL auto_increment,
        created_at datetime(6),
        last_modified_at datetime(6),
        study_id bigint,
        user_id bigint,
        PRIMARY KEY (user_study_id)
);


CREATE TABLE IF NOT NULL EXISTS users (
       user_id bigint NOT NULL auto_increment,
        created_at datetime(6),
        last_modified_at datetime(6),
        email varchar(255) NOT NULL,
        img_url varchar(255),
        info longtext,
        refresh varchar(300),
        token varchar(300),
        username varchar(10) NOT NULL,
        PRIMARY KEY (user_id)
);


CREATE TABLE IF NOT NULL EXISTS USERS_ROLE (
       USERS_USER_ID bigint NOT NULL,
        ROLE varchar(255)
);