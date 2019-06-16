package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.OrderService;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * @param  authorization the first {@code String} to check if the access is available.
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

        //Check if the coupon name is empty
        if(couponName.isEmpty())
        {
            throw new CouponNotFoundException("CPF-002",
                    "Coupon name field should not be empty");
        }

        // Get coupon details
        CouponEntity couponDetails = orderService.getCoupon(couponName,bearerToken);

        // Create response
        CouponDetailsResponse couponDetailsResponse = new CouponDetailsResponse();
        if(couponDetails==null)
        {
            couponDetailsResponse = null;
            throw new CouponNotFoundException("CPF-001",
                    "No coupon by this name");
        } else {

                     couponDetailsResponse.id(UUID.fromString(couponDetails.getUuid()))
                    .couponName(couponDetails.getCoupon_name())
                    .percent(couponDetails.getPercent());
        }

        // Return coupon details response
        return new ResponseEntity<CouponDetailsResponse>(couponDetailsResponse, HttpStatus.OK);
    }

}
