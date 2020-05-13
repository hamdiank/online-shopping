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

import com.onepoint.test.entities.Bill;
import com.onepoint.test.entities.Order;
import com.onepoint.test.exceptions.NoProductOrderException;
import com.onepoint.test.exceptions.PaidOrderException;
import com.onepoint.test.services.BillService;

@RestController
@RequestMapping("/bill")
public class BillController {

	@Autowired
	BillService billService;

	@GetMapping("/bills")
	public List<Bill> getAllBills() {
		return billService.getAllBills();
	}

	@PostMapping("/add")
	public Bill createBill(@Valid @RequestBody Bill bill) {
		return billService.CreateBill(bill);
	}

	@GetMapping("/sortedBill")
	public List<Bill> getSortedBills() {
		return billService.getSortedBills();
	}

}
