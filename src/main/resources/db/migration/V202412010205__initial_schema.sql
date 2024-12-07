ALTER TABLE informacao_associado CHANGE COLUMN casamentos_catolicos email VARCHAR(100) NOT NULL;
ALTER TABLE tipo_casamento DROP COLUMN especializado;

CREATE TABLE tipo_cerimonia(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(32)
);

CREATE TABLE tipo_cerimonia_associado(
    id INT PRIMARY KEY AUTO_INCREMENT,
    tipo_cerimonia_id INT,
    informacao_associado_id INT,
    CONSTRAINT tipo_cerimonia_associado FOREIGN KEY (tipo_cerimonia_id) REFERENCES tipo_cerimonia(id),
    CONSTRAINT informacao_associado_tipo_cerimonia FOREIGN KEY (informacao_associado_id) REFERENCES informacao_associado(id)
);



