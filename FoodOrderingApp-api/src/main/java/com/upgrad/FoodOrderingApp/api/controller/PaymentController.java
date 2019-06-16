package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.upgrad.FoodOrderingApp.service.businness.PaymentService;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;

@RestController
@RequestMapping("/")
public class PaymentController {
	@Autowired
	private PaymentService patymentService;
	
	/**
	 * This api endpoint is used to retrieve all the payment method present in the database, ordered by their name
	 *
	 * @return ResponseEntity<PaymentListResponse> type object along with HttpStatus OK
	 */
	
	@CrossOrigin
	@RequestMapping(method = RequestMethod.GET , path = "/payment" , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	

	
	public ResponseEntity<PaymentListResponse> getAllPaymentDetails() {

		// get all categories ordered by name
		List<PaymentEntity> paymentEntityList = patymentService.getAllPaymentMethodByName();

		// create response
		PaymentListResponse paymentListResponse= new PaymentListResponse();
		
		for(PaymentEntity paymentEntity : paymentEntityList) {
			
			PaymentResponse paymentResponse = new PaymentResponse()
					.id(UUID.fromString(paymentEntity.getUuid()))
					.paymentName(paymentEntity.getPaymentName());
			paymentListResponse.addPaymentMethodsItem(paymentResponse);
		}

		return new ResponseEntity<PaymentListResponse>(paymentListResponse,HttpStatus.OK);
		
	}
	

	
}
