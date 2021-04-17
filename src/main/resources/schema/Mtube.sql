-- user Table Create SQL
CREATE TABLE user
(
    `id`             BIGINT          NOT NULL    AUTO_INCREMENT COMMENT '아이디', 
    `name`           VARCHAR(45)     NOT NULL    COMMENT '채널설명', 
    `email`          VARCHAR(50)     NOT NULL    COMMENT '이메일', 
    `password`       VARCHAR(100)    NOT NULL    COMMENT '비밀번호', 
    `picture`        VARCHAR(50)     NOT NULL    COMMENT '사진', 
    `role`           VARCHAR(5)      NOT NULL    COMMENT '역할', 
    `isRemoved`      VARCHAR(5)      NOT NULL    COMMENT '삭제여부', 
    `created_date`   DATETIME        NOT NULL    COMMENT '생성시간', 
    `modified_date`  DATETIME        NOT NULL    COMMENT '수정시간', 
    CONSTRAINT  PRIMARY KEY (id)
);

ALTER TABLE user COMMENT '사용자';


-- user Table Create SQL
CREATE TABLE channel
(
    `id`               BIGINT         NOT NULL    AUTO_INCREMENT COMMENT '아이디', 
    `name`             VARCHAR(50)    NOT NULL    COMMENT '채널이름', 
    `description`      TEXT           NOT NULL    COMMENT '채널설명', 
    `subscribe_count`  INT            NOT NULL    COMMENT '구독자수', 
    `isBanned`         VARCHAR(5)     NOT NULL    COMMENT '벤 여부', 
    `created_date`     DATETIME       NOT NULL    COMMENT '생성시간', 
    `modified_date`    DATETIME       NOT NULL    COMMENT '수정시간', 
    `user_id`          BIGINT         NOT NULL    COMMENT '사용자', 
    CONSTRAINT  PRIMARY KEY (id)
);

ALTER TABLE channel COMMENT '채널';

