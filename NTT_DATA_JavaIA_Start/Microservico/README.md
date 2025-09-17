# Desafio de Microserviços

Este repositório contém a implementação do desafio técnico de microserviços da NTT DATA. A solução está organizada como um projeto Maven multimódulo composto por quatro aplicações Spring Boot:

- `discovery-server` — Service Discovery Eureka do Spring Cloud Netflix.
- `api-gateway` — Spring Cloud Gateway com filtro simples baseado em token.
- `product-service` — Catálogo de produtos com endpoints CRUD, persistidos em um banco H2 em memória.
- `order-service` — Simulador de pedidos que consulta o catálogo e calcula totais para as requisições.

## Pré-requisitos

- JDK 17+
- Maven 3.9+

## Executando a stack

1. Inicie o servidor de descoberta:
   ```bash
   mvn -pl discovery-server spring-boot:run
   ```
2. Inicie o API Gateway:
   ```bash
   mvn -pl api-gateway spring-boot:run
   ```
3. Inicie o serviço de produtos:
   ```bash
   mvn -pl product-service spring-boot:run
   ```
4. Inicie o serviço de pedidos:
   ```bash
   mvn -pl order-service spring-boot:run
   ```

Todos os serviços se registram no Eureka (`http://localhost:8761`). Quando estiverem ativos, o API Gateway escuta na porta `8700` e encaminha as requisições para cada serviço. Inclua o header `Authorization: Bearer desafio-token` em toda chamada roteada pelo gateway.

## Endpoints úteis

- `GET http://localhost:8700/products` — lista os produtos do catálogo.
- `POST http://localhost:8700/products` — cria um produto. Exemplo de payload:
  ```json
  {
    "name": "Mouse Gamer",
    "description": "Sensor 16k DPI com iluminação RGB",
    "price": 249.90
  }
  ```
- `POST http://localhost:8700/orders` — simula um pedido. Exemplo de payload:
  ```json
  {
    "customer": "Alice",
    "items": [
      { "productId": 1, "quantity": 1 },
      { "productId": 2, "quantity": 2 }
    ]
  }
  ```
- `GET http://localhost:8700/orders/products` — lista os produtos vistos pelo serviço de pedidos.

O console do H2 está disponível em `http://localhost:8100/h2-console` (JDBC URL `jdbc:h2:mem:products-db`).

Os health checks do Actuator estão expostos em `/actuator/health` em cada serviço.


# Microservices Challenge

This repository contains the implementation of the NTT DATA microservices technical challenge. The solution is organised as a Maven multi-module project composed of four Spring Boot applications:

- `discovery-server` — Spring Cloud Netflix Eureka service discovery.
- `api-gateway` — Spring Cloud Gateway with a simple token based filter.
- `product-service` — Product catalogue with CRUD endpoints backed by an in-memory H2 database.
- `order-service` — Order simulator that queries the catalogue and calculates totals for incoming requests.

## Prerequisites

- JDK 17+
- Maven 3.9+

## Running the stack

1. Start the discovery server:
   ```bash
   mvn -pl discovery-server spring-boot:run
   ```
2. Start the API gateway:
   ```bash
   mvn -pl api-gateway spring-boot:run
   ```
3. Start the product service:
   ```bash
   mvn -pl product-service spring-boot:run
   ```
4. Start the order service:
   ```bash
   mvn -pl order-service spring-boot:run
   ```

All services register themselves with Eureka (`http://localhost:8761`). Once everything is running, the API Gateway listens on port `8700` and proxies requests to the individual services. Include the header `Authorization: Bearer desafio-token` on every request routed through the gateway.

## Useful endpoints

- `GET http://localhost:8700/products` — list catalogue products.
- `POST http://localhost:8700/products` — create a product. Sample payload:
  ```json
  {
    "name": "Gaming Mouse",
    "description": "16k DPI sensor with RGB lighting",
    "price": 249.90
  }
  ```
- `POST http://localhost:8700/orders` — simulate an order. Example payload:
  ```json
  {
    "customer": "Alice",
    "items": [
      { "productId": 1, "quantity": 1 },
      { "productId": 2, "quantity": 2 }
    ]
  }
  ```
- `GET http://localhost:8700/orders/products` — list products as seen by the order service.

The H2 console is available at `http://localhost:8100/h2-console` (JDBC URL `jdbc:h2:mem:products-db`).

Actuator health checks are exposed at `/actuator/health` on each service.

