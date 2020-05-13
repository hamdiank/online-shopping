package com.test.onepoint.service;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.onepoint.test.entities.Bill;
import com.onepoint.test.repositories.BillRepository;
import com.onepoint.test.services.BillService;
import com.onepoint.test.services.impl.BillServiceImpl;

@SpringBootTest(classes = BillServiceImpl.class)
public class BillServiceTest {

	@Autowired
	BillService billService;
	@MockBean
	BillRepository billRepository;

	@Test
	public void getAllBills_shouldReturnAllBills() {
		// given
		Bill bill1 = new Bill(1l, new BigDecimal(12.50), LocalDate.of(2020, 5, 9));
		Bill bill2 = new Bill(2l, new BigDecimal(80.00), LocalDate.of(2020, 4, 13));
		Bill bill3 = new Bill(3l, new BigDecimal(17.77), LocalDate.of(2020, 1, 19));
		List<Bill> givenBills = Arrays.asList(bill1, bill2, bill3);
		// when
		when(billRepository.findAll()).thenReturn(givenBills);

		List<Bill> expectedBills = billService.getAllBills();
		// then
		Assertions.assertEquals(givenBills, expectedBills);
	}

	@Test
	public void createBill_shouldReturnAllBill() {
		// given
		Long billId = 1l;
		Bill givenBill = new Bill(billId, new BigDecimal(12.50), LocalDate.of(2020, 5, 9));
		// when
		when(billRepository.save(givenBill)).thenReturn(givenBill);

		Bill expectedBill = billService.CreateBill(givenBill);
		// then
		Assertions.assertEquals(expectedBill, givenBill);
	}
	

}
