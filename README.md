# Desafio-Zitrus
Serviço responsável pelos principais endpoints relacionados ao escopo do desafio

### Dependências

* Java 11
* Maven 3

### Banco de dados usados

* MySql
* H2 Banco em Memória

### Stack

* Spring-boot
* Lombok

### Acesso as URL's do projeto

**Recurso** | **Ambiente**
--- | --- 
Swagger | [localhost](http://localhost:3400/swagger-ui.html)



### Rodar o projeto

* mvn clean && mvn compile para gerar as classe necessárias

* mvn clean spring-boot:run -Dspring-boot.run.profiles=dev <- aqui vc pode defir a propertis que ele vai olhar: dev, prod ou test

### End point stock_movement

caso o movementType seja uma Saída

{
"amountMoved": null, 
"idProduct": 1,
"movementType": "Saída",
"quantityEntry": 10, 
"saleValue": null
}

caso o movementType seja uma Entrada

{
"amountMoved": 10,
"idProduct": 1,
"movementType": "Entrada",
"quantityEntry": null,
"saleValue": 690.00
}