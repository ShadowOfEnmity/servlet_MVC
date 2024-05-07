create table goods
(
    id       bigint default nextval('goods_id_seq'::regclass) not null
        constraint goods_pk
            primary key,
    name     varchar(124),
    price    numeric(10, 2),
    quantity integer
);


create table users
(
    id       bigint default nextval('users_id_seq'::regclass) not null
        constraint id
            primary key,
    name     varchar(124)                                     not null,
    age      integer                                          not null,
    email    varchar(124)                                     not null,
    login    varchar(124)                                     not null,
    password varchar(32)                                      not null
);


create table carts
(
    user_id      bigint not null
        constraint carts_users_id_fk
            references users,
    product_name varchar(124),
    price        numeric(10, 2),
    quantity     integer,
    product_id   bigint not null
        constraint carts_goods_id_fk
            references goods,
    constraint carts_pk
        primary key (user_id, product_id)
);