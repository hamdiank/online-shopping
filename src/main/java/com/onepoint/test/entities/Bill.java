package com.onepoint.test.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private BigDecimal amount;
	@Column
	private LocalDate creationDate;

	public Bill() {
		super();
	}

	public Bill(BigDecimal amount, LocalDate creationDate) {
		super();
		this.amount = amount;
		this.creationDate = creationDate;
	}

	public Bill(Long id, BigDecimal amount, LocalDate creationDate) {
		super();
		this.id = id;
		this.amount = amount;
		this.creationDate = creationDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

}
