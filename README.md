<!-- Título -->
<h1 align="center">
ANALISADOR DE PONTOS DE FUNÇÃO 
</h1>

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
* [Plano de Iteração](docs/docPlanIteracao.md)
* [Lista de User Stories](docs/docListUserStorie.md)
* [Contagem de Tamanho Funcional com Análise por Pontos de Função](docs/docContTamAPF.md)

<a id="pre"></a> 

# Pré-requisitos
Para executar o projeto, será necessário instalar os seguintes programas:
* JDK 11: Necessário para executar o projeto Java.
* Maven: Necessário para realizar o build do projeto Java.	* Maven: Necessário para realizar o build do projeto Java.
* Eclipse ou Vscode + extenssões: Java Extension Pack e Spring Boot Tools : Para o desenvolvimento do Projeto.
* MariaDB ou MySql: para persistência no Banco de Dados	* MySql ou MariaDb: para persistência no Banco de Dados
* Criação do Banco de Dados dev e test(apf_db e apf_db_test) e Usuário(apf_user) com sua devidas permissões.
Código abaixo:

1. Criação das bases de dados e usuário

```sql
    CREATE DATABASE apf_db CHARACTER SET UTF8 COLLATE utf8_bin;
    CREATE DATABASE apf_db_test CHARACTER SET UTF8 COLLATE utf8_bin;

    CREATE USER 'apf_user'@'localhost' IDENTIFIED BY '12345';

    GRANT ALL ON apf_db.* TO 'apf_user'@'localhost';
    GRANT ALL ON apf_db_test.* TO 'apf_user'@'localhost';
```

2.Povoamento do Banco de Dados

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

* Na página [Tutoriais](docs/Tutorials.md) temos a lista de tutoriais consultados.

## Artigos

* Na página [Artigos](docs/referencias.md) temos a lista de artigos consultados.
