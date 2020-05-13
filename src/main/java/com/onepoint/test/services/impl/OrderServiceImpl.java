package com.onepoint.test.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onepoint.test.entities.Bill;
import com.onepoint.test.entities.Order;
import com.onepoint.test.enumeration.OrderStatus;
import com.onepoint.test.exceptions.EntityNotFoundException;
import com.onepoint.test.exceptions.NoProductOrderException;
import com.onepoint.test.exceptions.PaidOrderException;
import com.onepoint.test.repositories.OrderRepository;
import com.onepoint.test.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	/**
	 *
	 */
	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	/**
	 *
	 */
	@Override
	public Order getOrderById(Long id) {

		return orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Order.class, id.toString()));

	}

	/**
	 * @param order
	 * @return created order if has at least one product
	 * @throws NoProductOrderException
	 *
	 */
	@Override
	public Order createOrder(Order order) throws NoProductOrderException {
		// order have at least one product
		if (order.getProducts().isEmpty()) {
			throw new NoProductOrderException("Order with no product");
		}
		// generate Bill
		if (order.getBill() == null && order.getStatus().equals(OrderStatus.PAID)) {
			order.setBill(new Bill(order.getTotalAmount(), LocalDate.now()));
		}
		return orderRepository.save(order);
	}

	/**
	 * Delete order if status not paid
	 * 
	 * @param id
	 */
	@Override
	public void deleteOrder(Long id) throws PaidOrderException {
		// get order to be deleted
		Optional<Order> order = orderRepository.findById(id);
		// PAID Order cannot be deleted
		if (order.isPresent()) {
			if (order.get().getStatus().equals(OrderStatus.PAID)) {
				throw new PaidOrderException("Cannot delete paid order");
			}
			orderRepository.delete(order.get());
		} else {
			throw new EntityNotFoundException(Order.class, id.toString());
		}

	}

}
