package com.onepoint.test.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.onepoint.test.entities.Bill;
import com.onepoint.test.repositories.BillRepository;
import com.onepoint.test.services.BillService;
@Service
public class BillServiceImpl implements BillService {
	
	@Autowired
	BillRepository billRepository;

	@Override
	public Bill CreateBill(Bill bill) {
		return billRepository.save(bill);
	}

	@Override
	public List<Bill> getAllBills() {
		return billRepository.findAll();
	}

	@Override
	public List<Bill> getSortedBills() {
        return billRepository.findAll(Sort.by(Sort.Direction.ASC, "creationDate"));

	}

}
