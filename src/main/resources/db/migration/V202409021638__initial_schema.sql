alter table usuario drop column created_by;
alter table usuario drop column updated_by;
alter table usuario add telefone char(13);
alter table usuario add column estado_civil;

alter table casal drop column  telefone1;
alter table casal drop column email;
alter table casal drop column senha;
alter table casal modify column telefone2 char(13);
alter table casal rename column telefone2 to telefone_parceiro;
alter table casal add column endereco VARCHAR(255);
alter table casal add column cep char(8);
alter table casal add column estado_civil_parceiro varchar(45);
alter table casal add column nome_parceiro varchar(45);

