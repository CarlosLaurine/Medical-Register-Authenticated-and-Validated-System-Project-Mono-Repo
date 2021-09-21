drop table if exists tb_register CASCADE;

create table tb_register (
      id bigint not null auto_increment,
      cell_phone bigint,
      cep integer,
      crm integer,
      deleted boolean not null,
      landline_phone bigint,
      name varchar(255),
      primary key (id)
    );
  
alter table tb_register 
     add constraint UK32o86ljv7skel1ja49auasmha unique (crm, cell_phone);
      