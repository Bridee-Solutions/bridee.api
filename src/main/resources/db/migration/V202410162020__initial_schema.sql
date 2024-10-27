CREATE TABLE tarefa(
    id INT PRIMARY KEY AUTO_INCREMENT,
    meses_anteriores INT NOT NULL,
    nome VARCHAR(70) NOT NULL,
    descricao VARCHAR(350) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    status VARCHAR(45) NOT NULL,
    data_limite DATE NOT NULL
);

CREATE TABLE tarefa_casal(
    id INT PRIMARY KEY AUTO_INCREMENT,
    tarefa_id INT,
    casal_id INT,
    CONSTRAINT tarefaCasal FOREIGN KEY (tarefa_id) REFERENCES tarefa(id),
    CONSTRAINT casalTarefa FOREIGN KEY (casal_id) REFERENCES casal(id)
);