package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * This api endpoint is used to signup a new customer
     *
     * @param signupCustomerRequest this argument contains all the attributes required to create a new customer in the database
     *
     * @return ResponseEntity<SignupCustomerResponse> type object along with HttpStatus CREATED
     *
     * @throws SignUpRestrictedException if customer details are not as per guidelines
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, path = "/customer/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupCustomerResponse> signup(
            @RequestBody(required = false) final SignupCustomerRequest signupCustomerRequest)
            throws SignUpRestrictedException
    {
        // Validation for required fields
        if (
                signupCustomerRequest.getFirstName().equals("") ||
                        signupCustomerRequest.getEmailAddress().equals("") ||
                        signupCustomerRequest.getContactNumber().equals("") ||
                        signupCustomerRequest.getPassword().equals("")
        ) {
            throw new SignUpRestrictedException("SGR-005", "Except last name all fields should be filled");
        }

        final CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setUuid(UUID.randomUUID().toString());
        customerEntity.setFirstName(signupCustomerRequest.getFirstName());
        customerEntity.setLastName(signupCustomerRequest.getLastName());
        customerEntity.setEmail(signupCustomerRequest.getEmailAddress());
        customerEntity.setContactNumber(signupCustomerRequest.getContactNumber());
        customerEntity.setPassoword(signupCustomerRequest.getPassword());

        final CustomerEntity createdCustomerEntity = customerService.saveCustomer(customerEntity);
        SignupCustomerResponse customerResponse = new SignupCustomerResponse()
                .id(createdCustomerEntity.getUuid()).status("CUSTOMER SUCCESSFULLY REGISTERED");
        return new ResponseEntity<SignupCustomerResponse>(customerResponse, HttpStatus.CREATED);
    }

    /**
     * This api endpoint is used to login an existing customer
     *
     * @param authorization customer credentials in 'Basic Base64<contactNumber:password>' format
     *
     * @return ResponseEntity<LoginResponse> type object along with HttpStatus OK
     *
     * @throws AuthenticationFailedException if customer credentials authentication fails
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, path = "/customer/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> login(
            @RequestHeader("authorization") final String authorization)
            throws AuthenticationFailedException
    {
        byte[] decode;
        String contactNumber;
        String customerPassword;
        try {
            decode = Base64.getDecoder().decode(authorization.split("Basic ")[1]);
            String decodedText = new String(decode);
            String[] decodedArray = decodedText.split(":");
            contactNumber = decodedArray[0];
            customerPassword = decodedArray[1];
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException ex) {
            throw new AuthenticationFailedException("ATH-003", "Incorrect format of decoded customer name and password");
        }

        CustomerAuthEntity createdCustomerAuthEntity = customerService.authenticate(contactNumber, customerPassword);

        LoginResponse loginResponse = new LoginResponse()
                .id(createdCustomerAuthEntity.getCustomer()
                        .getUuid()).message("LOGGED IN SUCCESSFULLY");

        loginResponse.setId(createdCustomerAuthEntity.getCustomer().getUuid());
        loginResponse.setFirstName(createdCustomerAuthEntity.getCustomer().getFirstName());
        loginResponse.setLastName(createdCustomerAuthEntity.getCustomer().getLastName());
        loginResponse.setContactNumber(createdCustomerAuthEntity.getCustomer().getContactNumber());
        loginResponse.setEmailAddress(createdCustomerAuthEntity.getCustomer().getEmail());

        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token", createdCustomerAuthEntity.getAccessToken());
        List<String> header = new ArrayList<>();
        header.add("access-token");
        headers.setAccessControlExposeHeaders(header);

        return new ResponseEntity<LoginResponse>(loginResponse, headers, HttpStatus.OK);
    }

    /**
     * This api endpoint is used to logout an existing customer
     *
     * @param authorization customer login access token in 'Bearer <access-token>' format
     *
     * @return ResponseEntity<LogoutResponse> type object along with HttpStatus OK
     *
     * @throws AuthorizationFailedException if validation on customer access token fails
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, path = "/customer/logout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LogoutResponse> logout(
            @RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException
    {
        String accessToken = authorization.split("Bearer ")[1];
        CustomerAuthEntity customerAuthEntity = customerService.logout(accessToken);
        LogoutResponse logoutResponse = new LogoutResponse()
                .id(customerAuthEntity.getCustomer().getUuid()).message("LOGGED OUT SUCCESSFULLY");
        return new ResponseEntity<LogoutResponse>(logoutResponse, HttpStatus.OK);
    }

    /**
     * This api endpoint is used to update an existing customer
     *
     * @param updateCustomerRequest this argument contains all the attributes required to update a customer in the database
     * @param authorization customer access token in 'Bearer <access-token>' format
     *
     * @return ResponseEntity<UpdateCustomerResponse> type object along with HttpStatus OK
     *
     * @throws AuthorizationFailedException if validation on customer access token fails
     * @throws UpdateCustomerException if first name is not provided in updateCustomerRequest param
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT, path = "/customer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdateCustomerResponse> update(
            @RequestBody(required = false) final UpdateCustomerRequest updateCustomerRequest,
            @RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException, UpdateCustomerException
    {
        if (updateCustomerRequest.getFirstName().equals("")) {
            throw new UpdateCustomerException("UCR-002", "First name field should not be empty");
        }

        String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);

        customerEntity.setFirstName(updateCustomerRequest.getFirstName());
        if (!updateCustomerRequest.getLastName().equals("")) {
            customerEntity.setLastName(updateCustomerRequest.getLastName());
        }

        CustomerEntity updatedCustomerEntity = customerService.updateCustomer(customerEntity);
        UpdateCustomerResponse customerResponse = new UpdateCustomerResponse()
                .id(updatedCustomerEntity.getUuid()).status("CUSTOMER DETAILS UPDATED SUCCESSFULLY");
        customerResponse.setFirstName(updatedCustomerEntity.getFirstName());
        customerResponse.setLastName(updatedCustomerEntity.getLastName());
        return new ResponseEntity<UpdateCustomerResponse>(customerResponse, HttpStatus.OK);
    }

    /**
     *
     * @param updatePasswordRequest this argument contains all the attributes required to update a customer's password in the database
     * @param authorization customer access token in 'Bearer <access-token>' format
     *
     * @return ResponseEntity<UpdatePasswordResponse> type object along with HttpStatus OK
     *
     * @throws AuthorizationFailedException if validation on customer access token fails
     * @throws UpdateCustomerException if old or new password is not provided
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT, path = "/customer/password", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdatePasswordResponse> changePassword(
            @RequestBody(required = false) final UpdatePasswordRequest updatePasswordRequest,
            @RequestHeader("authorization") final String authorization)
            throws AuthorizationFailedException, UpdateCustomerException
    {
        if (updatePasswordRequest.getOldPassword().equals("") || updatePasswordRequest.getNewPassword().equals("")) {
            throw new UpdateCustomerException("UCR-003", "No field should be empty");
        }

        String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);

        CustomerEntity updatedCustomerEntity = customerService.updateCustomerPassword(
                updatePasswordRequest.getOldPassword(),
                updatePasswordRequest.getNewPassword(),
                customerEntity
        );

        UpdatePasswordResponse updatePasswordResponse = new UpdatePasswordResponse()
                .id(updatedCustomerEntity.getUuid())
                .status("CUSTOMER PASSWORD UPDATED SUCCESSFULLY");
        return new ResponseEntity<UpdatePasswordResponse>(updatePasswordResponse, HttpStatus.OK);
    }
}
