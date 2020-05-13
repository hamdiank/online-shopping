# Online Shopping

The task is to write an API for an online shopping site. It may be a web REST API or just a set a functions / classes.
Features:
*   list, create, find by id, delete a product
*	list, create, find by id, delete an order
*	sort the products by name, price or weight
*	sort the bills

Business rules:
*   A 5 % discount is applied to the Bill when the price of the Order exceeds 1000€
*	A Bill indicates the shipment costs : 25 € for every 10 kg (50 € for 20kg, 75 € for 30kg, etc.)
*	Bills are automatically generated when an Order status is set to paid
*	A paid Order cannot be deleted
*	An Order contains at least 1 Product

## Built With

* 	Maven
* 	JDK 1.8
* 	Spring Boot2
* 	H2 Database
* 	Swagger
* 	Junit5

## Running the application locally
you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

you can see and Test the different models and APi using Swagger interface:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)




