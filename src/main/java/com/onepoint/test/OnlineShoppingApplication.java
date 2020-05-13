package com.onepoint.test;

import java.math.BigDecimal;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.onepoint.test.entities.Order;
import com.onepoint.test.entities.Product;
import com.onepoint.test.enumeration.OrderStatus;
import com.onepoint.test.services.OrderService;
import com.onepoint.test.services.ProductService;

@SpringBootApplication
public class OnlineShoppingApplication {
	private final Logger logger = LoggerFactory.getLogger(OnlineShoppingApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(OnlineShoppingApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(ProductService productService, OrderService orderService) {
		return (args) -> {
			Product product1 = new Product(1l, "Swiss cheese", new BigDecimal(775), new BigDecimal(50));
			Product product2 = new Product(2l, "strawberry", new BigDecimal(1000.50), new BigDecimal(25));
			Product product3 = new Product(3l, "fish", new BigDecimal(25.20), new BigDecimal(30));

			productService.createProduct(product1);
			productService.createProduct(product2);
			productService.createProduct(product3);
			logger.info("product1 " + product1.toString());
			logger.info("product1 " + product2.toString());

			logger.info("product1 " + product3.toString());

			Order order1 = new Order();
			Order order2 = new Order();
			// order 1
			order1.setProducts(Arrays.asList(product1, product2));
			order1.setOrderWeight(order1.getOrderWeight());
			order1.setShipmentAmount(order1.getShipmentAmount());
			order1.setTotalAmount(order1.getTotalAmount());
			// PAID order 2
			order2.setProducts(Arrays.asList(product3, product1));
			order2.setOrderWeight(order2.getOrderWeight());
			order2.setShipmentAmount(order2.getShipmentAmount());
			order2.setTotalAmount(order2.getTotalAmount());
			order2.setStatus(OrderStatus.PAID);
			orderService.createOrder(order1);
			orderService.createOrder(order2);

			logger.info("ordr " + order1.toString());
			logger.info("ordr " + order2.toString());
		};
	}
}
