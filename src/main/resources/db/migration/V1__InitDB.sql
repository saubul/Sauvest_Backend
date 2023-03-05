create table roles (
       id bigint not null,
        role_name varchar(255),
        primary key (id));
create table users (
       id bigint not null,
        email varchar(255),
        enabled boolean not null,
        name varchar(255),
        password varchar(255),
        surname varchar(255),
        username varchar(255),
        sso_token varchar(255) not null,
        primary key (id));
create table users_roles (
       user_id bigint not null,
        role_id bigint not null,
        primary key (user_id, role_id));
create table verification_tokens (
       id bigint not null,
        date_created date,
        token varchar(255),
        user_id bigint,
        primary key (id));
alter table if exists users drop constraint if exists user_email_uq;
alter table if exists users add constraint user_email_uq unique (email);
alter table if exists users drop constraint if exists user_username_uq;
alter table if exists users add constraint user_username_uq unique (username);
create sequence role_id_seq start with 1 increment by 1;
create sequence user_id_seq start with 1 increment by 1;
create sequence verification_token_id_seq start with 1 increment by 1;
alter table if exists users_roles 
       add constraint role_id_fk 
       foreign key (role_id) 
       references roles;
alter table if exists users_roles 
       add constraint user_id_fk 
       foreign key (user_id) 
       references users;
alter table if exists verification_tokens 
       add constraint user_id_fk 
       foreign key (user_id) 
       references users;
create table subscriptions (
       id bigint not null,
        subsciprtion_user_id bigint,
        user_id bigint,
        primary key (id));
create sequence subscription_id_seq start with 1 increment by 1;
    alter table if exists subscriptions 
       add constraint subsciprtion_user_id_fk 
       foreign key (subsciprtion_user_id) 
       references users;
    alter table if exists subscriptions 
       add constraint user_id_fk 
       foreign key (user_id) 
       references users;
create table comments (
       id bigint not null,
        content varchar(255),
        creation_date_time timestamp(6),
        post_id bigint,
        user_id bigint,
        primary key (id));
create table posts (
       id bigint not null,
        content varchar(500),
        creation_date_time timestamp(6),
        user_id bigint,
        img_path varchar(255),
        vote_count bigint default 0,
        primary key (id));
create sequence comment_id_seq start with 1 increment by 1;
create sequence post_id_seq start with 1 increment by 1;
alter table if exists comments 
       add constraint post_id_fk 
       foreign key (post_id) 
       references posts;
alter table if exists comments 
       add constraint user_id_fk 
       foreign key (user_id) 
       references users;
alter table if exists posts 
       add constraint user_id_fk 
       foreign key (user_id) 
       references users;
create table votes (
       id bigint not null,
        post_id bigint,
        user_id bigint,
        primary key (id));
create sequence vote_id_seq start with 1 increment by 1;
alter table if exists votes 
       add constraint post_id_fk 
       foreign key (post_id) 
       references posts;
alter table if exists votes 
       add constraint user_id_fk 
       foreign key (user_id) 
       references users;
INSERT INTO roles(id, role_name) values(nextval('role_id_seq'), 'ROLE_USER'), (nextval('role_id_seq'), 'ROLE_MODERATOR'), (nextval('role_id_seq'), 'ROLE_ADMIN');