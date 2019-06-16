package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CouponDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderItemDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private CustomerDao customerDao;

    /**
     * This method helps find coupon details by coupon name
     *
     * @param couponName Name of the coupon to get the details for
     *
     * @return CouponEntity object
     *
     * @throws CouponNotFoundException If coupon name is invalid
     */
    public CouponEntity getCouponByCouponName(String couponName) throws CouponNotFoundException {

        if (couponName.equals("")) {
            throw new CouponNotFoundException("CPF-002", "Coupon name field should not be empty");
        }

        CouponEntity couponEntity = couponDao.getCouponByCouponName(couponName);

        if (couponEntity == null) {
            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        }

        return couponEntity;
    }

    /**
     * Returns coupon by coupon UUID
     *
     * @param uuid UUID of coupon
     *
     * @return CouponEntity object
     *
     * @throws CouponNotFoundException If validation on coupon fails
     */
    public CouponEntity getCouponByCouponId(String uuid) throws CouponNotFoundException {
        CouponEntity couponEntity = couponDao.getCouponByCouponUUID(uuid);

        if (couponEntity == null) {
            throw new CouponNotFoundException("CPF-001", "No coupon by this id");
        }

        return couponEntity;
    }

    /**
     * Saves given order
     *
     * @param orderEntity Order details
     *
     * @return OrderEntity object
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderEntity saveOrder(OrderEntity orderEntity) {
        return orderDao.createOrder(orderEntity);
    }

    /**
     * Saves given order and item relation
     *
     * @param orderItemEntity Order and item to relate
     *
     * @return OrderItemEntity object
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderItemEntity saveOrderItem(OrderItemEntity orderItemEntity) {
        return orderItemDao.createOrderItemEntity(orderItemEntity);
    }

    /**
     * Returns orders by customer
     *
     * @param customerUUID UUID of customer
     *
     * @return List<OrderEntity> object
     */
    public List<OrderEntity> getOrdersByCustomers(String customerUUID) {
        return orderDao.getOrdersByCustomers(customerDao.getCustomerByUUID(customerUUID));
    }
}
