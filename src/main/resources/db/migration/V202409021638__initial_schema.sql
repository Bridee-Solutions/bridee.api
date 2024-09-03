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

create table assessor_fornecedor (
    id int primary key auto_increment,
    fk_fornecedor int,
    fk_assessor int,
    CONSTRAINT fkFornecedorAssessor FOREIGN KEY (fk_fornecedor) REFERENCES fornecedor (id),
    CONSTRAINT fkAssessorFornecedor FOREIGN KEY (fk_assessor) REFERENCES assessor (id)
);

rename table convidado_evento to convite
alter table acompanhante add column status varchar(45);
alter table convite add column acompanhante_id int;
alter table convite add constraint fkConviteAcompanhante FOREIGN KEY (acompanhante_id) REFERENCES acompanhante (id);


alter table fornecedor drop column premium;
alter table assessor add column premium boolean;

