# Análise de Desempenho - Tabela Hash (Java 21)

Este projeto implementa uma Tabela Hash customizada para armazenar registros de livros lidos a partir de um arquivo CSV. O sistema executa testes de benchmark (inserção, busca e remoção) variando funções de hash e métodos de tratamento de colisão.

---

## Pré-requisitos

Para rodar este projeto, você precisa obrigatoriamente ter o **Java Development Kit (JDK) 21** instalado na sua máquina.

Para verificar se você tem a versão correta, abra o seu terminal (Prompt de Comando, PowerShell ou Terminal do Linux/Mac) e digite:

` ` `bash
java -version
` ` `

*A saída deve indicar a versão 21.*

---

## Estrutura de Pastas

O código possui um caminho de leitura de arquivo fixo no `CSVReader.java`. Se as pastas não estiverem **exatamente** com esta estrutura e nomenclatura, o programa vai falhar ao tentar ler os dados.

Crie uma pasta principal para o projeto (por exemplo, `Hash-Table-Implementation) e organize os arquivos **exatamente** da seguinte forma:

```text
Hash-Table-Implementation/
└── src/
    ├── data/
    │   └── ARQUIVO PARA TRABALHO AV3.csv
    ├── Book.java
    ├── CSVReader.java
    ├── HashTable.java
    ├── Main.java
    └── PerformanceAnalyzer.java
```

**Regras estritas para os arquivos:**
1. Os 5 arquivos `.java` devem ficar soltos na raiz da pasta `src`.
2. Dentro de `src`, crie uma pasta chamada `data`.
3. Coloque o seu arquivo CSV dentro da pasta `data` com o nome exato: `ARQUIVO PARA TRABALHO AV3.csv` (respeite as letras maiúsculas e os espaços).

---

## Como Executar o Projeto

Abra sua IDE de preferência e importe a pasta Hash-Table-Implementation. Depois, rode o `Main.java`

### O que deve acontecer:
O console começará a imprimir os resultados dos benchmarks. Você verá blocos de execução para todas as 6 combinações possíveis (3 métodos de Hash × 2 métodos de colisão), exibindo:
* Quantidade total de colisões.
* Fator de carga.
* Tempo médio de inserção, busca e remoção (em milissegundos).
* Resultados detalhados dos testes de busca (com ISBNs existentes e inexistentes).

---

## Possíveis Erros e Como Corrigir

* **`java.io.FileNotFoundException` ou `NullPointerException` ao iniciar:**
  Isso significa que o programa não encontrou o arquivo CSV. Volte ao **Passo 2** e certifique-se de que a pasta `src/data/` foi criada dentro da mesma pasta onde você está rodando `Main.java`, e que o nome do arquivo CSV está idêntico ao exigido.
