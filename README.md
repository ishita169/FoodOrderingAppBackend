# FoodOrderingAppBackend
Food Ordering App - Backend Part

Rest API Endpoints-II
Restaurant Controller
The following API endpoints must be implemented in the 'RestaurantController' class:

Get All Restaurants - "/restaurant"

It should be a GET request and will not require any parameter from the customer.

When any customer tries to access this endpoint, it should retrieve all the restaurants in order of their ratings and display the response in a JSON format with the corresponding HTTP status.
Within each restaurant, the list of categories should be displayed in a categories string in alphabetical order of their category name and items shouldn’t be displayed.
Here is a link to a sample JSON response.
 
 Rest API Endpoints-I
Customer Controller
Note: "@CrossOrigin" annotation has to be added in this controller which will help to share resources across origin, which will be required when your frontend and backend will be running on different ports.

The following API endpoints must be implemented in the 'CustomerController' class:

signup - "/customer/signup"

It should be a POST request

This endpoint requests for all the attributes in “SignupCustomerRequest” about the customer.

If the contact number provided already exists in the current database, throw ‘SignUpRestrictedException’ with the message code (SGR-001) and message (This contact number is already registered! Try other contact number.) and their corresponding HTTP status.

If any field other than last name is empty,  throw ‘SignUpRestrictedException’ with the message code (SGR-005) and message (Except last name all fields should be filled) and their corresponding HTTP status.

If the email ID provided by the customer is not in the correct format, i.e., not in the format of xxx@xx.xx (here, x is a variable and can be a number or a letter if the alphabet), throw ‘SignUpRestrictedException’ with the message code (SGR-002) and message (Invalid email-id format!).

If the contact number provided by the customer is not in correct format, i.e., it does not contain only numbers and has more or less than 10 digits,  throw ‘SignUpRestrictedException’ with the message code (SGR-003) and message (Invalid contact number!) with the corresponding HTTP status.

