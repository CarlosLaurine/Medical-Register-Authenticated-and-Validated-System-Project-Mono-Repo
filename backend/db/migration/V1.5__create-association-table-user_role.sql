drop table if exists tb_user_role CASCADE;

create table tb_user_role (
     user_id bigint not null,
     role_id bigint not null,
     primary key (user_id, role_id),
     foreign key (user_id) 
     references tb_user(id),
     foreign key (role_id) 
     references tb_role(id)
    );
