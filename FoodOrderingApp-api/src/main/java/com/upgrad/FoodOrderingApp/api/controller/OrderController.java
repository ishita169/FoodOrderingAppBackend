package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.*;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderEntity;
import com.upgrad.FoodOrderingApp.service.entity.OrderItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ItemService itemService;

    /**
     * This api endpoint is used to find coupon details by coupon name
     *
     * @param couponName Name of the coupon to get the details for
     * @param authorization Customer access token in 'Bearer <access-token>' format
     *
     * @return ResponseEntity<CouponDetailsResponse> type object along with HttpStatus OK
     *
     * @throws AuthorizationFailedException If validation on customer access token fails
     * @throws CouponNotFoundException If coupon name is invalid
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/order/coupon/{coupon_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CouponDetailsResponse> getCouponByCouponName(
            @PathVariable("coupon_name") final String couponName,
            @RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException, CouponNotFoundException
    {
        String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);

        CouponEntity couponEntity = orderService.getCouponByCouponName(couponName);

        CouponDetailsResponse couponDetailsResponse = new CouponDetailsResponse()
                .id(UUID.fromString(couponEntity.getUuid()))
                .couponName(couponEntity.getCouponName())
                .percent(couponEntity.getPercent());
        return new ResponseEntity<CouponDetailsResponse>(couponDetailsResponse, HttpStatus.OK);
    }

    /**
     * This api endpoint is used to find orders by customer
     *
     * @param authorization Customer access token in 'Bearer <access-token>' format
     *
     * @return ResponseEntity<CustomerOrderResponse> type object along with HttpStatus OK
     *
     * @throws AuthorizationFailedException If validation on access token fails
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/order", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CustomerOrderResponse> getCustomerOrders(
            @RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException
    {
        String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);

        // Get all orders by customer
        List<OrderEntity> orderEntityList = orderService.getOrdersByCustomers(customerEntity.getUuid());

        // Create response
        CustomerOrderResponse customerOrderResponse = new CustomerOrderResponse();

        for (OrderEntity orderEntity : orderEntityList) {

            OrderListCoupon orderListCoupon = new OrderListCoupon()
                    .id(UUID.fromString(orderEntity.getCoupon().getUuid()))
                    .couponName(orderEntity.getCoupon().getCouponName())
                    .percent(orderEntity.getCoupon().getPercent());

            OrderListPayment orderListPayment = new OrderListPayment()
                    .id(UUID.fromString(orderEntity.getPayment().getUuid()))
                    .paymentName(orderEntity.getPayment().getPaymentName());

            OrderListCustomer orderListCustomer = new OrderListCustomer()
                    .id(UUID.fromString(orderEntity.getCustomer().getUuid()))
                    .firstName(orderEntity.getCustomer().getFirstName())
                    .lastName(orderEntity.getCustomer().getLastName())
                    .emailAddress(orderEntity.getCustomer().getEmail())
                    .contactNumber(orderEntity.getCustomer().getContactNumber());

            OrderListAddressState orderListAddressState = new OrderListAddressState()
                    .id(UUID.fromString(orderEntity.getAddress().getState().getUuid()))
                    .stateName(orderEntity.getAddress().getState().getStatename());

            OrderListAddress orderListAddress = new OrderListAddress()
                    .id(UUID.fromString(orderEntity.getAddress().getUuid()))
                    .flatBuildingName(orderEntity.getAddress().getFlatBuilNo())
                    .locality(orderEntity.getAddress().getLocality())
                    .city(orderEntity.getAddress().getCity())
                    .pincode(orderEntity.getAddress().getPincode())
                    .state(orderListAddressState);

            OrderList orderList = new OrderList()
                    .id(UUID.fromString(orderEntity.getUuid()))
                    .bill(new BigDecimal(orderEntity.getBill()))
                    .coupon(orderListCoupon)
                    .discount(new BigDecimal(orderEntity.getDiscount()))
                    .date(orderEntity.getDate().toString())
                    .payment(orderListPayment)
                    .customer(orderListCustomer)
                    .address(orderListAddress);

            for (OrderItemEntity orderItemEntity : itemService.getItemsByOrder(orderEntity)) {

                ItemQuantityResponseItem itemQuantityResponseItem = new ItemQuantityResponseItem()
                        .id(UUID.fromString(orderItemEntity.getItem().getUuid()))
                        .itemName(orderItemEntity.getItem().getItemName())
                        .itemPrice(orderItemEntity.getItem().getPrice())
                        .type(ItemQuantityResponseItem.TypeEnum.fromValue(orderItemEntity.getItem().getType().getValue()));

                ItemQuantityResponse itemQuantityResponse = new ItemQuantityResponse()
                        .item(itemQuantityResponseItem)
                        .quantity(orderItemEntity.getQuantity())
                        .price(orderItemEntity.getPrice());

                orderList.addItemQuantitiesItem(itemQuantityResponse);
            }

            customerOrderResponse.addOrdersItem(orderList);
        }

        return new ResponseEntity<CustomerOrderResponse>(customerOrderResponse, HttpStatus.OK);
    }

    /**
     * This api endpoint is used to create new order
     *
     * @param saveOrderRequest this argument contains all the attributes required to create a new order in the database
     * @param authorization Customer access token in 'Bearer <access-token>' format
     *
     * @return ResponseEntity<SaveOrderResponse> type object along with HttpStatus CREATED
     *
     * @throws AuthorizationFailedException If validation on access token fails
     * @throws CouponNotFoundException If validation on coupon fails
     * @throws AddressNotFoundException If validation on address fails
     * @throws PaymentMethodNotFoundException If validation on payment fails
     * @throws RestaurantNotFoundException If validation on restaurant fails
     * @throws ItemNotFoundException If validation on item fails
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, path = "/order", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveOrderResponse> saveOrder(
            @RequestBody(required = false) final SaveOrderRequest saveOrderRequest,
            @RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException, CouponNotFoundException,
            AddressNotFoundException, PaymentMethodNotFoundException,
            RestaurantNotFoundException, ItemNotFoundException
    {
        String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);

        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUuid(UUID.randomUUID().toString());
        orderEntity.setCoupon(orderService.getCouponByCouponId(saveOrderRequest.getCouponId().toString()));
        orderEntity.setPayment(paymentService.getPaymentByUUID(saveOrderRequest.getPaymentId().toString()));
        orderEntity.setCustomer(customerEntity);
        orderEntity.setAddress(addressService.getAddressByUUID(saveOrderRequest.getAddressId(), customerEntity));
        orderEntity.setBill(saveOrderRequest.getBill().doubleValue());
        orderEntity.setDiscount(saveOrderRequest.getDiscount().doubleValue());
        orderEntity.setRestaurant(restaurantService.restaurantByUUID(saveOrderRequest.getRestaurantId().toString()));
        orderEntity.setDate(new Date());
        OrderEntity savedOrderEntity = orderService.saveOrder(orderEntity);

        for (ItemQuantity itemQuantity : saveOrderRequest.getItemQuantities()) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrder(savedOrderEntity);
            orderItemEntity.setItem(itemService.getItemByUUID(itemQuantity.getItemId().toString()));
            orderItemEntity.setQuantity(itemQuantity.getQuantity());
            orderItemEntity.setPrice(itemQuantity.getPrice());
            orderService.saveOrderItem(orderItemEntity);
        }

        SaveOrderResponse saveOrderResponse = new SaveOrderResponse()
                .id(savedOrderEntity.getUuid()).status("ORDER SUCCESSFULLY PLACED");
        return new ResponseEntity<SaveOrderResponse>(saveOrderResponse, HttpStatus.CREATED);
    }
}