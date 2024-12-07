ALTER TABLE informacao_associado RENAME COLUMN local TO cidade;
ALTER TABLE informacao_associado ADD COLUMN bairro VARCHAR(60);
ALTER TABLE orcamento_fornecedor MODIFY COLUMN preco DECIMAL(10,2);
ALTER TABLE assessor MODIFY COLUMN preco DECIMAL(10,2);
ALTER TABLE custo MODIFY COLUMN preco_estimado DECIMAL(10,2);
ALTER TABLE custo MODIFY COLUMN preco_atual DECIMAL(10,2);
ALTER TABLE casamento ADD COLUMN tamanho_casamento VARCHAR(45);

CREATE TABLE tipo_casamento(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(45),
    especializado boolean
);

CREATE TABLE tipo_casamento_associado(
    id INT PRIMARY KEY AUTO_INCREMENT,
    tipo_casamento_id INT,
    informacao_associado_id INT,
    CONSTRAINT tipo_casamento_associado FOREIGN KEY (tipo_casamento_id) REFERENCES tipo_casamento(id),
    CONSTRAINT informacao_associado_tipo FOREIGN KEY (informacao_associado_id) REFERENCES informacao_associado(id)
);