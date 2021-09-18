INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Carlos', 'Pinho', 'carlos@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('Níkolas', 'Lencioni', 'nikolas@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

INSERT INTO tb_specialty (name, deleted) VALUES ('Alergologia', false);
INSERT INTO tb_specialty (name, deleted) VALUES ('Angiologia', false);
INSERT INTO tb_specialty (name, deleted) VALUES ('Buco maxilo', false);
INSERT INTO tb_specialty (name, deleted) VALUES ('Cardiologia clínca', false);
INSERT INTO tb_specialty (name, deleted) VALUES ('Cardiologia infantil', false);
INSERT INTO tb_specialty (name, deleted) VALUES ('Cirurgia cabeça e pescoço', false);
INSERT INTO tb_specialty (name, deleted) VALUES ('Cirurgia cardíaca', false);
INSERT INTO tb_specialty (name, deleted) VALUES ('Cirurgia de tórax', false);

INSERT INTO tb_register (name, crm, landline_phone, cell_phone, cep, deleted) VALUES ('José de Assis', 74259, 36247170, 991345699, 49038561, false)
INSERT INTO tb_register (name, crm, landline_phone, cell_phone, cep, deleted) VALUES ('Carlos Laurine', 98643, 32239022, 995702214, 69911690, false)
INSERT INTO tb_register (name, crm, landline_phone, cell_phone, cep, deleted) VALUES ('Plínio Borges', 81885, 36259997, 987252233, 81510490, false)
INSERT INTO tb_register (name, crm, landline_phone, cell_phone, cep, deleted) VALUES ('Renan Marinho', 32190, 32243638, 988839516, 68905540, false)



INSERT INTO tb_register_specialty (register_id, specialty_id) VALUES (1, 1)
INSERT INTO tb_register_specialty (register_id, specialty_id) VALUES (1, 3)

INSERT INTO tb_register_specialty (register_id, specialty_id) VALUES (2, 2)
INSERT INTO tb_register_specialty (register_id, specialty_id) VALUES (2, 4)

INSERT INTO tb_register_specialty (register_id, specialty_id) VALUES (3, 5)
INSERT INTO tb_register_specialty (register_id, specialty_id) VALUES (3, 7)

INSERT INTO tb_register_specialty (register_id, specialty_id) VALUES (4, 6)
INSERT INTO tb_register_specialty (register_id, specialty_id) VALUES (4, 8)