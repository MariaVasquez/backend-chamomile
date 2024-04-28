package org.user.api.userchamomile.services;

import org.user.api.userchamomile.entities.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAddressesByUser(String username);
    Address getAddress(long id);
    Address createAddress(Address address, String userName);
    Address updateAddress(Address address, String username);
    void deleteAddress(long id);
}
