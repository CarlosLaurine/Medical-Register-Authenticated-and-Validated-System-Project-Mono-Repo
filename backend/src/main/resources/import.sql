INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Carlos', 'Pinho', 'carlos@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Níkolas', 'Lencioni', 'nikolas@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_specialty (name) VALUES ('Alergologia');
INSERT INTO tb_specialty (name) VALUES ('Angiologia');
INSERT INTO tb_specialty (name) VALUES ('Buco maxilo');
INSERT INTO tb_specialty (name) VALUES ('Cardiologia clínca');
INSERT INTO tb_specialty (name) VALUES ('Cardiologia infantil');
INSERT INTO tb_specialty (name) VALUES ('Cirurgia cabeça e pescoço');
INSERT INTO tb_specialty (name) VALUES ('Cirurgia cardíaca');
INSERT INTO tb_specialty (name) VALUES ('Cirurgia de tórax');

INSERT INTO tb_register (name, crm, landline_phone, cell_phone, cep) VALUES ('José de Assis', 74259, 991345699, 36247170, 49038561)
INSERT INTO tb_register (name, crm, landline_phone, cell_phone, cep) VALUES ('Carlos Laurine', 98643, 995702214, 32239022, 69911690)
INSERT INTO tb_register (name, crm, landline_phone, cell_phone, cep) VALUES ('Plínio Borges', 81885, 987252233, 36259997, 81510490)
INSERT INTO tb_register (name, crm, landline_phone, cell_phone, cep) VALUES ('Renan Marinho', 32190, 988839516, 32243638, 68905540)



INSERT INTO tb_register_specialty (register_id, specialty_id) VALUES (1, 1)
INSERT INTO tb_register_specialty (register_id, specialty_id) VALUES (1, 3)

INSERT INTO tb_register_specialty (register_id, specialty_id) VALUES (2, 2)
INSERT INTO tb_register_specialty (register_id, specialty_id) VALUES (2, 4)

INSERT INTO tb_register_specialty (register_id, specialty_id) VALUES (3, 5)
INSERT INTO tb_register_specialty (register_id, specialty_id) VALUES (3, 7)

INSERT INTO tb_register_specialty (register_id, specialty_id) VALUES (4, 6)
INSERT INTO tb_register_specialty (register_id, specialty_id) VALUES (4, 8)