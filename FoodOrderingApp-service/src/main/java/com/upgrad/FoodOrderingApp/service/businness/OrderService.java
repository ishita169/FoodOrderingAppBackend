package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;


    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerService customerService;


    /**
     * @param  coupon_name Name of the coupon to be accessed
     * @param  accessToken the first {@code String} to check if the access is available.
     * @return CouponEntity object.
     */
    public CouponEntity getCoupon(String coupon_name, String accessToken) throws AuthorizationFailedException {

        CouponEntity couponEntity = null;
        //boolean validateCustomer = customerService.checkCustomer(accessToken);
        // @Manasa
        // We should vaidate in Contoroller itself.
        // Call the customerService.getCustomer from contorller & throw auth exception
        //if(validateCustomer){
            couponEntity = orderDao.getCouponByName(coupon_name);
        //}
        return couponEntity;
    }

}
