create table chat_messages (
       id bigint not null,
        content varchar(255),
        chat_id bigint,
        user_id bigint,
        primary key (id));
create table chats (
       id bigint not null,
        name varchar(255),
        primary key (id));
create table users_chats (
       chat_id bigint not null,
        user_id bigint not null);
create sequence chat_id_seq start with 1 increment by 1;
create sequence chat_message_id_seq start with 1 increment by 1;
alter table if exists chat_messages 
       add constraint chat_id_fk 
       foreign key (chat_id) 
       references chats;
alter table if exists chat_messages 
       add constraint user_id_fk 
       foreign key (user_id) 
       references users;
alter table if exists users_chats 
       add constraint user_id 
       foreign key (user_id) 
       references users;
alter table if exists users_chats 
       add constraint chat_id 
       foreign key (chat_id) 
       references chats;
alter table if exists chats 
       add constraint chat_name_uq unique (name);