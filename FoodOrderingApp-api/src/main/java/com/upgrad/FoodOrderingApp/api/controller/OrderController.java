package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.OrderService;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    /**
     * @param  authorization the first {@code String} to check if the access is available.
     * @param couponName Coupon name to be looked for
     * @return ResponseEntity is returned with Status OK.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/order/coupon/{coupon_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CouponDetailsResponse> getCoupon(@PathVariable("coupon_name") final String couponName,@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, CouponNotFoundException {
        // Logic to handle Bearer <accesstoken>
        // User can give only Access token or Bearer <accesstoken> as input.
        String bearerToken = null;
        try {
            bearerToken = authorization.split("Bearer ")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            bearerToken = authorization;
        }

        //Validate customer
        CustomerEntity customerEntity = customerService.getCustomer(bearerToken);

        // Get coupon details
        CouponEntity couponDetails = orderService.getCoupon(couponName);

        // Create response
        CouponDetailsResponse couponDetailsResponse = new CouponDetailsResponse()
                .id(UUID.fromString(couponDetails.getUuid()))
                .couponName(couponDetails.getCouponName())
                .percent(couponDetails.getPercent());;

        // Return coupon details response
        return new ResponseEntity<CouponDetailsResponse>(couponDetailsResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<OrderList>> getPastOrdersByCustomer(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, CouponNotFoundException {
        // Logic to handle Bearer <accesstoken>
        // User can give only Access token or Bearer <accesstoken> as input.
        String bearerToken = null;
        try {
            bearerToken = authorization.split("Bearer ")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            bearerToken = authorization;
        }

        //Validate customer
        CustomerEntity customerEntity = customerService.getCustomer(bearerToken);

        // Get all past orders
        List<OrderEntity> allPastOrders = orderService.getAllPastOrdersByCustomer(customerEntity);

        // Create response
        List<OrderList> allPastOrdersResponses = new ArrayList<OrderList>();

        /*for (int i = 0; i < allPastOrders.size(); i++) {
            OrderList orderDetailsResponse = new OrderList()
                    .id(allPastOrders.);
            allPastOrdersResponses.add(orderDetailsResponse);
        }*/

        // Return response
        return new ResponseEntity<List<OrderList>>(allPastOrdersResponses, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveOrderResponse> saveOrderByCustomer(final SaveOrderRequest saveOrderRequest,@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException, CouponNotFoundException {
        // Logic to handle Bearer <accesstoken>
        // User can give only Access token or Bearer <accesstoken> as input.
        String bearerToken = null;
        try {
            bearerToken = authorization.split("Bearer ")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            bearerToken = authorization;
        }
        // Creating entity for further update


        // Return response

        SaveOrderResponse saveOrderResponse = new SaveOrderResponse();
        return new ResponseEntity<SaveOrderResponse>(saveOrderResponse, HttpStatus.OK);

    }
}
