# Bugs e links de soluções

## Falha ao carregar o HikariDataSouce

Ao executar `mvn test` seguinte erro estava ocorrendo:

```text
Failed to bind properties under '' to com.zaxxer.hikari.HikariDataSource.
```

Isso ocorria ao executar as classes de testes de controladores [LoginControllerTest](https://github.com/labens-ufrn/apf-bsi/blob/develop/src/test/java/br/ufrn/dct/apf/controller/LoginControllerTest.java) e [UserStoryControllerTest](https://github.com/labens-ufrn/apf-bsi/blob/develop/src/test/java/br/ufrn/dct/apf/controller/UserStoryControllerTest.java).

Para resolvermos acrescentamos o atributo `DataSouce` com a anotação `@MockBean` nas classes de testes de controladores:

```java
    @MockBean
    private DataSource dataSource;
```

Com esta modificação os testes executaram perfeitamente.
