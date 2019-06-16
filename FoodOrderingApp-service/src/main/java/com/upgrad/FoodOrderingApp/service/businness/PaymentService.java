package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.PaymentDao;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import com.upgrad.FoodOrderingApp.service.exception.PaymentMethodNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

	@Autowired
	private PaymentDao paymentDao;

	/**
	 * This method implements the business logic for 'payment' endpoint
	 *
	 * @return List<PaymentEntity> object
	 */
	public List<PaymentEntity> getAllPaymentMethods() {
		return paymentDao.getAllPaymentMethods();
	}

	/**
	 * Returns payment for given UUID
	 *
	 * @param uuid UUID of payment entity
	 *
	 * @return PaymentEntity object
	 *
	 * @throws PaymentMethodNotFoundException If validation on payment fails
	 */
	public PaymentEntity getPaymentByUUID(String uuid) throws PaymentMethodNotFoundException {
		PaymentEntity paymentEntity = paymentDao.getPaymentByUUID(uuid);
		if (paymentEntity == null) {
			throw new PaymentMethodNotFoundException("PNF-002", "No payment method found by this id");
		}
		return paymentEntity;
	}
}