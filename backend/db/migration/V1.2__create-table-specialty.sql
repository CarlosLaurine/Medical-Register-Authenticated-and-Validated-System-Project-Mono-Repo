drop table if exists tb_specialty CASCADE;

create table tb_specialty (
     id bigint not null auto_increment,
     deleted boolean not null,
     name varchar(255),
     primary key (id)
    );