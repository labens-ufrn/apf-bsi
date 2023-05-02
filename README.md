<!-- Título -->
<h1 align="center">
ANALISADOR DE PONTOS DE FUNÇÃO 
</h1>
 
<!-- Badge_Grade -->
<p align="center">
  <img alt="GitHub language count" src="https://img.shields.io/github/languages/count/lukemorales/rocketshoes-react-native.svg">

  <a href="https://www.codacy.com/app/lukemorales/rocketshoes-react-native?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=lukemorales/rocketshoes-react-native&amp;utm_campaign=Badge_Grade">
    <img alt="Codacy grade" src="https://img.shields.io/codacy/grade/e4cc1482460841bdaa99c2e75e01f0bc.svg">
  </a>

  <img alt="Repository size" src="https://img.shields.io/github/repo-size/lukemorales/rocketshoes-react-native.svg">
  <a href="https://github.com/lukemorales/rocketshoes-react-native/commits/master">
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/lukemorales/rocketshoes-react-native.svg">
  </a>
  <a href="https://github.com/lukemorales/rocketshoes-react-native/issues">
    <img alt="Repository issues" src="https://img.shields.io/github/issues/lukemorales/rocketshoes-react-native.svg">
  </a>
  <img alt="GitHub" src="https://img.shields.io/github/license/lukemorales/rocketshoes-react-native.svg">
</p>

<!-- Descrição -->
<h4 align="center">
Sistema para a contagem e análise dos pontos de função para estimar o tamanho funcional de um software.
</h4><br><br>

 <!-- Links dos tópicos -->
<p align="center"></p>
<a href="#sobre">Sobre o Projeto</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
<a href="#doc">Documentação</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
<a href="#pre">Pré-requisitos</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
<a href="#des">Desenvolvimento</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
<a href="#bui">Build e Execução</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
<a href="#tes">Testes</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
<a href="#lic">Licença</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#ref">Referências</a>
</p>
<br>
<a id="sobre"></a> 

# Sobre o Projeto
Sistema para facilitar a contagem na análise por pontos de função (APF) desenvolvido para ser utilizado nas disciplinas de Engenharia de Software do curso de Bacharelado em Sistemas de Informação do CERES/UFRN.

<a id="doc"></a> 

# Documentação
* [Documento de Visão](docs/docVisao.md)
* [Plano de Iteração]()
* [Lista de User Stories]()
* [Contagem de Tamanho Funcional com Análise por Pontos de Função]()

<a id="pre"></a> 

# Pré-requisitos
Para executar o projeto, será necessário instalar os seguintes programas:
* JDK 8: Necessário para executar o projeto Java.
* Maven: Necessário para realizar o build do projeto Java.
* Eclipse ou Vscode + extensões Java: Para o desenvolvimento do Projeto.
* MariaDB ou MySql: para persistência no Banco de Dados
* Criação do Banco de Dados dev e test(apf_db e apf_db_test) e Usuário(apf_user) com sua devidas permissões.
Código abaixo:

1. Criação das bases de dados e usuário

```sql
CREATE ROLE apf_user WITH
	LOGIN
	NOSUPERUSER
	NOCREATEDB
	NOCREATEROLE
	NOINHERIT
	NOREPLICATION
	CONNECTION LIMIT -1
	PASSWORD 'xxxxxx';
COMMENT ON ROLE apf_user IS 'Usuário do Sistema APF de Contagem de Pontos de Função.';
```

```sql
CREATE DATABASE apf_db
    WITH 
    OWNER = apf_user
    ENCODING = 'UTF8'
    LC_COLLATE = 'pt_BR.utf8'
    LC_CTYPE = 'pt_BR.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
```

Por algum motivo, o JPA não está criando o banco de dados automaticamente. Desta forma, gerei o esquema relacional em `create.sql` e executei manualmente via PgAdmin. Desta forma, foi necessário executar comandos de permissões para o usuário `apf_user` do banco de dados `apf_db`.
```sql
GRANT ALL PRIVILEGES ON DATABASE apf_db TO apf_user;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public to apf_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public to apf_user;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public to apf_user;
```

Como os testes não estavam executando com a criação do esquema, essa parte foi removida. Ajustamos o nome da tabela `user` para `users`, pois o nome `user` é uma *palavra reservada* do Postgres.
```sql
CREATE SCHEMA IF NOT EXISTS apf
    AUTHORIZATION apf_user;
```

2. Povoamento do Banco de Dados
```sql
    INSERT INTO role VALUES (1,'ADMIN');
    INSERT INTO role VALUES (2,'USER');

    INSERT INTO attribution VALUES (1,'PROJECT MANAGER');
    INSERT INTO attribution VALUES (2,'PROJECT MEMBER');
```
## Padrões de Codificação do Editor
* Insert spaces for tabs
* Tab policy para Spaces only
* Indentation size: 4
* Tab size: 4


<a id="des"></a> 

# Desenvolvimento
Para iniciar o desenvolvimento, é necessário clonar o projeto do GitHub num diretório de sua preferência:
```bash
cd "diretório de sua preferência"
git clone https://github.com/labens-ufrn/apf-bsi.git
```

<a id="bui"></a> 

# Build e Execução

Esse projeto faz uso do framework spring-boot que encapsula todas as dependências no arquivo jar.
Para o build do projeto, execute os comandos abaixo:
```java
mvn clean
mvn install
Vá para o diretório /target
java -jar apf-bsi-0.0.1-SNAPSHOT.jar
```
O comando irá baixar todas as dependências do projeto e criar um diretório target com os artefatos construídos, que incluem o arquivo jar do projeto. Além disso, serão executados os testes unitários, e se algum falhar, o Maven exibirá essa informação no console.
Se tudo ocorreu como esperado vai conseguir acessar estes endereços com sucesso.
```bash
http://localhost:8080/apf/
http://localhost:8080/apf/login
http://localhost:8080/apf/registration
```

<a id="tes"></a> 

# Testes
Para rodar os testes, utilize o comando abaixo:
```java
mvn test
```

<a id="lic"></a> 

# licença

```bash
MIT
```
<a id="ref"></a> 

# Referências

Desenvolvido a partir do tutorial e do código linkado abaixo:


* https://medium.com/@gustavo.ponce.ch/spring-boot-spring-mvc-spring-security-mysql-a5d8545d837d
* https://github.com/gustavoponce7/SpringSecurityLoginTutorial
* https://franckaragao.wordpress.com/2016/08/23/integracao-continua-com-o-travis-ci-em-projetos-java-usando-o-maven/
* https://github.com/lowrin/spring-boot-input-autocomplete-example

## Tutoriais
- Na página [Tutoriais](docs/Tutorials.md) temos a lista de tutoriais consultados.
## Artigos
- Na página [Artigos](docs/referencias.md) temos a lista de artigos consultados.

