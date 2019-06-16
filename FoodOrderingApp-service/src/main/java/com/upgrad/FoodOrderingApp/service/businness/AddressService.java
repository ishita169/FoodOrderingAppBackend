package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.AddressDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerAddressDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.dao.StateDao;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.AddressNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SaveAddressException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private CustomerAddressDao customerAddressDao;

    @Autowired
    private StateDao stateDao;

    @Autowired
    private OrderDao ordersDao;

    /**
     * This method implements the business logic for 'save address' endpoint
     *
     * @param addressEntity new address will be created from given AddressEntity object
     * @param customerEntity customer whose address is to be updated
     *
     * @return AddressEntity object
     *
     * @throws SaveAddressException exception if any of the validation fails on customer details
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity saveAddress(AddressEntity addressEntity, CustomerEntity customerEntity) throws SaveAddressException {

        // Validation for required fields
        if (
                addressEntity.getFlatBuilNo().equals("") ||
                        addressEntity.getLocality().equals("") ||
                        addressEntity.getCity().equals("") ||
                        addressEntity.getPincode().equals("") ||
                        addressEntity.getState() == null ||
                        addressEntity.getActive() == null
        ) {
            throw new SaveAddressException("SAR-001", "No field can be empty");
        }

        // Validation for format of Pincode
        // This is to validate Indian Post code.
        // [1-9]: Matches exactly one digit from 1 to 9.
        // [0-9]{5}: Matches exactly five digits in the inclusive range 0-9
        if(!addressEntity.getPincode().matches("^[1-9][0-9]{5}$")){
            throw new SaveAddressException("SAR-002", "Invalid pincode");
        }

        AddressEntity createdAddressEntity = addressDao.createAddress(addressEntity);

        CustomerAddressEntity customerAddressEntity = new CustomerAddressEntity();
        customerAddressEntity.setCustomer(customerEntity.getId());
        customerAddressEntity.setAddress(createdAddressEntity.getId());
        customerAddressDao.createCustomerAddress(customerAddressEntity);

        return createdAddressEntity;
    }

    /**
     * Returns state for a given UUID
     *
     * @param stateUUID UUID of state entity
     *
     * @return StateEntity object
     *
     * @throws AddressNotFoundException If validation on state fails
     */
    public StateEntity getStateByUUID(String stateUUID) throws AddressNotFoundException {
        StateEntity stateEntity = stateDao.getStateByUUID(stateUUID);
        if (stateEntity == null) {
            throw new AddressNotFoundException("ANF-002", "No state by this state id");
        }
        return stateEntity;
    }

    /**
     * Returns all addresses for a given customer
     *
     * @param customerEntity Customer whose addresses are to be returned
     *
     * @return List<AddressEntity> object
     */
    public List<AddressEntity> getAllAddress(CustomerEntity customerEntity) {
        return customerEntity.getAddresses();
    }

    /**
     * This method implements the business logic for 'Get All States' endpoint
     *
     * @return List<StateEntity> object
     */
    public List<StateEntity> getAllStates() {
        return stateDao.getAllStates();
    }

    /**
     * This method implements the business logic for 'Get Address by Id' endpoint
     *
     * @param addressUUID Address UUID
     * @param customerEntity Customer whose has made request
     *
     * @return AddressEntity object
     *
     * @throws AddressNotFoundException If validation on address fails
     * @throws AuthorizationFailedException If validation on customer fails
     */
    public AddressEntity getAddressByUUID(String addressUUID, CustomerEntity customerEntity) throws AddressNotFoundException, AuthorizationFailedException {
        AddressEntity addressEntity = addressDao.getAddressByUUID(addressUUID);

        if (addressEntity == null) {
            throw new AddressNotFoundException("ANF-003", "No address by this id");
        }

        if (!addressEntity.getCustomer().getUuid().equals(customerEntity.getUuid())) {
            throw new AuthorizationFailedException("ATHR-004", "You are not authorized to view/update/delete any one else's address");
        }

        return addressEntity;
    }

    /**
     * Deletes given address entity
     *
     * @param addressEntity Address to delete
     *
     * @return AddressEntity object
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public AddressEntity deleteAddress(AddressEntity addressEntity) {
        List<OrderEntity> ordersEntityList = ordersDao.getOrdersByAddress(addressEntity);
        if (ordersEntityList == null || ordersEntityList.isEmpty()) {
            return addressDao.deleteAddressEntity(addressEntity);
        }

        addressEntity.setActive(0);
        return addressDao.updateAddressEntity(addressEntity);
    }
}
