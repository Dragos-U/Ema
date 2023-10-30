package ro.itschool.ema.services;

import ro.itschool.ema.models.dtos.AddressDTO;

public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO);


    boolean deleteAddressById(Long id);


}
