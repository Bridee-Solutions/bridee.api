ALTER TABLE casamento DROP COLUMN status;
ALTER TABLE casamento_assessorado ADD COLUMN status VARCHAR(45);
RENAME TABLE casamento_assessorado TO pedido_assessoria;

CREATE TABLE categoria_convidado(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(60) NOT NULL
);

ALTER TABLE convidado ADD COLUMN categoria_convidado_id INT;
ALTER TABLE convidado ADD CONSTRAINT categoriaConvidadoFk FOREIGN KEY (categoria_convidado_id) REFERENCES categoria_convidado(id);
ALTER TABLE convidado DROP COLUMN categoria;