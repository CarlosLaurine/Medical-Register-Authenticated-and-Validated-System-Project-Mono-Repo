drop table if exists tb_user CASCADE;

create table tb_user (
    id bigint not null auto_increment,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    primary key (id)
    );
    
alter table tb_user 
       add constraint UK_4vih17mube9j7cqyjlfbcrk4m unique (email);
