package com.onepoint.test.services;

import java.util.List;

import com.onepoint.test.entities.Order;
import com.onepoint.test.exceptions.NoProductOrderException;
import com.onepoint.test.exceptions.PaidOrderException;

import javassist.NotFoundException;

public interface OrderService {

	public List<Order> getAllOrders();

	public Order getOrderById(Long id);

	public Order createOrder(Order order) throws NoProductOrderException;

	public void deleteOrder(Long id) throws PaidOrderException;

}
