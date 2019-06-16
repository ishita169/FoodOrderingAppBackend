package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
     * @return CouponEntity object.
     */
    public CouponEntity getCoupon(String coupon_name) throws  CouponNotFoundException {

        CouponEntity couponEntity = null;

        //Check if the coupon name is empty
        if(coupon_name.isEmpty())
        {
            throw new CouponNotFoundException("CPF-002",
                    "Coupon name field should not be empty");
        }

        couponEntity = orderDao.getCouponByName(coupon_name);

        if(couponEntity==null)
        {
            throw new CouponNotFoundException("CPF-001",
                    "No coupon by this name");
        }
        return couponEntity;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<OrderEntity> getAllPastOrdersByCustomer(CustomerEntity customerEntity){
        return orderDao.getOrdersByCustomers(customerEntity);
    }
}
