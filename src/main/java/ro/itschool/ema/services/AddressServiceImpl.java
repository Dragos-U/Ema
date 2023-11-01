package ro.itschool.ema.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
}
