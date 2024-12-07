ALTER TABLE casal MODIFY COLUMN orcamento_total DECIMAL(10,2);
RENAME TABLE pin TO imagem_casal;

CREATE TABLE casamento_assessorado(
    id INT PRIMARY KEY AUTO_INCREMENT,
    preco DECIMAL(10,2),
    assessor_id INT,
    casamento_id INT,
    CONSTRAINT assessor_casamento_fk FOREIGN KEY (assessor_id) REFERENCES assessor(id),
    CONSTRAINT casamento_assessor_fk FOREIGN KEY (casamento_id) REFERENCES casamento(id)
);

ALTER TABLE assessor DROP COLUMN preco;