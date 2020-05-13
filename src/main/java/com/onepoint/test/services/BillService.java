package com.onepoint.test.services;

import java.util.List;

import com.onepoint.test.entities.Bill;

public interface BillService {

	public Bill CreateBill(Bill bill);

	public List<Bill> getAllBills();

	public List<Bill> getSortedBills();

}
