package com.test.onepoint.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.onepoint.test.entities.Order;
import com.onepoint.test.entities.Product;

public class OrderTest {

	@Test
	public void getProductAmount_shouldReturnTheSumOfAllProductsPrice() {
		// given
		Order order = new Order();
		Product product1 = new Product();
		product1.setPrice(new BigDecimal(10.2));
		Product product2 = new Product();
		product2.setPrice(new BigDecimal(7));
		Product product3 = new Product();
		product3.setPrice(new BigDecimal(8.3));
		order.setProducts(Arrays.asList(product1, product2, product3));

		// when
		BigDecimal expectedProductAmount = order.getProductsAmount();
		// then
		Assertions.assertEquals(expectedProductAmount, new BigDecimal("25.50"));
	}
}
