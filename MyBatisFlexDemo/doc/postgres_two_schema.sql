create table goods_order
(
    id          integer generated always as identity
        constraint order_pk
            primary key,
    goods_id    integer               not null,
    total_price numeric(6, 2)         not null,
    number      integer               not null,
    is_delete   boolean default false not null
);

comment on table goods_order is '订单';

comment on column goods_order.goods_id is '商品';

comment on column goods_order.total_price is '总价';

comment on column goods_order.number is '个数';

comment on column goods_order.is_delete is '逻辑删除字段';

alter table goods_order
    owner to mybatis_flex_two;