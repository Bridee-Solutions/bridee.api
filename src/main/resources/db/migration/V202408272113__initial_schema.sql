create table
    usuario (
        id int primary key auto_increment,
        nome varchar(45),
        email varchar(45) unique,
        senha varchar(255),
        created_by varchar(45),
        updated_by varchar(45),
        created_at datetime,
        updated_at datetime
    );

create table
    aplicacao (
        id int primary key auto_increment,
        nome varchar(45),
        ativa tinyInt (1)
    );

create table
    token (
        id int primary key auto_increment,
        tipo varchar(45),
        token varchar(45),
        expired tinyInt (1),
        fk_aplicacao int,
        fk_usuario int,
        CONSTRAINT fkAplicacao FOREIGN KEY (fk_aplicacao) REFERENCES aplicacao (id),
        CONSTRAINT fkUsuarioToken FOREIGN KEY (fk_usuario) REFERENCES usuario (id)
    );

create table
    role (
        id int primary key auto_increment,
        nome varchar(45)
    );

create table
    user_token (
        id int primary key auto_increment,
        fk_role int,
        fk_usuario int,
        CONSTRAINT fkRole_const FOREIGN KEY (fk_role) REFERENCES role (id),
        CONSTRAINT fkUsuario_const FOREIGN KEY (fk_usuario) REFERENCES usuario (id)
    );

create table
    casal (
        id int primary key auto_increment,
        email varchar(45),
        senha varchar(255),
        telefone1 varchar(45),
        telefone2 varchar(45),
        fk_usuario int,
        CONSTRAINT fkUsuarioCasal FOREIGN KEY (fk_usuario) REFERENCES usuario (id)
    );

create table
    fornecedor (
        id int primary key auto_increment,
        nome varchar(45),
        midia_social varchar(255),
        email varchar(45),
        nota int,
        premium tinyInt (1),
        fk_usuario int,
        created_by varchar(45),
        updated_by varchar(45),
        created_at datetime,
        updated_at datetime,
        CONSTRAINT fkUsuarioFornecedor FOREIGN KEY (fk_usuario) REFERENCES usuario (id)
    );

create table
    fornecedor_casal (
        id int primary key auto_increment,
        fk_fornecedor int,
        fk_usuario int,
        CONSTRAINT fkFornecedor FOREIGN KEY (fk_fornecedor) REFERENCES fornecedor (id),
        CONSTRAINT fkUsuarioFornecedorCasal FOREIGN KEY (fk_usuario) REFERENCES usuario (id)
    );

create table
    categoria_servico (
        id int primary key auto_increment,
        nome varchar(45),
        active tinyInt (1)
    );

create table
    servico (
        id int primary key auto_increment,
        nome varchar(45),
        fk_categoria int,
        preco decimal(10, 2),
        nota int,
        fk_fornecedor int,
        created_by varchar(45),
        updated_by varchar(45),
        created_at datetime,
        updated_at datetime,
        CONSTRAINT fkFornecedorServico FOREIGN KEY (fk_fornecedor) REFERENCES fornecedor (id),
        CONSTRAINT categoria FOREIGN KEY (fk_categoria) REFERENCES categoria_servico (id)
    );

create table
	imagem (
		id int primary key auto_increment,
        nome varchar(45),
        url varchar(45)
    );

create table
    pin (
        id int primary key auto_increment,
        fk_imagem int,
        fk_casal int,
        CONSTRAINT fkImagem FOREIGN KEY (fk_imagem) REFERENCES imagem (id),
        CONSTRAINT fkCasalPin FOREIGN KEY (fk_casal) REFERENCES casal (id)
    );

create table
	presente (
		id int primary key auto_increment,
        nome varchar(45),
        categoria varchar(45),
        valor decimal(10,2),
        fk_casal int,
        CONSTRAINT fkCasalPresente FOREIGN KEY (fk_casal) REFERENCES casal (id)

    );

create table
    playlist (
        id int primary key auto_increment,
        nome varchar(45),
        fk_casal int,
        CONSTRAINT fkCasalPlaylist FOREIGN KEY (fk_casal) REFERENCES casal (id)
    );

create table
    musica (
        id int primary key auto_increment,
        nome varchar(45),
        artista varchar(45),
        url varchar(255),
        fk_playlist int,
        CONSTRAINT fkPlaylist FOREIGN KEY (fk_playlist) REFERENCES playlist (id)
    );

