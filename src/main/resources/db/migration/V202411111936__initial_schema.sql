ALTER TABLE casamento ADD COLUMN status VARCHAR(60);
ALTER TABLE casamento ADD COLUMN mensagem_convite VARCHAR(800);
ALTER TABLE casal DROP CONSTRAINT fkAssessorCasal;
ALTER TABLE casal DROP COLUMN assessor_id;