package org.user.api.userchamomile.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.user.api.userchamomile.dto.GenericResponseDto;
import org.user.api.userchamomile.entities.Address;
import org.user.api.userchamomile.services.AddressService;
import org.user.api.userchamomile.util.JwtUtils;
import org.user.api.userchamomile.util.ResponseCode;

import java.util.List;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/register")
    public GenericResponseDto<?> register(@Valid @RequestBody Address address, BindingResult result, HttpServletRequest request) {
        return new GenericResponseDto<>(ResponseCode.LCO001, ResponseCode.LCO001.getHtmlMessage(), addressService.createAddress(address, JwtUtils.readJWT(request)));
    }

    @PutMapping("/edit")
    public GenericResponseDto<?> edit(@Valid @RequestBody Address address, BindingResult result, HttpServletRequest request) {
        return new GenericResponseDto<>(ResponseCode.LCO004, ResponseCode.LCO004.getHtmlMessage(), addressService.updateAddress(address, JwtUtils.readJWT(request)));
    }

    @DeleteMapping("/delete")
    public GenericResponseDto<?> delete(@RequestParam Long addressId) {
        addressService.deleteAddress(addressId);
        return new GenericResponseDto<>(ResponseCode.LCO005, ResponseCode.LCO005.getHtmlMessage(), addressId);
    }

    @GetMapping
    public GenericResponseDto<List<Address>> getAddresses(HttpServletRequest request) {
        List<Address> addresses = addressService.getAddressesByUser(JwtUtils.readJWT(request));
        return new GenericResponseDto<>(ResponseCode.LCO006, ResponseCode.LCO006.getHtmlMessage(), addresses);
    }


    @GetMapping("/by-id")
    public GenericResponseDto<Address> getAddress(@RequestParam Long addressId) {
        Address addresses = addressService.getAddress(addressId);
        return new GenericResponseDto<>(ResponseCode.LCO006, ResponseCode.LCO006.getHtmlMessage(), addresses);
    }
}
