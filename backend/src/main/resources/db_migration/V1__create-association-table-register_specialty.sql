    
drop table if exists tb_register_specialty CASCADE 
    
create table tb_register_specialty (
   register_id bigint not null,
   specialty_id bigint not null,
   primary key (register_id, specialty_id)
    )
  
alter table tb_register_specialty 
   add constraint FKriqutpqn97c2u2ti67sf5u7jj 
   foreign key (specialty_id) 
   references tb_specialty
    
alter table tb_register_specialty 
   add constraint FKtkxaci1a59jfddhd2y8pmti7a 
   foreign key (register_id) 
   references tb_register
   
drop table if exists tb_register_specialty CASCADE    

   