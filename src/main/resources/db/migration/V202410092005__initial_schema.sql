ALTER TABLE servico DROP CONSTRAINT fkFornecedorServico;
ALTER TABLE servico DROP CONSTRAINT categoria;
ALTER TABLE orcamento DROP CONSTRAINT fkServico;
ALTER TABLE orcamento DROP COLUMN servico_id;
DROP TABLE servico;
ALTER TABLE categoria_servico ADD COLUMN imagem_url VARCHAR(250);
ALTER TABLE fornecedor ADD COLUMN categoria_servico_id int;
ALTER TABLE fornecedor ADD CONSTRAINT categoriaServicoFornecedor FOREIGN KEY (categoria_servico_id) REFERENCES categoria_servico(id);
ALTER TABLE fornecedor DROP COLUMN midia_social;

CREATE TABLE favoritos(
    id INT PRIMARY KEY AUTO_INCREMENT,
    casal_id INT,
    fornecedor_id INT,
    CONSTRAINT casalFavoritos FOREIGN KEY (casal_id) REFERENCES casal(id),
    CONSTRAINT fornecedorFavoritos FOREIGN KEY (fornecedor_id) REFERENCES fornecedor(id)
);

CREATE TABLE avaliacao(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nota INT NOT NULL,
    casal_id INT,
    fornecedor_id INT,
    assessor_id INT,
    CONSTRAINT avaliacaoCasal FOREIGN KEY (casal_id) REFERENCES casal(id),
    CONSTRAINT avaliacaoFornecedor FOREIGN KEY (fornecedor_id) REFERENCES fornecedor(id),
    CONSTRAINT avaliacaoAssessor FOREIGN KEY (assessor_id) REFERENCES assessor(id)
);

CREATE TABLE orcamento_fornecedor(
    id INT PRIMARY KEY AUTO_INCREMENT,
    preco DECIMAL(5,2),
    fornecedor_id INT,
    casal_id INT,
    CONSTRAINT casalOrcamentoFornecedor FOREIGN KEY (casal_id) REFERENCES casal(id),
    CONSTRAINT fornecedorOrcamentoCasal FOREIGN KEY (fornecedor_id) REFERENCES fornecedor(id)
);

ALTER TABLE orcamento ADD COLUMN orcamento_fornecedor_id int;
ALTER TABLE orcamento ADD CONSTRAINT fkFornecedorOrcamento FOREIGN KEY (orcamento_fornecedor_id) REFERENCES orcamento_fornecedor(id);
ALTER TABLE casal ADD COLUMN foto VARCHAR(250);
ALTER TABLE assessor ADD COLUMN foto VARCHAR(250);
ALTER TABLE assessor ADD COLUMN telefone_empresa CHAR(13);

CREATE TABLE forma_pagamento(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(45) NOT NULL
);

CREATE TABLE informacao_associado(
    id INT PRIMARY KEY AUTO_INCREMENT,
    visao_geral VARCHAR(300) NOT NULL,
    servicos_oferecidos VARCHAR(300) NOT NULL,
    forma_de_trabalho VARCHAR(300) NOT NULL,
    tamanho_casamento VARCHAR(250) NOT NULL,
    casamentos_catolicos boolean NOT NULL,
    url_site VARCHAR(200) NOT NULL,
    fornecedor_id INT,
    forma_pagamento_id INT,
    assessor_id INT,
    CONSTRAINT informacaoFornecedor FOREIGN KEY (fornecedor_id) REFERENCES fornecedor(id),
    CONSTRAINT informacaoPagamento FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento(id),
    CONSTRAINT informacaoAssessor FOREIGN KEY (assessor_id) REFERENCES assessor(id)
);

ALTER TABLE evento DROP CONSTRAINT fkTipo;
ALTER TABLE evento DROP COLUMN tipo_id;
DROP TABLE tipo_evento;
RENAME TABLE evento TO casamento;
ALTER TABLE informacao_associado ADD COLUMN local VARCHAR(60);
ALTER TABLE avaliacao ADD COLUMN comentario VARCHAR(400);

CREATE TABLE imagem_associado(
    id INT PRIMARY KEY AUTO_INCREMENT,
    imagem_id INT,
    informacao_associado_id INT,
    CONSTRAINT imagemAssociado FOREIGN KEY (imagem_id) REFERENCES imagem(id),
    CONSTRAINT associadoImagem FOREIGN KEY (informacao_associado_id) REFERENCES informacao_associado(id)
);

CREATE TABLE subcategoria_servico(
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(45) NOT NULL,
    ativa boolean NOT NULL,
    imagem_url VARCHAR(250),
    categoria_servico_id INT,
    CONSTRAINT subcategoriaServicoCategoria FOREIGN KEY (categoria_servico_id) REFERENCES categoria_servico(id)
);

ALTER TABLE fornecedor DROP CONSTRAINT categoriaServicoFornecedor;
ALTER TABLE fornecedor DROP COLUMN categoria_servico_id;
ALTER TABLE fornecedor ADD COLUMN subcategoria_servico_id int;
ALTER TABLE fornecedor ADD CONSTRAINT subcategoriaServicoFornecedor FOREIGN KEY (subcategoria_servico_id) REFERENCES subcategoria_servico(id);
ALTER TABLE informacao_associado DROP CONSTRAINT informacaoPagamento;
ALTER TABLE informacao_associado DROP COLUMN forma_pagamento_id;

CREATE TABLE forma_pagamento_associado(
    id INT PRIMARY KEY AUTO_INCREMENT,
    informacao_associado_id INT,
    forma_pagamento_id INT,
    CONSTRAINT formaPagamentoAssociado FOREIGN KEY (informacao_associado_id) REFERENCES informacao_associado(id),
    CONSTRAINT associadoFormaPagamento FOREIGN KEY (forma_pagamento_id) REFERENCES forma_pagamento(id)
);