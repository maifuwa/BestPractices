create table goods
(
    id    integer generated always as identity
        constraint goods_pk
            primary key,
    name  varchar(20)   not null,
    price numeric(5, 2) not null
);

comment on table goods is '商品';

comment on column goods.name is '商品名字';

comment on column goods.price is '价格';

alter table goods
    owner to "githubCiCD";

