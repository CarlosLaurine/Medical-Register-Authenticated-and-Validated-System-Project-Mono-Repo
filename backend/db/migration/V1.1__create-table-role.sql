drop table if exists tb_role CASCADE;
 
create table tb_role (
    id bigint not null auto_increment,
    authority varchar(255),
    primary key (id)
    );

