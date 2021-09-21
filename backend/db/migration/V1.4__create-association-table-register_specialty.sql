 drop table if exists tb_register_specialty CASCADE;
    
create table tb_register_specialty (
   register_id bigint not null,
   specialty_id bigint not null,
   primary key (register_id, specialty_id),
   foreign key (register_id) 
   references tb_register(id),
   foreign key (specialty_id) 
   references tb_specialty(id)
    );