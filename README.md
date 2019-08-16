# Analisador de Pontos de Função

[![Build Status](https://travis-ci.com/labens-ufrn/apf-bsi.svg?branch=master)](https://travis-ci.com/labens-ufrn/apf-bsi)

Sistema para facilitar a contagem na análise por pontos de função (APF) desenvolvido para ser utilizado nas disciplinas de Engenharia de Software do curso de Bacharelado em Sistemas de Informação do CERES/UFRN.

## Padrões de Codificação

* Insert spaces for tabs
* Tab policy para Spaces only
* Indentation size: 4
* Tab size: 4

## Pré-requisitos

* JDK 8
* MariaDB ou MySql
* Criação do Banco de Dados (dev e test) e Usuário

```sql
    CREATE DATABASE apf_db;
    CREATE DATABASE apf_db_test;

    CREATE USER 'apf_user'@'localhost' IDENTIFIED BY '12345';

    GRANT ALL ON apf_db.* TO 'apf_user'@'localhost';
    GRANT ALL ON apf_db_test.* TO 'apf_user'@'localhost';
```

* Povoamento do Banco de Dados

```sql
    INSERT INTO role VALUES (1,'ADMIN');
    INSERT INTO role VALUES (2,'USER');

    INSERT INTO attribution VALUES (1,'PROJECT MANAGER');
    INSERT INTO attribution VALUES (2,'PROJECT MEMBER');
```
## Execução

Esse projeto faz uso do framework spring-boot que encapsula todas as dependências no arquivo jar.

1. mvn clean
2. mvn install
3. Go to the target folder
4. java -jar apf-bsi-0.0.1-SNAPSHOT.jar

- http://localhost:8080/apf/
- http://localhost:8080/apf/login
- http://localhost:8080/apf/registration

## Referências

Desenvolvido a partir do tutorial e do código linkado abaixo:

* https://medium.com/@gustavo.ponce.ch/spring-boot-spring-mvc-spring-security-mysql-a5d8545d837d
* https://github.com/gustavoponce7/SpringSecurityLoginTutorial
* https://franckaragao.wordpress.com/2016/08/23/integracao-continua-com-o-travis-ci-em-projetos-java-usando-o-maven/
* https://github.com/lowrin/spring-boot-input-autocomplete-example

### Tutoriais

Na página [Tutoriais](Tutorials.md) temos a lista de tutoriais consultados.
