# Teste Prático

Este projeto Java foi desenvolvido como parte de um teste prático para uma empresa. Ele implementa um sistema para gerenciar informações de funcionários, seguindo os requisitos especificados.

## Requisitos

1. **Classe Pessoa**:
   - Atributos: nome (String) e data de nascimento (LocalDate).

2. **Classe Funcionário** (subclasse de Pessoa):
   - Atributos adicionais: salário (BigDecimal) e função (String).

3. **Classe Principal**:
   - Responsável por realizar diversas operações com os funcionários, como inserção, remoção, impressão, aumento salarial, agrupamento, entre outros.

### Funcionalidades Implementadas:

- **Inserção de Funcionários**:
  - Inicializa a lista de funcionários com dados fictícios.

- **Remoção de Funcionário**:
  - Remove o funcionário "João" da lista.

- **Impressão de Funcionários**:
  - Todos os funcionários são impressos com suas informações formatadas corretamente (nome, data de nascimento, salário e função).

- **Aumento Salarial**:
  - Aumenta o salário de todos os funcionários em 10%.

- **Agrupamento por Função**:
  - Agrupa os funcionários por função em um mapa, exibindo os funcionários agrupados de forma compacta.

- **Funcionários que Fazem Aniversário**:
  - Imprime os funcionários que fazem aniversário nos meses de outubro (10) e dezembro (12).

- **Funcionário Mais Velho**:
  - Identifica e imprime o funcionário mais velho, exibindo seu nome e idade.

- **Lista de Funcionários em Ordem Alfabética**:
  - Ordena e imprime a lista de funcionários por ordem alfabética de nome.

- **Total dos Salários**:
  - Calcula e imprime o total dos salários de todos os funcionários.

- **Salários Mínimos Recebidos**:
  - Calcula e imprime quantos salários mínimos cada funcionário recebe, considerando que o salário mínimo é R$1212.00.

## Dependências Utilizadas

- Spring Boot: Facilita o desenvolvimento de aplicativos Spring.
- Spring Boot Starter Data JPA: Facilita o acesso a dados com o Spring Data.
- Spring Boot Starter Web: Suporte para desenvolvimento de aplicativos web.
- Spring Boot DevTools: Ferramentas de desenvolvimento para agilizar o ciclo de desenvolvimento.
- H2 Database: Banco de dados em memória para ambiente de desenvolvimento.
- Spring Boot Starter Validation: Suporte para validação de dados.
- Springdoc OpenAPI UI: Ferramentas para documentação e visualização de APIs com OpenAPI 3/Swagger.
- Lombok: Biblioteca para reduzir o código boilerplate.

## Como Executar

1. Clone o repositório: `git clone https://github.com/seu-usuario/teste-pratico-iniflex.git`
2. Navegue até o diretório do projeto: `cd teste-pratico-iniflex`
3. Compile o projeto: `mvn clean package`
4. Execute a aplicação: `java -jar target/teste-pratico-iniflex-0.0.1-SNAPSHOT.jar`
5. Acesse a documentação da API: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Observações

- Certifique-se de ter o Maven e o Java instalados localmente.
- Os dados de funcionários são gerados aleatoriamente para fins de teste.

---

Desenvolvido por [José Antonio](https://github.com/Zezinhojun)
