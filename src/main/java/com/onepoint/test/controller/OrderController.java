package com.onepoint.test.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onepoint.test.entities.Order;
import com.onepoint.test.exceptions.NoProductOrderException;
import com.onepoint.test.exceptions.PaidOrderException;
import com.onepoint.test.services.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderService orderService;

	@GetMapping("/orders")
	public List<Order> getAllOrders() {
		return orderService.getAllOrders();
	}

	@GetMapping("/{id}")
	public Order getOrderById(@PathVariable(value = "id") Long orderId) {
		return orderService.getOrderById(orderId);

	}

	@PostMapping("/add")
	public Order createOrder(@Valid @RequestBody Order order) throws NoProductOrderException {
		return orderService.createOrder(order);
	}

	@DeleteMapping("/{id}")
	public void deleteOrder(@PathVariable long id) throws PaidOrderException {
		orderService.deleteOrder(id);
	}

}
