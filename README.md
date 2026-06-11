# AAI - Sistema Interno de Imobiliária

Sistema de gerenciamento interno para imobiliárias, desenvolvido como projeto acadêmico. Permite o cadastro, atualização, remoção e listagem de endereços, imóveis e clientes (pessoa física e jurídica).

---

## Grupo

- Nicolas Antônio Lourenço Erler
- Nycollas Baldim
- Gabriel Ribeiro Rodrigues
- Israel Caputo

## Tecnologias Utilizadas

- Java 17
- JavaFX
- SQL Server
- mssql-jdbc (driver de conexão)

---

## Modelagem do Banco de Dados

O banco foi modelado seguindo o fluxo ER → Conceitual → Físico, com base nos estudos de Banco de Dados do professor Humberto. As tabelas criadas são:

- `endereco` — armazena os dados de endereço
- `cliente` — entidade abstrata com tipo PF ou PJ
- `pf` — pessoa física, vinculada a cliente via FK com remoção em cascata
- `pj` — pessoa jurídica, vinculada a cliente via FK com remoção em cascata
- `imovel` — imóvel vinculado a um endereço e opcionalmente a um cliente

---

## Estrutura do Projeto (Java)

### Classes de Modelagem

- `Cliente` *(abstract)* — base comum para pessoa física e jurídica
- `PessoaFisica` *(extends Cliente)* — representa clientes PF
- `PessoaJuridica` *(extends Cliente)* — representa clientes PJ
- `Endereco` — representa um endereço
- `Imovel` — representa um imóvel

A abstração de `Cliente` com herança para `PessoaFisica` e `PessoaJuridica` foi adotada porque ambos os tipos compartilham a categoria de cliente, mas exigem tratamentos distintos nos dados e validações.

### Padrão DAO + Controller

Cada classe de modelagem possui um DAO e um Controller associados, responsáveis pelas operações de adição, remoção, atualização e listagem. A exceção são `PessoaFisica` e `PessoaJuridica`, que são removidas via remoção em cascata ao deletar o `Cliente` correspondente.

---

## Telas

- **Login** — tela inicial de acesso ao sistema
- **Menu Geral** — navegação entre os módulos
- **Gerenciamento de Imóveis** — listagem com opções de adicionar, atualizar e remover
- **Gerenciamento de Clientes** — listagem com opções de adicionar, atualizar e remover
- **Gerenciamento de Endereços** — listagem com opções de adicionar, atualizar e remover

---

## Como Rodar

> O projeto não possui um executável `.exe`. É necessário rodá-lo por uma IDE Java.

### Requisitos

- Java 17 instalado
- SQL Server instalado e em execução
- IDE Java (Eclipse, IntelliJ, etc.)
- Driver mssql-jdbc configurado no projeto

### Passos

1. Execute o arquivo `POOAAI.sql` no SQL Server para criar as tabelas
2. Configure a conexão com o banco na classe `Conexao` (URL, usuário e senha)
3. Rode o projeto pela IDE