If the password provided by the customer is weak, i.e., it doesn’t have at least eight characters and does not contain at least one digit, one uppercase letter, and one of the following characters [#@$%&*!^] ,  throw ‘SignUpRestrictedException’ with the message code (SGR-004) and message (Weak password!) with the corresponding HTTP status.

Else, save the customer information in the database and return the “uuid” of the registered customer and status “CUSTOMER SUCCESSFULLY REGISTERED” in the JSON response with the corresponding HTTP status. Also, make sure to save the password after encrypting it using “PasswordCryptographyProvider” class given in the stub file.

 

2.  login- "/customer/login"

This endpoint is used for customer authentication. Customer authenticates in the application and after successful authentication, JWT token is given to a customer.

It should be a POST request

This endpoint requests for the Customer credentials to be passed in the authorization header as part of Basic authentication. You need to pass “Basic username:password” (username:password encoded to Base64 format) in the authorization header. Here username is the contact number of the user.

If the Basic authentication is not provided incorrect format, throw “AuthenticationFailedException” with the message code (ATH-003) and message (Incorrect format of decoded customer name and password) along with the corresponding HTTP status.

If the contact number provided by the customer does not exist, throw “AuthenticationFailedException” with the message code (ATH-001) and message (This contact number has not been registered!) along with the corresponding HTTP status.

If the password provided by the customer does not match the password in the existing database, throw “AuthenticationFailedException” with the message code (ATH-002) and message (Invalid Credentials) along with the corresponding HTTP status.

Else, save the customer login information in the database and return the uuid, first name, last name, contact number, and email address of the authenticated customer from “customers” table and message “LOGGED IN SUCCESSFULLY” in the Json response with the corresponding HTTP status. Note that the “JwtAccessToken” class has been given in the stub file to generate an access token.

Also, return the access token in the Response Header which will be used by the customer for any further operation in the Application. This is also similar to the signin endpoint in the quora project.

Here is how the response should look like:



 

 

 

3. logout - "/customer/logout"

This endpoint is used to logout from the FoodOrderingApp Application. The customer cannot access any other authenticated endpoint once he is logged out of the application.

It should be a POST request.

Access to this endpoint requires authentication.

This endpoint must request the access token of the signed in customer in authorization Request Header.

If the access token provided by the customer does not exist in the database, throw “AuthorizationFailedException” with the message code (ATHR-001) and message (Customer is not Logged in.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the customer has already logged out, throw “AuthorizationFailedException” with the message code (ATHR-002) and message (Customer is logged out. Log in again to access this endpoint.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the session has expired, throw “AuthorizationFailedException” with the message code (ATHR-003) and message (Your session is expired. Log in again to access this endpoint.) along with the corresponding HTTP status.

Else, update the LogoutAt time of the customer in the database and return the “uuid” of the signed out customer from “customers” table and message “LOGGED OUT SUCCESSFULLY” in the JSON response with the corresponding HTTP status.

 

4. Update - “/customer”

It should be a PUT request.

This endpoint requests for all the attributes in “UpdateCustomerRequest” about the customer.

Access to this endpoint requires authentication.

This endpoint must request the access token of the signed in customer in authorization Request Header. You need to pass Bearer before the access-token. You should pass the access-token like this in the authorization parameter of the request header.



 

 

If firstname field is empty, throw “UpdateCustomerException” with the message code (UCR-002) and message (First name field should not be empty) along with the corresponding HTTP status.

If the access token provided by the customer does not exist in the database, throw “AuthorizationFailedException” with the message code (ATHR-001) and message (Customer is not Logged in.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the customer has already logged out, throw “AuthorizationFailedException” with the message code (ATHR-002) and message (Customer is logged out. Log in again to access this endpoint.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the session has expired, throw “AuthorizationFailedException” with the message code (ATHR-003) and message (Your session is expired. Log in again to access this endpoint.) along with the corresponding HTTP status.

Else, return uuid, first name and last name of the customer from “customers” table and the message “CUSTOMER DETAILS UPDATED SUCCESSFULLY” in the JSON response with the corresponding HTTP status.


 

5. Change Password - “/customer/password”

It should be a PUT request.

This endpoint requests for all the attributes in “UpdatePasswordRequest” about the customer.

Access to this endpoint requires authentication.

This endpoint must request the access token of the signed in customer in authorization Request Header.

If the old or new password field is empty, throw “UpdateCustomerException” with the message code (UCR-003) and message (No field should be empty) along with the corresponding HTTP status.

If the access token provided by the customer does not exist in the database, throw “AuthorizationFailedException” with the message code (ATHR-001) and message (Customer is not Logged in.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the customer has already logged out, throw “AuthorizationFailedException” with the message code (ATHR-002) and message (Customer is logged out. Log in again to access this endpoint.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the session has expired, throw “AuthorizationFailedException” with the message code (ATHR-003) and message (Your session is expired. Log in again to access this endpoint.) along with the corresponding HTTP status.

If the new password provided by the customer is weak, i.e., it doesn’t have at least eight characters and does not contain at least one digit, one uppercase letter, and one of the following characters [#@$%&*!^] ,  throw “UpdateCustomerException” with the message code (UCR-001) and message (Weak password!) with the corresponding HTTP status.

If the old password field entered is incorrect, throw “UpdateCustomerException” with the message code (UCR-004) and message (Incorrect old password!) along with the corresponding HTTP status.

Else, return uuid of the customer and message “CUSTOMER PASSWORD UPDATED SUCCESSFULLY” in the JSON response with the corresponding HTTP status.

Address Controller
The following API endpoints must be implemented in the 'Address Controller' class:

Save Address - “/address”

It should be a POST request.

This endpoint requests for all the attributes in “SaveAddressRequest” about the customer.

Access to this endpoint requires authentication.

This endpoint must request the access token of the signed in customer in authorization Request Header.

This endpoint requests for all the attributes in “SaveAddressRequest” about the customer.

If the access token provided by the customer does not exist in the database, throw “AuthorizationFailedException” with the message code (ATHR-001) and message (Customer is not Logged in.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the customer has already logged out, throw “AuthorizationFailedException” with the message code (ATHR-002) and message (Customer is logged out. Log in again to access this endpoint.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the session has expired, throw “AuthorizationFailedException” with the message code (ATHR-003) and message (Your session is expired. Log in again to access this endpoint.) along with the corresponding HTTP status.

If any field is empty, throw “SaveAddressException” with the message code (SAR-001) and message (No field can be empty) and their corresponding HTTP status.

If the pincode entered is invalid (i.e it does not include only numbers or its size is not six), throw “SaveAddressException” with the message code (SAR-002) and message (Invalid pincode) and their corresponding HTTP status.

If the state uuid entered does not exist in the database, throw “AddressNotFoundException” with the message code (ANF-002) and message (No state by this id) and their corresponding HTTP status.

Else, return uuid of the address saved and message “ADDRESS SUCCESSFULLY REGISTERED” in the JSON response with the corresponding HTTP status.

 

Get All Saved Addresses - “/address/customer”

It should be a GET request.

Access to this endpoint requires authentication.

This endpoint must request the access token of the signed in customer in authorization Request Header.

If the access token provided by the customer does not exist in the database, throw “AuthorizationFailedException” with the message code (ATHR-001) and message (Customer is not Logged in.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the customer has already logged out, throw “AuthorizationFailedException” with the message code (ATHR-002) and message (Customer is logged out. Log in again to access this endpoint.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the session has expired, throw “AuthorizationFailedException” with the message code (ATHR-003) and message (Your session is expired. Log in again to access this endpoint.) along with the corresponding HTTP status.

Else, return the list of saved address in descending order of their saved time in the Json response with the corresponding HTTP status.

Here is the link to a sample JSON response.

 

Delete Saved Address - “/address/{address_id}”

It should be a DELETE request.

This endpoint must request the following value from the customer as a path variable:

Address UUID - String

Access to this endpoint requires authentication.

This endpoint must request the access token of the signed in customer in authorization Request Header.

If the access token provided by the customer does not exist in the database, throw “AuthorizationFailedException” with the message code (ATHR-001) and message (Customer is not Logged in.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the customer has already logged out, throw “AuthorizationFailedException” with the message code (ATHR-002) and message (Customer is logged out. Log in again to access this endpoint.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the session has expired, throw “AuthorizationFailedException” with the message code (ATHR-003) and message (Your session is expired. Log in again to access this endpoint.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the user who has logged in is not the same user who has created the address, throw “AuthorizationFailedException” with the message code (ATHR-004) and message (You are not authorized to view/update/delete any one else's address ) along with the corresponding HTTP status.

If address id field is empty, throw “AddressNotFoundException” with the message code (ANF-005) and message (Address id can not be empty) and their corresponding HTTP status.

If address id entered is incorrect, throw “AddressNotFoundException” with the message code (ANF-003) and message (No address by this id) and their corresponding HTTP status.

Else, return uuid of the address deleted and message “ADDRESS DELETED SUCCESSFULLY” in the JSON response with the corresponding HTTP status.


 

Get All States - “/states”

It should be a GET request will not require any parameter from the customer.

When any customer tries to access this endpoint, it should retrieve all the states present in the database and display the response in a JSON format with the corresponding HTTP status.

Here is the link to a sample JSON response.

 

Get Restaurant/s by Name - “/restaurant/name/{reastaurant_name}”

It should be a GET request.

This endpoint must request the following value from the customer as a path variable:

Restaurant name - String

If the restaurant name field entered by the customer is empty, throw “RestaurantNotFoundException” with the message code (RNF-003) and message (Restaurant name field should not be empty) and their corresponding HTTP status.

If there are no restaurants by the name entered by the customer, return an empty list with corresponding HTTP status.

Here, it is not necessary for the restaurant name to exactly match that in the database. Even if there is a partial match, all the restaurants corresponding to that name should be returned in alphabetical order of their names. For example., if a customer searches with the word “pizza”, restaurants such as “Pizza Hut”, “Joey’s Pizza Club”, “Mojo Pizza”, “Indipizzamania” etc. will be returned.

Within each restaurant, the list of categories should be displayed in a categories string, in alphabetical order of their names and the items shouldn’t be displayed.

Here, the name searched should not be case sensitive.

Here is a link to a sample JSON response.

Get Restaurants by Category Id “/restaurant/category/{category_id}”

It should be a GET request.

This endpoint must request the following value from the customer as a path variable:

Category UUID - String

If the category id field entered by the customer is empty, throw “CategoryNotFoundException” with the message code (CNF-001) and message (Category id field should not be empty) and their corresponding HTTP status.

If there is no category by the uuid entered by the customer, throw “CategoryNotFoundException” with the message code (CNF-002) and message (No category by this id) and their corresponding HTTP status.

If there are no restaurants under the category entered by the customer, return an empty list with corresponding HTTP status.

If the category id entered by the customer matches any category in the database, it should retrieve all the restaurants under this category in alphabetical order and then display the response in a JSON format with the corresponding HTTP status.

Within each restaurant, the list of categories should be displayed in a categories string, in alphabetical order and the items shouldn’t be displayed.

Here is a link to a sample JSON response.

Get Restaurant by Restaurant ID - “/api/restaurant/{restaurant_id}”

It should be a GET request.

This endpoint must request the following value from the customer as a path variable:
Restaurant UUID - String
If the restaurant id field entered by the customer is empty, throw “RestaurantNotFoundException” with the message code (RNF-002) and message (Restaurant id field should not be empty) and their corresponding HTTP status.
If there is no restaurant by the uuid entered by the customer, throw “RestaurantNotFoundException” with the message code (RNF-001) and message (No restaurant by this id) and their corresponding HTTP status.
If the restaurant id entered by the customer matches any restaurant in the database, it should retrieve that restaurant’s details and then display the response in the JSON format with the corresponding HTTP status.
The restaurant detail should have all the items it contains grouped by their categories in alphabetical order.
Here is a link to a sample JSON response.

 

Update Restaurant Details- “/api/restaurant/{restaurant_id}”

It should be a PUT request.

This endpoint must request the following value from the customer:

Customer Rating - Double (Request Parameter)

Restaurant UUID - String (Path Variable)

Access to this endpoint requires authentication.

This endpoint must request the access token of the signed in customer in authorization Request Header.

If the access token provided by the customer does not exist in the database, throw “AuthorizationFailedException” with the message code (ATHR-001) and message (Customer is not Logged in.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the customer has already logged out, throw “AuthorizationFailedException” with the message code (ATHR-002) and message (Customer is logged out. Log in again to access this endpoint.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the session has expired, throw “AuthorizationFailedException” with the message code (ATHR-003) and message (Your session is expired. Log in again to access this endpoint.) along with the corresponding HTTP status.

If the customer has successfully logged in, and:

If the restaurant id field entered by the customer is empty, throw “RestaurantNotFoundException” with the message code (RNF-002) and message (Restaurant id field should not be empty) and their corresponding HTTP status.

If there is no restaurant by the uuid entered by the customer, throw “RestaurantNotFoundException” with the message code (RNF-001) and message (No restaurant by this id) and their corresponding HTTP status.

If the customer rating field entered by the customer is empty or is not in the range of 1 to 5, throw “InvalidRatingException” with the message code (IRE-001) and message (Restaurant should be in the range of 1 to 5) and their corresponding HTTP status.

If the restaurant id entered by the customer matches any restaurant in the database, it should update that restaurant’s rating in the database along with the number of customers who have rated it. Then return the uuid of the restaurant updated and message “RESTAURANT RATING UPDATED SUCCESSFULLY” in the JSON response with the corresponding HTTP status. 

Here, customerRating is the average rating of any restaurant and numberCustomersRated is the number of customers who have rated this restaurant. So, make sure that you update both the average rating and the number of customers who gave ratings. The rating ranges from 0 to 5.

Item Controller
The following API endpoints must be implemented in the 'ItemController' class:

Get Top 5 Items by Popularity - “/item/restaurant/{restaurant_id}

It should be a GET request.

This endpoint must request the following value from the customer as a path variable:

Restaurant UUID - String 

If there is no restaurant by the uuid entered by the customer, throw “RestaurantNotFoundException” with the message code (RNF-001) and message (No restaurant by this id) and their corresponding HTTP status.

If the restaurant id entered by the customer matches any restaurant in the database, it should retrieve the top five items of that restaurant based on the number of times that item was ordered and then display the response in a JSON format with the corresponding HTTP status.

Here is a link to a sample JSON response.

Category Controller
The following API endpoints must be implemented in the 'CategoryController' class:

Get All Categories - “/category”

It should be a GET request and will not require any parameters from the user.

Else, it should retrieve all the categories present in the database, ordered by their name and display the response in a JSON format with the corresponding HTTP status.

Here is a link to a sample JSON response.

 

Get Category by Id - “/category/{category_id}”

It should be a GET request.

This endpoint must request the following value from the customer as a path variable:

Category UUID - String

If the category id field is empty, throw “CategoryNotFoundException” with the message code (CNF-001) and message (Category id field should not be empty) and their corresponding HTTP status.

If there are no categories available by the id provided, throw “CategoryNotFoundException” with the message code (CNF-002) and message (No category by this id) and their corresponding HTTP status.

If the category id entered by the customer matches any category in the database, it should retrieve that category with all items within that category and then display the response in a JSON format with the corresponding HTTP status. Also, the name searched should not be case sensitive.

Here is a link to a sample JSON response.

Rest API Endpoints-III
Order Controller
The following API endpoints must be implemented in the 'OrderController' class:

Get Coupon by Coupon Name - “/order/coupon/{coupon_name}”

It should be a GET request.

This endpoint must request the following values from the customer as a path variable:

Coupon Name: String

Access to this endpoint requires authentication.

This endpoint must request the access token of the signed in customer in authorization Request Header.

If the access token provided by the customer does not exist in the database, throw “AuthorizationFailedException” with the message code (ATHR-001) and message (Customer is not Logged in.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the customer has already logged out, throw “AuthorizationFailedException” with the message code (ATHR-002) and message (Customer is logged out. Log in again to access this endpoint.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the session has expired, throw “AuthorizationFailedException” with the message code (ATHR-003) and message (Your session is expired. Log in again to access this endpoint.) along with the corresponding HTTP status.

If the customer has logged in successfully, and:

If the coupon name entered by the customer matches any coupon in the database, retrieve the coupon details and display the response in a JSON format with the corresponding HTTP status.

If the coupon name entered by the customer does not match any coupon that exists in the database, throw “CouponNotFoundException” with the message code (CPF-001) and message (No coupon by this name) and their corresponding HTTP status.

If the coupon name entered by the customer is empty, throw “CouponNotFoundException” with the message code (CPF-002) and message (Coupon name field should not be empty) and their corresponding HTTP status.

Here is a link to a sample JSON response.
 

Get Past Orders of User - “/order”

It should be a GET request.

Access to this endpoint requires authentication.

This endpoint must request the access token of the signed in customer in authorization Request Header.

If the access token provided by the customer does not exist in the database, throw “AuthorizationFailedException” with the message code (ATHR-001) and message (Customer is not Logged in.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the customer has already logged out, throw “AuthorizationFailedException” with the message code (ATHR-002) and message (Customer is logged out. Log in again to access this endpoint.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the session has expired, throw “AuthorizationFailedException” with the message code (ATHR-003) and message (Your session is expired. Log in again to access this endpoint.) along with the corresponding HTTP status.

If the customer has logged in successfully:

Else, retrieve all the past orders from the customer sorted by their order date, with the newest order first, and return them in a JSON format with the corresponding HTTP status.

Here is a link to a sample JSON response.

 

Save Order - “/order”

It should be a POST request.

This endpoint rests for all the attributes in “SaveOrderRequest” from the customer.

Access to this endpoint requires authentication.

This endpoint must request the access token of the signed in customer in authorization Request Header.

If the access token provided by the customer does not exist in the database, throw “AuthorizationFailedException” with the message code (ATHR-001) and message (Customer is not Logged in.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the customer has already logged out, throw “AuthorizationFailedException” with the message code (ATHR-002) and message (Customer is logged out. Log in again to access this endpoint.) along with the corresponding HTTP status.

If the access token provided by the customer exists in the database, but the session has expired, throw “AuthorizationFailedException” with the message code (ATHR-003) and message (Your session is expired. Log in again to access this endpoint.) along with the corresponding HTTP status.

If the coupon uuid entered by the customer does not match any coupon that exists in the database, throw “CouponNotFoundException” with the message code (CPF-002) and message (No coupon by this id) and their corresponding HTTP status.

If the address uuid entered by the customer does not match any address that exists in the database, throw “AddressNotFoundException” with the message code (ANF-003) and message (No address by this id) and their corresponding HTTP status.

If the address uuid entered by the customer does not belong to him, throw “AuthorizationFailedException” with the message code (ATHR-004) and message (You are not authorized to view/update/delete any one else's address) and their corresponding HTTP status.

If the payment uuid entered by the customer does not match any payment method that exists in the database, throw “PaymentMethodNotFoundException” with the message code (PNF-002) and message (No payment method found by this id) and their corresponding HTTP status.

If there is no restaurant by the restaurant uuid entered by the customer, throw “RestaurantNotFoundException” with the message code (RNF-001) and message (No restaurant by this id) and their corresponding HTTP status.

If there are no items by the item uuid entered by the customer, throw “ItemNotFoundException” with the message code (INF-003) and message (No item by this id exist) and their corresponding HTTP status.

Else, save the order in the database and return the uuid of the order generated and message “ORDER SUCCESSFULLY PLACED” in the JSON response with the corresponding HTTP status.

 

Payment Controller
The following API endpoint must be implemented in the 'PaymentController' class:

Get Payment Methods - “/payment”

It should be a GET request and does not require any parameter from the customer.

Retrieve all the payment methods and return them in the JSON format with the corresponding HTTP status.

Here is a link to a sample JSON response.


