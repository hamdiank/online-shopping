package com.onepoint.test.entities;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.util.CollectionUtils;

import com.onepoint.test.enumeration.OrderStatus;

@Entity
@Table(name = "ORDER_T")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private OrderStatus status = OrderStatus.PENDING;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "ORDER_PRODUCTS", joinColumns = @JoinColumn(name = "id_order"), inverseJoinColumns = @JoinColumn(name = "id_product"))
	private List<Product> products;
	private BigDecimal shipmentAmount;
	@Column
	private BigDecimal totalAmount;
	@Column
	private BigDecimal orderWeight;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private Bill bill;

	public Order() {
	}

	public Order(Long id, OrderStatus status, List<Product> products, BigDecimal shipmentAmount, BigDecimal totalAmount,
			BigDecimal orderWeight, Bill bill) {
		super();
		this.id = id;
		this.status = status;
		this.products = products;
		this.shipmentAmount = shipmentAmount;
		this.totalAmount = totalAmount;
		this.orderWeight = orderWeight;
		this.bill = bill;
	}

	public Order(List<Product> products) {
		this.products = products;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	/**
	 * Returns the shipment amount (25 for every 10 kg)
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getShipmentAmount() {
		return new BigDecimal(((int) Math.floor(getOrderWeight().intValue()) / 10) * 25);
	}

	public void setShipmentAmount(BigDecimal shipmentAmount) {
		this.shipmentAmount = shipmentAmount;
	}

	/**
	 * Returns the total amount of the order with discount and shipment
	 * 
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getTotalAmount() {
		BigDecimal total = getProductsAmount().add(getShipmentAmount());

		if (total.compareTo(BigDecimal.valueOf(1000.0)) > 0) {
			return total.subtract(total.multiply(new BigDecimal("0.05"))).setScale(2, BigDecimal.ROUND_HALF_EVEN);
		} else {
			return total;
		}
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * Returns total weight of products, rounded to 2 decimals
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getOrderWeight() {
		if (CollectionUtils.isEmpty(products)) {
			return BigDecimal.ZERO;
		}
		return products.stream().map(product -> product.getWeight()).reduce(BigDecimal.ZERO, BigDecimal::add)
				.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public void setOrderWeight(BigDecimal orderWeight) {
		this.orderWeight = orderWeight;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	/**
	 * Returns the total price of all products in the order, rounded to 2 decimals
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getProductsAmount() {
		if (CollectionUtils.isEmpty(products)) {
			return BigDecimal.ZERO;
		}
		return products.stream().map(product -> product.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Order order = (Order) o;
		return Objects.equals(id, order.id) && status == order.status && Objects.equals(products, order.products)
				&& Objects.equals(shipmentAmount, order.shipmentAmount)
				&& Objects.equals(totalAmount, order.totalAmount) && Objects.equals(orderWeight, order.orderWeight);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, status, products, shipmentAmount, totalAmount, orderWeight);
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", status=" + status + ", products=" + products + ", shipmentAmount="
				+ shipmentAmount + ", totalAmount=" + totalAmount + ", orderWeight=" + orderWeight + ", bill=" + bill
				+ "]";
	}

}
