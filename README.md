# FoodOrderingAppBackend
Food Ordering App - Backend Part


address-controller : Address Controller 
------------------------------------------
POST /address
saveAddress

GET /address/customer
getAllSavedAddresses

DELETE /address/{address_id}
deleteSavedAddress

GET /states
getAllStates

category-controller : Category Controller
--------------------------------------------
GET /category
getAllCategories

GET /category/{category_id}
getCategoryById

customer-controller : Customer Controller 
------------------------------------------
PUT /customer
update

POST /customer/login
login

POST /customer/logout
logout

PUT /customer/password
changePassword

POST /customer/signup
signup

item-controller : Item Controller 
---------------------------------------
GET /item/restaurant/{restaurant_id}
getItemsByPopularity

order-controller : Order Controller
---------------------------------------
GET /order
getPastOrdersByCustomer

POST /order
saveOrderByCustomer

GET /order/coupon/{coupon_name}
getCoupon

payment-controller : Payment Controller 
----------------------------------------

GET /payment
getAllPaymentMethods

restaurant-controller : Restaurant Controller 
----------------------------------------------
GET /restaurant
getAllRestaurants

GET /restaurant/category/{category_id}
getRestaurantsByCategoryId

GET /restaurant/name/{restaurant_name}
getRestaurantsByName

GET /restaurant/{restaurant_id}
getRestaurantById

PUT /restaurant/{restaurant_id}
updateRestaurantDetails