ALTER TABLE channel
    ADD CONSTRAINT FK_channel_user_id_user_id FOREIGN KEY (user_id)
        REFERENCES user (id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE channel_playlist_name
(
    `id`             BIGINT         NOT NULL    AUTO_INCREMENT COMMENT '아이디', 
    `channel_id`     BIGINT         NOT NULL    COMMENT '채널', 
    `name`           VARCHAR(45)    NOT NULL    COMMENT '플레이리스트 이름', 
    `created_date`   DATETIME       NOT NULL    COMMENT '생성시간', 
    `modified_date`  DATETIME       NOT NULL    COMMENT '수정시간', 
    CONSTRAINT  PRIMARY KEY (id)
);

ALTER TABLE channel_playlist_name COMMENT '채널관리자의 플레이리스트';

ALTER TABLE channel_playlist_name
    ADD CONSTRAINT FK_channel_playlist_name_channel_id_channel_id FOREIGN KEY (channel_id)
        REFERENCES channel (id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE video_post
(
    `id`              BIGINT          NOT NULL    AUTO_INCREMENT COMMENT '아이디', 
    `channel_id`      BIGINT          NOT NULL    COMMENT '채널', 
    `category`        VARCHAR(20)     NOT NULL    COMMENT '카테고리', 
    `playlist`        BIGINT          NULL        COMMENT '재생목록', 
    `title`           VARCHAR(50)     NOT NULL    COMMENT '제목', 
    `video_file`      VARCHAR(200)    NOT NULL    COMMENT '비디오 파일', 
    `content`         TEXT            NOT NULL    COMMENT '내용', 
    `view_count`      INT             NOT NULL    COMMENT '조회수', 
    `permit_comment`  VARCHAR(5)      NOT NULL    COMMENT '댓글가능여부', 
    `isRemoved`       VARCHAR(5)      NOT NULL    COMMENT '삭제여부', 
    `temp`            VARCHAR(5)      NOT NULL    COMMENT '임시저장여부', 
    `created_date`    DATETIME        NOT NULL    COMMENT '생성시간', 
    `modified_date`   DATETIME        NOT NULL    COMMENT '수정시간', 
    CONSTRAINT  PRIMARY KEY (id)
);

ALTER TABLE video_post COMMENT '비디오';

ALTER TABLE video_post
    ADD CONSTRAINT FK_video_post_channel_id_channel_id FOREIGN KEY (channel_id)
        REFERENCES channel (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE video_post
    ADD CONSTRAINT FK_video_post_playlist_channel_playlist_name_id FOREIGN KEY (playlist)
        REFERENCES channel_playlist_name (id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE comment
(
    `id`             BIGINT          NOT NULL    AUTO_INCREMENT COMMENT '아이디', 
    `content`        VARCHAR(500)    NOT NULL    COMMENT '내용', 
    `user_id`        BIGINT          NOT NULL    COMMENT '작성자', 
    `like_count`     INT             NOT NULL    COMMENT '좋아요 수', 
    `dislike_count`  INT             NOT NULL    COMMENT '싫어요 수', 
    `heart`          VARCHAR(45)     NOT NULL    COMMENT '하트', 
    `parent`         BIGINT          NOT NULL    COMMENT '부모댓글', 
    `isRemoved`      VARCHAR(45)     NOT NULL    COMMENT '삭제여부', 
    `created_date`   DATETIME        NOT NULL    COMMENT '생성시간', 
    `modified_date`  DATETIME        NOT NULL    COMMENT '수정시간', 
    `video_post_id`  BIGINT          NOT NULL    COMMENT '포스트', 
    CONSTRAINT  PRIMARY KEY (id)
);

ALTER TABLE comment COMMENT '댓글';

ALTER TABLE comment
    ADD CONSTRAINT FK_comment_user_id_user_id FOREIGN KEY (user_id)
        REFERENCES user (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE comment
    ADD CONSTRAINT FK_comment_video_post_id_video_post_id FOREIGN KEY (video_post_id)
        REFERENCES video_post (id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE user_playlist_name
(
    `id`             BIGINT      NOT NULL    AUTO_INCREMENT COMMENT '아이디', 
    `name`           BIGINT      NOT NULL    COMMENT '플레이리스트 이름', 
    `user_id`        BIGINT      NOT NULL    COMMENT '유저', 
    `created_date`   DATETIME    NOT NULL    COMMENT '생성시간', 
    `modified_date`  DATETIME    NOT NULL    COMMENT '수정시간', 
    CONSTRAINT  PRIMARY KEY (id)
);

ALTER TABLE user_playlist_name COMMENT '유저의 플레이리스트';

ALTER TABLE user_playlist_name
    ADD CONSTRAINT FK_user_playlist_name_user_id_user_id FOREIGN KEY (user_id)
        REFERENCES user (id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE like_video
(
    `id`             BIGINT        NOT NULL    AUTO_INCREMENT COMMENT '아이디', 
    `user_id`        BIGINT        NOT NULL    COMMENT '유저', 
    `video_post_id`  BIGINT        NOT NULL    COMMENT '포스트', 
    `kind`           VARCHAR(7)    NOT NULL    COMMENT '종류', 
    `created_date`   DATETIME      NOT NULL    COMMENT '생성시간', 
    CONSTRAINT  PRIMARY KEY (id)
);

ALTER TABLE like_video COMMENT '비디오 포스트 좋아요';

ALTER TABLE like_video
    ADD CONSTRAINT FK_like_video_user_id_user_id FOREIGN KEY (user_id)
        REFERENCES user (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE like_video
    ADD CONSTRAINT FK_like_video_video_post_id_video_post_id FOREIGN KEY (video_post_id)
        REFERENCES video_post (id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE like_comment
(
    `id`            BIGINT        NOT NULL    AUTO_INCREMENT COMMENT '아이디', 
    `user_id`       BIGINT        NOT NULL    COMMENT '유저', 
    `comment_id`    BIGINT        NOT NULL    COMMENT '댓글', 
    `kind`          VARCHAR(7)    NOT NULL    COMMENT '종류', 
    `created_date`  DATETIME      NOT NULL    COMMENT '생성시간', 
    CONSTRAINT  PRIMARY KEY (id)
);

ALTER TABLE like_comment COMMENT '댓글 좋아요';

ALTER TABLE like_comment
    ADD CONSTRAINT FK_like_comment_comment_id_comment_id FOREIGN KEY (comment_id)
        REFERENCES comment (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE like_comment
    ADD CONSTRAINT FK_like_comment_user_id_user_id FOREIGN KEY (user_id)
        REFERENCES user (id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE user_playlist
(
    `id`                     BIGINT      NOT NULL    AUTO_INCREMENT COMMENT '아이디', 
    `video_post_id`          BIGINT      NOT NULL    COMMENT '포스트', 
    `user_playlist_name_id`  BIGINT      NOT NULL    COMMENT '플레이리스트이름', 
    `created_date`           DATETIME    NOT NULL    COMMENT '생성시간', 
    `modified_date`          DATETIME    NOT NULL    COMMENT '수정시간', 
    CONSTRAINT  PRIMARY KEY (id)
);

ALTER TABLE user_playlist COMMENT '유저의 플레이리스트 목록';

ALTER TABLE user_playlist
    ADD CONSTRAINT FK_user_playlist_video_post_id_video_post_id FOREIGN KEY (video_post_id)
        REFERENCES video_post (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE user_playlist
    ADD CONSTRAINT FK_user_playlist_user_playlist_name_id_user_playlist_name_id FOREIGN KEY (user_playlist_name_id)
        REFERENCES user_playlist_name (id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE subscribe
(
    `id`            BIGINT      NOT NULL    AUTO_INCREMENT COMMENT '아이디', 
    `user_id`       BIGINT      NOT NULL    COMMENT '유저', 
    `channel_id`    BIGINT      NOT NULL    COMMENT '채널', 
    `created_date`  DATETIME    NOT NULL    COMMENT '생성시간', 
    CONSTRAINT  PRIMARY KEY (id)
);

ALTER TABLE subscribe COMMENT '구독';

ALTER TABLE subscribe
    ADD CONSTRAINT FK_subscribe_user_id_user_id FOREIGN KEY (user_id)
        REFERENCES user (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE subscribe
    ADD CONSTRAINT FK_subscribe_channel_id_channel_id FOREIGN KEY (channel_id)
        REFERENCES channel (id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE user_watch_records
(
    `id`             BIGINT      NOT NULL    AUTO_INCREMENT COMMENT '아이디', 
    `video_post_id`  BIGINT      NOT NULL    COMMENT '포스트', 
    `user_id`        BIGINT      NOT NULL    COMMENT '유저', 
    `start_time`     DATETIME    NOT NULL    COMMENT '시작시간', 
    `end_time`       DATETIME    NOT NULL    COMMENT '종료시간', 
    `created_date`   DATETIME    NOT NULL    COMMENT '생성시간', 
    `modified_date`  DATETIME    NOT NULL    COMMENT '수정시간', 
    CONSTRAINT  PRIMARY KEY (id)
);

ALTER TABLE user_watch_records COMMENT '유저 시청 기록';

ALTER TABLE user_watch_records
    ADD CONSTRAINT FK_user_watch_records_video_post_id_video_post_id FOREIGN KEY (video_post_id)
        REFERENCES video_post (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE user_watch_records
    ADD CONSTRAINT FK_user_watch_records_user_id_user_id FOREIGN KEY (user_id)
        REFERENCES user (id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE alarm
(
    `id`             BIGINT      NOT NULL    AUTO_INCREMENT COMMENT '아이디', 
    `channel_id`     BIGINT      NOT NULL    COMMENT '채널', 
    `user_id`        BIGINT      NOT NULL    COMMENT '유저', 
    `created_date`   DATETIME    NOT NULL    COMMENT '생성시간', 
    `modified_date`  DATETIME    NOT NULL    COMMENT '수정시간', 
    CONSTRAINT  PRIMARY KEY (id)
);

ALTER TABLE alarm COMMENT '실시간 알림';

ALTER TABLE alarm
    ADD CONSTRAINT FK_alarm_channel_id_channel_id FOREIGN KEY (channel_id)
        REFERENCES channel (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE alarm
    ADD CONSTRAINT FK_alarm_user_id_user_id FOREIGN KEY (user_id)
        REFERENCES user (id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE report
(
    `id`             BIGINT      NOT NULL    AUTO_INCREMENT COMMENT '아이디', 
    `video_post_id`  BIGINT      NOT NULL    COMMENT '포스트', 
    `user_id`        BIGINT      NOT NULL    COMMENT '유저', 
    `reason)`        TEXT        NOT NULL    COMMENT '사유', 
    `created_date`   DATETIME    NOT NULL    COMMENT '등록시간', 
    `modified_date`  DATETIME    NOT NULL    COMMENT '수정시간', 
    CONSTRAINT  PRIMARY KEY (id)
);

ALTER TABLE report COMMENT '신고';

ALTER TABLE report
    ADD CONSTRAINT FK_report_video_post_id_video_post_id FOREIGN KEY (video_post_id)
        REFERENCES video_post (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE report
    ADD CONSTRAINT FK_report_user_id_user_id FOREIGN KEY (user_id)
        REFERENCES user (id) ON DELETE RESTRICT ON UPDATE RESTRICT;


-- user Table Create SQL
CREATE TABLE channel_playlist
(
    `id`             BIGINT      NOT NULL    AUTO_INCREMENT COMMENT '아이디', 
    `channel_id`     BIGINT      NOT NULL    COMMENT '채널', 
    `video_post_id`  BIGINT      NOT NULL    COMMENT '포스트', 
    `created_date`   DATETIME    NOT NULL    COMMENT '생성시간', 
    `modified_date`  DATETIME    NOT NULL    COMMENT '수정시간', 
    CONSTRAINT  PRIMARY KEY (id)
);

ALTER TABLE channel_playlist COMMENT '채널관리자의 플레이리스트 목록';

ALTER TABLE channel_playlist
    ADD CONSTRAINT FK_channel_playlist_channel_id_channel_id FOREIGN KEY (channel_id)
        REFERENCES channel (id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE channel_playlist
    ADD CONSTRAINT FK_channel_playlist_video_post_id_video_post_id FOREIGN KEY (video_post_id)
        REFERENCES video_post (id) ON DELETE RESTRICT ON UPDATE RESTRICT;


