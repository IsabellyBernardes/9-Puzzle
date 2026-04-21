# 9-Puzzle

Estrutura padrão de um projeto Java com Maven.

## Estrutura

```text
.
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com/isabelly/puzzle/App.java
│   │   └── resources
│   └── test
│       ├── java
│       │   └── com/isabelly/puzzle/AppTest.java
│       └── resources
```

## Como executar

```bash
mvn compile
mvn test
mvn exec:java -Dexec.mainClass="com.isabelly.puzzle.App"
```
