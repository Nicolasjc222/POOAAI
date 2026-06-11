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
	CONSTRAINT ck_UF_endereco CHECK (LEN(UF_endereco)=2),
	CONSTRAINT ck_CEP_endereco CHECK (LEN(CEP_endereco)=8)
)

CREATE TABLE imovel
(
	id_imovel smallint NOT NULL IDENTITY(1,1),
	id_endereco smallint NOT NULL,
	id_cliente smallint NULL,
	tipo_imovel varchar(50) NOT NULL,
	area_imovel int NOT NULL,
	valor_imovel int NOT NULL,
	comodos_imovel smallint NOT NULL,
	data_insercao DATETIME DEFAULT GETDATE(),
	data_atualizacao DATETIME DEFAULT GETDATE(),
	finalidade VARCHAR(20) NOT NULL DEFAULT 'Venda',
	CONSTRAINT pk_id_imovel PRIMARY KEY (id_imovel),
	CONSTRAINT fk_endereco_imovel FOREIGN KEY (id_endereco) REFERENCES endereco(id_endereco),
	CONSTRAINT uk_endereco_imovel UNIQUE (id_endereco),
	CONSTRAINT fk_cliente_imovel FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
)

CREATE TABLE cliente
(
	id_cliente smallint NOT NULL IDENTITY(1,1),
	telefone_cliente varchar(20) NOT NULL,
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
	CONSTRAINT fk_pf_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente) ON DELETE CASCADE,
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
	CONSTRAINT fk_pj_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente) ON DELETE CASCADE,
	CONSTRAINT ck_cnpj_cliente CHECK (LEN(cnpj_cliente) = 14)
)

-- ENDERECO (10 registros)
INSERT INTO endereco (rua_endereco, bairro_endereco, cidade_endereco, UF_endereco, CEP_endereco, numero_endereco, complemento_endereco) VALUES
('Rua das Flores', 'Centro', 'São Paulo', 'SP', '01310100', 100, 'Apto 12'),
('Av. Brasil', 'Jardins', 'Rio de Janeiro', 'RJ', '20040020', 200, NULL),
('Rua XV de Novembro', 'Centro', 'Curitiba', 'PR', '80020310', 300, 'Sala 5'),
('Rua Bahia', 'Savassi', 'Belo Horizonte', 'MG', '30160011', 45, NULL),
('Av. Paulista', 'Bela Vista', 'São Paulo', 'SP', '01310942', 1500, 'Conj. 81'),
('Rua dos Andradas', 'Centro Histórico', 'Porto Alegre', 'RS', '90020005', 78, NULL),
('Rua Augusta', 'Consolação', 'São Paulo', 'SP', '01305100', 900, 'Apto 3'),
('Av. Beira Mar', 'Centro', 'Florianópolis', 'SC', '88015100', 12, NULL),
('Rua do Sol', 'Boa Viagem', 'Recife', 'PE', '51020060', 55, 'Casa'),
('Av. Getúlio Vargas', 'Funcionários', 'Belo Horizonte', 'MG', '30112020', 230, 'Apto 101');

-- CLIENTE (10 registros, alternando PF e PJ)
INSERT INTO cliente (telefone_cliente, email_cliente, id_endereco, tipo_cliente) VALUES
('11999990001', 'joao@email.com', 1, 'PF'),
('21988880002', 'empresa1@email.com', 2, 'PJ'),
('41977770003', 'maria@email.com', 3, 'PF'),
('31966660004', 'empresa2@email.com', 4, 'PJ'),
('11955550005', 'carlos@email.com', 5, 'PF'),
('51944440006', 'empresa3@email.com', 6, 'PJ'),
('11933330007', 'ana@email.com', 7, 'PF'),
('48922220008', 'empresa4@email.com', 8, 'PJ'),
('81911110009', 'pedro@email.com', 9, 'PF'),
('31900000010', 'empresa5@email.com', 10, 'PJ');

-- PESSOA FISICA
INSERT INTO pf (cpf_cliente, nome, id_cliente) VALUES
('05699828044', 'João Silva', 1),
('16734563081', 'Maria Souza', 3),
('23451289091', 'Carlos Oliveira', 5),
('34567190190', 'Ana Santos', 7),
('41237567866', 'Pedro Costa', 9);

-- PESSOA JURIDICA
INSERT INTO pj (cnpj_cliente, id_cliente, razao_social) VALUES
('12345678000140', 2, 'Empresa Alpha Ltda'),
('23456789000109', 4, 'Beta Comercial S.A.'),
('34567890000109', 6, 'Gamma Serviços Ltda'),
('45678901000108', 8, 'Delta Imóveis S.A.'),
('56789012000184', 10, 'Epsilon Construtora Ltda');

INSERT INTO endereco (rua_endereco, bairro_endereco, cidade_endereco, UF_endereco, CEP_endereco, numero_endereco, complemento_endereco) VALUES
('Rua Imovel 1', 'Vila Nova', 'São Paulo', 'SP', '02010000', 10, NULL),
('Rua Imovel 2', 'Lapa', 'São Paulo', 'SP', '05073000', 20, NULL),
('Rua Imovel 3', 'Ipanema', 'Rio de Janeiro', 'RJ', '22420000', 30, NULL),
('Rua Imovel 4', 'Meireles', 'Fortaleza', 'CE', '60165050', 40, NULL),
('Rua Imovel 5', 'Aldeota', 'Fortaleza', 'CE', '60150000', 50, NULL),
('Rua Imovel 6', 'Itaim Bibi', 'São Paulo', 'SP', '04533010', 60, NULL),
('Rua Imovel 7', 'Brooklin', 'São Paulo', 'SP', '04571000', 70, NULL),
('Rua Imovel 8', 'Barreiro', 'Belo Horizonte', 'MG', '30640000', 80, NULL),
('Rua Imovel 9', 'Pampulha', 'Belo Horizonte', 'MG', '31275000', 90, NULL),
('Rua Imovel 10', 'Asa Norte', 'Brasília', 'DF', '70770000', 100, NULL);

INSERT INTO imovel (id_endereco, id_cliente, tipo_imovel, area_imovel, valor_imovel, comodos_imovel, finalidade) VALUES
(11, 1, 'Apartamento', 60, 350000, 3, 'Venda'),
(12, 2, 'Comercial', 120, 800000, 5, 'Venda'),
(13, NULL, 'Casa', 90, 450000, 4, 'Aluguel'),
(14, 3, 'Apartamento', 45, 200000, 2, 'Aluguel'),
(15, NULL, 'Comercial', 200, 1200000, 8, 'Venda'),
(16, 4, 'Casa', 150, 600000, 6, 'Venda'),
(17, NULL, 'Apartamento', 70, 380000, 3, 'Aluguel'),
(18, 5, 'Comercial', 80, 500000, 4, 'Venda'),
(19, NULL, 'Casa', 110, 520000, 5, 'Aluguel'),
(20, 6, 'Apartamento', 55, 310000, 2, 'Venda');

SELECT * FROM imovel
SELECT * FROM endereco
SELECT * FROM cliente
SELECT * FROM pf
SELECT * FROM pj