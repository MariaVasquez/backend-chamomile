package org.user.api.userchamomile.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.user.api.userchamomile.entities.Address;
import org.user.api.userchamomile.entities.User;
import org.user.api.userchamomile.error.CustomException;
import org.user.api.userchamomile.repositories.AddressRepository;
import org.user.api.userchamomile.repositories.UserRepository;
import org.user.api.userchamomile.services.AddressService;
import org.user.api.userchamomile.util.ResponseCode;

import java.util.List;

@Service
public class AddressImpl implements AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Address> getAddressesByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> {
                    logger.error("Error finding user by username: {}", username);
                    return new CustomException(ResponseCode.LCO003, ResponseCode.LCO003.getHtmlMessage());
                });
        return addressRepository.findByUserId(user.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public Address getAddress(long id) {
        return addressRepository.findById(id).orElseThrow(()-> {
            logger.error("Error finding address by id: {}", id);
            return new CustomException(ResponseCode.LCO002, ResponseCode.LCO002.getHtmlMessage());
        });
    }

    @Transactional
    @Override
    public Address createAddress(Address address, String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(()-> {
                    logger.error("Error finding user by username: {}", userName);
                    return new CustomException(ResponseCode.LCO003, ResponseCode.LCO003.getHtmlMessage());
                });
        address.setUser(user);
        return addressRepository.save(address);
    }

    @Transactional
    @Override
    public Address updateAddress(Address address, String username) {
        if (address.getId().describeConstable().isEmpty()){
            throw new CustomException(ResponseCode.LCO008, ResponseCode.LCO008.getHtmlMessage());
        }
        getAddress(address.getId());
        return createAddress(address, username);
    }

    @Transactional
    @Override
    public void deleteAddress(long id) {
        try {
            addressRepository.deleteAddressBy(id);
        }catch (Exception e) {
             logger.error("Error while deleting address : {}", e.getMessage());
            throw new CustomException(ResponseCode.LCO000, ResponseCode.LCO000.getHtmlMessage(), e.getMessage());
        }

    }
}
