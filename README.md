# FoodOrderingAppBackend
Food Ordering App - Backend Part


address-controller : Address Controller Show/Hide List Operations Expand Operations
POST /address
saveAddress

GET /address/customer
getAllSavedAddresses

DELETE /address/{address_id}
deleteSavedAddress

GET /states
getAllStates

category-controller : Category Controller Show/Hide List Operations Expand Operations
GET /category
getAllCategories

GET /category/{category_id}
getCategoryById

customer-controller : Customer Controller Show/Hide List Operations Expand Operations
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

item-controller : Item Controller Show/Hide List Operations Expand Operations
GET /item/restaurant/{restaurant_id}
getItemsByPopularity

order-controller : Order Controller Show/Hide List Operations Expand Operations
GET /order
getPastOrdersByCustomer

POST /order
saveOrderByCustomer

GET /order/coupon/{coupon_name}
getCoupon

payment-controller : Payment Controller Show/Hide List Operations Expand Operations
GET /payment
getAllPaymentMethods

restaurant-controller : Restaurant Controller Show/Hide List Operations Expand Operations
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













