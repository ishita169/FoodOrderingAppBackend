package com.upgrad.FoodOrderingApp.service.businness;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upgrad.FoodOrderingApp.service.dao.PaymentDao;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;

@Service
public class PaymentService {
	@Autowired
	private PaymentDao paymentDao;
	
	/**
	 * This method implements the business logic for 'payment' endpoint
	 *
	 * @return List<PaymentEntity> object
	 */
	public List<PaymentEntity> getAllPaymentMethodByName() {
		
		return paymentDao.getAllPaymentMethod().stream()
				.sorted(Comparator.comparing(PaymentEntity::getPaymentName))
				.collect(Collectors.toList());
		
		
	}

}


