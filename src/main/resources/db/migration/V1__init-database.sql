create sequence if not exists users_id_seq;

create table if not exists users
(
id           serial primary key,
email        varchar(255) unique not null,
password     text                not null,
profile_role varchar(255) check (profile_role in ('CUSTOMER', 'MANAGER', 'ADMIN')),

created_by   varchar(255),
updated_by   varchar(255),

created_at   timestamp           not null default current_timestamp,
updated_at   timestamp           not null default current_timestamp
);