create table
    assessor (
        id int primary key auto_increment,
        cnpj char(14),
        fk_usuario int,
        CONSTRAINT fkUsuario FOREIGN KEY (fk_usuario) REFERENCES usuario (id)
    );

create table
    casal_assessor (
        id int primary key auto_increment,
        fk_casal int,
        fk_assessor int,
        CONSTRAINT fkAssessor FOREIGN KEY (fk_assessor) REFERENCES assessor (id),
        CONSTRAINT fkCasal FOREIGN KEY (fk_casal) REFERENCES casal (id)
    );

create table
    tipo_orcamento (
        id int primary key auto_increment,
        nome varchar(45)
    );


create table
    item_orcamento (
        id int primary key auto_increment,
        fk_tipo int,
        CONSTRAINT tipo FOREIGN KEY (fk_tipo) REFERENCES tipo_orcamento (id)
    );

create table
    custo (
        id int primary key auto_increment,
        nome varchar(45),
        preco_estimado decimal (10, 2),
        preco_atual decimal (10, 2),
        created_by varchar(45),
        updated_by varchar(45),
        created_at datetime,
        updated_at datetime,
        fk_item_orcamento int,
        CONSTRAINT fkItemOrcamentoCusto FOREIGN KEY (fk_item_orcamento) REFERENCES item_orcamento (id)
    );

create table
    orcamento (
        id int primary key auto_increment,
        nome varchar(45),
	categoria varchar(45),
        fk_casal_assessor int,
        fk_servico int,
        fk_item_orcamento int,
        CONSTRAINT fkCasalXAssessor FOREIGN KEY (fk_casal_assessor) REFERENCES casal_assessor (id),
        CONSTRAINT fkServico FOREIGN KEY (fk_servico) REFERENCES assessor (id),
    	CONSTRAINT fkItemOrcamento FOREIGN KEY (fk_item_orcamento) REFERENCES item_orcamento (id)
    );

create table
    tipo_evento (
        id int primary key auto_increment,
        nome varchar(45)
    );

create table
	evento (
		id int primary key auto_increment,
        nome varchar(45),
        tipo int,
        total_acompanhantes int,
        total_convidados int,
        data_inicio DATE,
        data_fim DATE,
        fk_casal int,
        fk_assessor int,
        fk_tipo int,
		created_by varchar(45),
        updated_by varchar(45),
        created_at datetime,
        updated_at datetime,
        CONSTRAINT fkAssessorEvento FOREIGN KEY (fk_assessor) REFERENCES assessor (id),
		CONSTRAINT fkCasalEvento FOREIGN KEY (fk_casal) REFERENCES casal (id),
		CONSTRAINT fkTipo FOREIGN KEY (fk_tipo) REFERENCES tipo_evento (id)
    );

create table
    cronograma (
        id int primary key auto_increment,
        fk_evento int,
        CONSTRAINT fk_evento FOREIGN KEY (fk_evento) REFERENCES evento (id)
    );

create table
    atividade (
        id int primary key auto_increment,
        nome varchar(45),
        inicio time,
        fim time,
        fk_cronograma int,
        CONSTRAINT fkCronograma FOREIGN KEY (fk_cronograma) REFERENCES cronograma (id)
    );

create table
    mesa (
        id int primary key auto_increment,
        nome varchar(45),
        numero_assentos int,
        disponivel tinyint(1),
        fk_evento int,
        CONSTRAINT fkEventoMesa FOREIGN KEY (fk_evento) REFERENCES evento (id)
    );

create table
    convidado (
        id int primary key auto_increment,
        nome varchar(45),
        categoria time,
        telefone char(13),
        status varchar(45),
        fk_mesa int,
        CONSTRAINT fkMesa FOREIGN KEY (fk_mesa) REFERENCES mesa (id)
    );

create table
    convidado_evento (
        id int primary key auto_increment,
        fk_evento int,
        fk_convidado int,
        CONSTRAINT fkEvento FOREIGN KEY (fk_evento) REFERENCES evento (id),
        CONSTRAINT fkConvidadoEvento FOREIGN KEY (fk_convidado) REFERENCES convidado (id)
    );

create table
    acompanhante (
        id int primary key auto_increment,
        nome varchar(45),
		categoria varchar(45),
        fk_convidado int,
        CONSTRAINT fkConvidado FOREIGN KEY (fk_convidado) REFERENCES convidado (id)
    );