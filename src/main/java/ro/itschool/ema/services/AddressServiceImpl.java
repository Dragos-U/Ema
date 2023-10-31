package ro.itschool.ema.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.itschool.ema.models.dtos.AddressDTO;
import ro.itschool.ema.models.entities.Address;
import ro.itschool.ema.repositories.AddressRepository;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService{
    private final AddressRepository addressRepository;

    private final ObjectMapper objectMapper;

    public AddressServiceImpl(AddressRepository addressRepository, ObjectMapper objectMapper){
        this.addressRepository = addressRepository;
        this.objectMapper = objectMapper;
    }


    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        Address addressEntity = objectMapper.convertValue(addressDTO, Address.class);
        Address saveAddressEntity = addressRepository.save(addressEntity);
        return objectMapper.convertValue(saveAddressEntity, AddressDTO.class);
    }


    @Override
    public boolean deleteAddressById(Long id) {
        if(addressRepository.existsById(id)){
            addressRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
