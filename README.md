# 📜 Índice

- [Objetivo do projeto](#objetivo)
- [Tecnologias Utilizadas](#tec)
- [Como rodar o projeto](#rodar)
- [Endpoints](#endpoints)
- [Desafios atendidos](#desafios)
- [Estrutura do projeto](#estrutura)

## Objetivo do projeto mini-autorizador <a name="objetivo"></a>

Projeto desenvolvido com o objetivo de criar um autorizador de pagamentos via cartão, fazendo algumas validações das operações realizadas.

## ✔️ Tecnologias Utilizadas <a name="tec"></a>

- **Java Spring Boot**: Framework para criação de aplicações Java.
- **Spring Data JPA**: Abstração sobre JPA para simplificar o acesso a dados.
- **Hibernate**: Implementação do JPA para mapeamento objeto-relacional.
- **Lombok**: Biblioteca para simplificar a criação de classes Java.
- **MapStruct**: Utilizado para conversão de objetos entre diferentes camadas do sistema.
- **Docker**: Ferramenta para criação de contêineres.
- **MySQL**: Sistema de gerenciamento de banco de dados relacional.
- **Clean Architecture**: A arquitetura do projeto segue o padrão Clean Architecture, promovendo a separação de responsabilidades e facilitando a manutenção e evolução do código.
- **Builder**: Utilizado para a criação de objetos de forma clara e concisa.
- **Mockito**: Framework de teste para Java que permite a criação de mocks de forma simples.
- **Jacoco**: Ferramenta de cobertura de código para Java que gera relatórios detalhados sobre a cobertura dos testes.
- **SonarQube**: Plataforma de análise estática de código que fornece uma visão abrangente da qualidade do software.

## 🔨 Como rodar o projeto <a name="rodar"></a>

#### Clonando projeto

```
  git clone https://github.com/fabio-barrufi/autorizador.git
```

#### Subindo docker

```
cd docker
docker-compose up
```

#### Swagger disponível no seguinte link:

```
  http://localhost:8080/swagger-ui.html
```

#### Após subir o docker, rodar o seguinte comando para instalação das dependências do projeto maven:

```
  mvn install
```

#### Você pode utilizar o seguinte comando para subir rodar o projeto, ou então diretamente por uma IDE de sua preferência

```
  mvn spring-boot:run
```

## 🎯 Endpoints <a name="endpoints"></a>

### Criar Novo Cartão

**Method**: POST
**URL**: `http://localhost:8080/cartoes`

**Body (json)**:

```json
{
  "numeroCartao": "6549873025634501",
  "senha": "1234"
}
```

### Possíveis Respostas:

Criação com sucesso:

**Status Code**: 201

**Body (json)**:

```json
{
  "senha": "1234",
  "numeroCartao": "6549873025634501"
}
```

Caso o cartão já exista:

**Status Code**: 422

**Body (json)**:

```json
{
  "senha": "1234",
  "numeroCartao": "6549873025634501"
}
```

### Obter Saldo do Cartão

**Method**: GET
**URL**: `http://localhost:8080/cartoes/{numeroCartao}`

Parâmetros:

{numeroCartao}: O número do cartão que se deseja consultar.

### Possíveis Respostas:

Obtenção com sucesso:

**Status Code**: 200

`Body: 495.15`

Caso o cartão não exista:

**Status Code**: 404

`Sem Body`

### Realizar uma Transação

**Method**: POST
**URL**: http://localhost:8080/transacoes

**Body (json)**:

```json
{
  "numeroCartao": "6549873025634501",
  "senhaCartao": "1234",
  "valor": 10.0
}
```

### Possíveis Respostas:

Transação realizada com sucesso:

**Status Code**: 201

`Body: OK`

Caso alguma regra de autorização tenha barrado a mesma:

**Status Code**: 422

`Body: SALDO_INSUFICIENTE | SENHA_INVALIDA | CARTAO_INEXISTENTE (dependendo da regra que impediu a autorização)`

#### ✅ Desafios atendidos <a name="desafios"></a>

##### Não utilizar if:

Todas as verificações e validações foram feitas utilizando outras abordagens, como Optional e orElseThrow, conforme o desafio proposto.

##### Concorrência:

Para garantir a consistência das transações e evitar problemas de concorrência, utilizei a palavra-chave `synchronized` no método `salvarNovoSaldoCartao` durante o desenvolvimento e teste. Esta abordagem foi escolhida para assegurar que apenas uma thread possa executar este método por vez, evitando a atualização simultânea do saldo do cartão.

No entanto, em um ambiente de produção com alta demanda e necessidade de escalabilidade, essa abordagem não é a mais adequada. Em cenários reais de produção, é ideal utilizar mecanismos como filas de mensagens (Kafka, RabbitMQ, AWS SQS) para gerenciar transações de forma assíncrona e eficiente. As filas permitem que as transações sejam processadas em ordem e garantem a consistência dos dados, mesmo sob alta carga.

#### 🛑 Possíveis melhorias futuras

- Salvar a senha e o número do cartão encryptada no banco de dados

#### 🚀 Estrutura utilizada <a name="estrutura"></a>

O projeto está estruturado de acordo com os princípios da Clean Architecture. Abaixo estão algumas das principais camadas e seus componentes:

##### Camada de Entrypoint

- Responsável pela entrada das requisições HTTP no serviço, é nela onde ficam minhas controllers. Não contém regras de negócio, mas pode ser feita a validação de campos obrigatórios vindas na requisição, por exemplo.

##### Camada de Use Cases

- Responsável pela lógica de negócios e aplicação. Contém as interfaces e implementações dos casos de uso.

##### Camada de Gateways

- Responsável pela comunicação com fontes externas de dados, como bancos de dados ou serviços externos. Implementa as interfaces definidas na camada de Use Cases.

##### Camada de Domínio

- Contém as entidades de domínio da aplicação, que representam os conceitos centrais e as regras de negócio da aplicação. As entidades podem conter métodos que representam operações relacionadas a elas, mas a lógica de negócio mais complexa deve estar nos casos de uso.

##### Camada de Mapeamento

- Responsável pela conversão de objetos entre diferentes camadas.

##### Tratamento de Exceções

A aplicação possui um tratamento genérico para exceções, permitindo a personalização do status e do corpo da resposta. As exceções lançadas são:

- **CampoInvalidoException**: Lançada quando algum campo recebido na request não atende aos padrões definidos.
- **CartaoExistenteException**: Lançada quando o usuário tenta criar um cartão que já existe na nossa base de dados.
- **CartaoInexistenteException**: Lançada quando uma transação tenta ser realizada com um cartão inexistente ou ao tentar consultar o saldo de um cartão inexistente.
- **SaldoInsuficienteException**: Lançada quando o saldo do cartão é insuficiente para realizar a transação.
- **SenhaInvalidaException**: Lançada quando a senha do cartão está incorreta ao tentar realizar a transação.
