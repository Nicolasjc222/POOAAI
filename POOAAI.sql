truncate table imovel
truncate table pf
truncate table pj
truncate table cliente
truncate table endereco
drop table endereco
drop table imovel
drop table cliente
drop table pf
drop table pj

CREATE TABLE endereco
(
	id_endereco smallint NOT NULL IDENTITY(1,1),
	rua_endereco varchar(100) NOT NULL,
	bairro_endereco varchar(100) NOT NULL,
	cidade_endereco varchar(100) NOT NULL,
	UF_endereco varchar(2) NOT NULL,
	CEP_endereco varchar(8) NOT NULL,
	numero_endereco int NOT NULL,
	complemento_endereco varchar(100) NULL,
	CONSTRAINT pk_id_endereco PRIMARY KEY (id_endereco),
	CONSTRAINT uk_CEP_endereco UNIQUE (CEP_endereco),
	CONSTRAINT ck_UF_endereco CHECK (LEN(UF_endereco)=2),
	CONSTRAINT ck_CEP_endereco CHECK (LEN(CEP_endereco)=8)
)

CREATE TABLE imovel
(
	id_imovel smallint NOT NULL IDENTITY(1,1),
	id_endereco smallint NOT NULL,
	tipo_imovel varchar(50) NOT NULL,
	area_imovel int NOT NULL,
	valor_imovel int NOT NULL,
	comodos_imovel smallint NOT NULL,
	data_insercao DATETIME DEFAULT GETDATE(),
	data_atualizacao DATETIME DEFAULT GETDATE(),
	CONSTRAINT pk_id_imovel PRIMARY KEY (id_imovel),
	CONSTRAINT fk_endereco_imovel FOREIGN KEY (id_endereco) REFERENCES endereco(id_endereco),
	CONSTRAINT uk_endereco_imovel UNIQUE (id_endereco)
)

CREATE TABLE cliente
(
	id_cliente smallint NOT NULL IDENTITY(1,1),
	telefone_cliente int NOT NULL,
	email_cliente varchar(100) NOT NULL,
	id_endereco smallint NOT NULL,
	tipo_cliente varchar(2) NOT NULL,
	CONSTRAINT pk_id_cliente PRIMARY KEY(id_cliente),
	CONSTRAINT fk_endereco_cliente FOREIGN KEY(id_endereco) REFERENCES endereco(id_endereco),
	CONSTRAINT ck_tipo_cliente CHECK (tipo_cliente IN ('PF','PJ'))
)

CREATE TABLE pf
(
	id_pf smallint NOT NULL IDENTITY(1,1),
	cpf_cliente varchar(11) NOT NULL,
	nome varchar(200) NOT NULL,
	id_cliente smallint NOT NULL,
	CONSTRAINT pk_id_pf PRIMARY KEY (id_pf),
	CONSTRAINT uk_cpf_cliente UNIQUE(cpf_cliente),
	CONSTRAINT fk_pf_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
	CONSTRAINT ck_cpf_cliente CHECK (LEN(cpf_cliente) = 11)
)

CREATE TABLE pj
(
	id_pj smallint NOT NULL IDENTITY(1,1),
	cnpj_cliente varchar(14) NOT NULL,
	id_cliente smallint NOT NULL,
	razao_social varchar(200) NOT NULL,
	CONSTRAINT pk_id_pj PRIMARY KEY (id_pj),
	CONSTRAINT uk_cnpj UNIQUE(cnpj_cliente),
	CONSTRAINT fk_pj_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
	CONSTRAINT ck_cnpj_cliente CHECK (LEN(cnpj_cliente) = 14)
)

SELECT * FROM imovel
SELECT * FROM endereco
SELECT * FROM cliente
SELECT * FROM pf
SELECT * FROM pj
