package com.test.onepoint.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.onepoint.test.entities.Bill;
import com.onepoint.test.entities.Order;
import com.onepoint.test.entities.Product;
import com.onepoint.test.enumeration.OrderStatus;
import com.onepoint.test.exceptions.EntityNotFoundException;
import com.onepoint.test.exceptions.NoProductOrderException;
import com.onepoint.test.exceptions.PaidOrderException;
import com.onepoint.test.repositories.OrderRepository;
import com.onepoint.test.services.OrderService;
import com.onepoint.test.services.impl.OrderServiceImpl;

@SpringBootTest(classes = OrderServiceImpl.class)
public class OrderServiceTest {

	@Autowired
	OrderService orderService;
	@MockBean
	OrderRepository orderRepository;

	@Test
	public void getAllOrders_shouldReturnAllOrders() {
		// given
		Product product1 = new Product(1l, "Swiss cheese", new BigDecimal(5.70), new BigDecimal(2.50));
		Product product2 = new Product(2l, "strawberry", new BigDecimal(10.50), new BigDecimal(1.50));
		Product product3 = new Product(3l, "fish", new BigDecimal(17.50), new BigDecimal(12.50));

		Order order1 = new Order(1l, OrderStatus.PENDING, Arrays.asList(product1, product2), new BigDecimal(2.50),
				new BigDecimal(8.50), new BigDecimal(12.50), new Bill());
		Order order2 = new Order(2l, OrderStatus.PENDING, Arrays.asList(product1, product3), new BigDecimal(12.50),
				new BigDecimal(9.50), new BigDecimal(121.50), new Bill());

		List<Order> givenOrders = Arrays.asList(order1, order2);
		// when
		when(orderRepository.findAll()).thenReturn(givenOrders);

		List<Order> expectedProducts = orderService.getAllOrders();
		// then
		Assertions.assertEquals(expectedProducts, givenOrders);
	}

	@Test
	public void getOrderById_shouldReturnOrder() {
		// given
		Long orderId = 1l;
		Product product1 = new Product(1l, "Swiss cheese", new BigDecimal(5.70), new BigDecimal(2.50));
		Product product2 = new Product(2l, "strawberry", new BigDecimal(10.50), new BigDecimal(1.50));

		Order givenOrder = new Order(orderId, OrderStatus.PENDING, Arrays.asList(product1, product2),
				new BigDecimal(2.50), new BigDecimal(8.50), new BigDecimal(12.50), new Bill());

		// when
		when(orderRepository.findById(orderId)).thenReturn(Optional.of(givenOrder));

		Order expectedOrder = orderService.getOrderById(orderId);

		// then
		Assertions.assertEquals(expectedOrder, givenOrder);
	}

	@Test
	public void getOrderById_shouldThrowEntityNotFoundExceptionIfNoOrder() {
		// given
		Long orderId = 1l;
		Assertions.assertThrows(EntityNotFoundException.class, () -> {

			orderService.getOrderById(orderId);
		});
	}

	@Test
	public void createOrder_shouldReturnOrder() throws NoProductOrderException {
		// given
		Long orderId = 1l;
		Product product1 = new Product(1l, "Swiss cheese", new BigDecimal(5.70), new BigDecimal(2.50));
		Product product2 = new Product(2l, "strawberry", new BigDecimal(10.50), new BigDecimal(1.50));

		Order givenOrder = new Order(orderId, OrderStatus.PENDING, Arrays.asList(product1, product2),
				new BigDecimal(2.50), new BigDecimal(8.50), new BigDecimal(12.50), new Bill());

		// when
		when(orderRepository.save(givenOrder)).thenReturn(givenOrder);

		Order expectedOrder = orderService.createOrder(givenOrder);

		// then
		Assertions.assertEquals(expectedOrder, givenOrder);
	}

	@Test
	public void createOrder_shouldThrowNoProductOrderExceptionIfNoProduct() {
		// given
		Order givenOrder = new Order(1l, OrderStatus.PENDING, Arrays.asList(), new BigDecimal(2.50),
				new BigDecimal(8.50), new BigDecimal(12.50), new Bill());

		Assertions.assertThrows(NoProductOrderException.class, () -> {

			orderService.createOrder(givenOrder);
		});
	}

	@Test
	public void deleteOrder_shouldThrowEntityNotFoundExceptionIfNotFound() {
		// given
		Long orderId = 1l;
		Assertions.assertThrows(EntityNotFoundException.class, () -> {

			orderService.deleteOrder(orderId);
		});
	}

	@Test
	public void deleteOrder_shouldThrowPaidOrderExceptionIfOrderPaidStatus() {
		// given
		Long orderId = 1l;
		Product product1 = new Product(orderId, "Swiss cheese", new BigDecimal(5.70), new BigDecimal(2.50));
		Order givenOrder = new Order(orderId, OrderStatus.PAID, Arrays.asList(product1), new BigDecimal(2.50),
				new BigDecimal(8.50), new BigDecimal(12.50), new Bill());

		// when
		when(orderRepository.findById(orderId)).thenReturn(Optional.of(givenOrder));
		Assertions.assertThrows(PaidOrderException.class, () -> {

			orderService.deleteOrder(orderId);
		});
	}

	@Test
	public void deleteOrder_shouldBeExcuted() throws PaidOrderException {
		// given
		Long orderId = 1l;
		Product product1 = new Product(orderId, "Swiss cheese", new BigDecimal(5.70), new BigDecimal(2.50));
		Order givenOrder = new Order(orderId, OrderStatus.CANCELLED, Arrays.asList(product1), new BigDecimal(2.50),
				new BigDecimal(8.50), new BigDecimal(12.50), new Bill());

		// when
		when(orderRepository.findById(orderId)).thenReturn(Optional.of(givenOrder));

		orderService.deleteOrder(orderId);

		// then
		verify(orderRepository).delete(givenOrder);
	}
}
