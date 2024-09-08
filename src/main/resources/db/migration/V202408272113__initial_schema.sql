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
        aplicacao_id int,
        usuario_id int,
        CONSTRAINT fkAplicacao FOREIGN KEY (aplicacao_id) REFERENCES aplicacao (id),
        CONSTRAINT fkUsuarioToken FOREIGN KEY (usuario_id) REFERENCES usuario (id)
    );

create table
    role (
        id int primary key auto_increment,
        nome varchar(45)
    );

create table
    user_token (
        id int primary key auto_increment,
        role_id int,
        usuario_id int,
        CONSTRAINT fkRole_const FOREIGN KEY (role_id) REFERENCES role (id),
        CONSTRAINT fkUsuario_const FOREIGN KEY (usuario_id) REFERENCES usuario (id)
    );

create table
    casal (
        id int primary key auto_increment,
        email varchar(45),
        senha varchar(255),
        telefone1 varchar(45),
        telefone2 varchar(45),
        usuario_id int,
        CONSTRAINT fkUsuarioCasal FOREIGN KEY (usuario_id) REFERENCES usuario (id)
    );

create table
    fornecedor (
        id int primary key auto_increment,
        nome varchar(45),
        midia_social varchar(255),
        email varchar(45),
        nota int,
        premium tinyInt (1),
        usuario_id int,
        created_by varchar(45),
        updated_by varchar(45),
        created_at datetime,
        updated_at datetime,
        CONSTRAINT fkUsuarioFornecedor FOREIGN KEY (usuario_id) REFERENCES usuario (id)
    );

create table
    fornecedor_casal (
        id int primary key auto_increment,
        fornecedor_id int,
        usuario_id int,
        CONSTRAINT fkFornecedor FOREIGN KEY (fornecedor_id) REFERENCES fornecedor (id),
        CONSTRAINT fkUsuarioFornecedorCasal FOREIGN KEY (usuario_id) REFERENCES usuario (id)
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
        categoria_id int,
        preco decimal(10, 2),
        nota int,
        fornecedor_id int,
        created_by varchar(45),
        updated_by varchar(45),
        created_at datetime,
        updated_at datetime,
        CONSTRAINT fkFornecedorServico FOREIGN KEY (fornecedor_id) REFERENCES fornecedor (id),
        CONSTRAINT categoria FOREIGN KEY (categoria_id) REFERENCES categoria_servico (id)
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
        imagem_id int,
        casal_id int,
        CONSTRAINT fkImagem FOREIGN KEY (imagem_id) REFERENCES imagem (id),
        CONSTRAINT fkCasalPin FOREIGN KEY (casal_id) REFERENCES casal (id)
    );

create table
	presente (
		id int primary key auto_increment,
        nome varchar(45),
        categoria varchar(45),
        valor decimal(10,2),
        casal_id int,
        CONSTRAINT fkCasalPresente FOREIGN KEY (casal_id) REFERENCES casal (id)

    );

create table
    playlist (
        id int primary key auto_increment,
        nome varchar(45),
        casal_id int,
        CONSTRAINT fkCasalPlaylist FOREIGN KEY (casal_id) REFERENCES casal (id)
    );

create table
    musica (
        id int primary key auto_increment,
        nome varchar(45),
        artista varchar(45),
        url varchar(255),
        playlist_id int,
        CONSTRAINT fkPlaylist FOREIGN KEY (playlist_id) REFERENCES playlist (id)
    );

create table
    assessor (
        id int primary key auto_increment,
        cnpj char(14),
        usuario_id int,
        CONSTRAINT fkUsuario FOREIGN KEY (usuario_id) REFERENCES usuario (id)
    );

create table
    casal_assessor (
        id int primary key auto_increment,
        casal_id int,
        assessor_id int,
        CONSTRAINT fkAssessor FOREIGN KEY (assessor_id) REFERENCES assessor (id),
        CONSTRAINT fkCasal FOREIGN KEY (casal_id) REFERENCES casal (id)
    );

create table
    tipo_orcamento (
        id int primary key auto_increment,
        nome varchar(45)
    );


create table
    item_orcamento (
        id int primary key auto_increment,
        tipo_id int,
        CONSTRAINT tipo FOREIGN KEY (tipo_id) REFERENCES tipo_orcamento (id)
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
        item_orcamento_id int,
        CONSTRAINT fkItemOrcamentoCusto FOREIGN KEY (item_orcamento_id) REFERENCES item_orcamento (id)
    );

create table
    orcamento (
        id int primary key auto_increment,
        nome varchar(45),
	categoria varchar(45),
        casal_assessor_id int,
        servico_id int,
        item_orcamento_id int,
        CONSTRAINT fkCasalXAssessor FOREIGN KEY (casal_assessor_id) REFERENCES casal_assessor (id),
        CONSTRAINT fkServico FOREIGN KEY (servico_id) REFERENCES assessor (id),
    	CONSTRAINT fkItemOrcamento FOREIGN KEY (item_orcamento_id) REFERENCES item_orcamento (id)
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
        casal_id int,
        assessor_id int,
        tipo_id int,
		created_by varchar(45),
        updated_by varchar(45),
        created_at datetime,
        updated_at datetime,
        CONSTRAINT fkAssessorEvento FOREIGN KEY (assessor_id) REFERENCES assessor (id),
		CONSTRAINT fkCasalEvento FOREIGN KEY (casal_id) REFERENCES casal (id),
		CONSTRAINT fkTipo FOREIGN KEY (tipo_id) REFERENCES tipo_evento (id)
    );

create table
    cronograma (
        id int primary key auto_increment,
        evento_id int,
        CONSTRAINT evento_id FOREIGN KEY (evento_id) REFERENCES evento (id)
    );

create table
    atividade (
        id int primary key auto_increment,
        nome varchar(45),
        inicio time,
        fim time,
        cronograma_id int,
        CONSTRAINT fkCronograma FOREIGN KEY (cronograma_id) REFERENCES cronograma (id)
    );

create table
    mesa (
        id int primary key auto_increment,
        nome varchar(45),
        numero_assentos int,
        disponivel tinyint(1),
        evento_id int,
        CONSTRAINT fkEventoMesa FOREIGN KEY (evento_id) REFERENCES evento (id)
    );

create table
    convidado (
        id int primary key auto_increment,
        nome varchar(45),
        categoria varchar(45),
        telefone char(13),
        status varchar(45),
        mesa_id int,
        CONSTRAINT fkMesa FOREIGN KEY (mesa_id) REFERENCES mesa (id)
    );

create table
    convidado_evento (
        id int primary key auto_increment,
        evento_id int,
        convidado_id int,
        CONSTRAINT fkEvento FOREIGN KEY (evento_id) REFERENCES evento (id),
        CONSTRAINT fkConvidadoEvento FOREIGN KEY (convidado_id) REFERENCES convidado (id)
    );

create table
    acompanhante (
        id int primary key auto_increment,
        nome varchar(45),
		categoria varchar(45),
		menor_idade boolean,
        convidado_id int,
        CONSTRAINT fkConvidado FOREIGN KEY (convidado_id) REFERENCES convidado (id)
    );