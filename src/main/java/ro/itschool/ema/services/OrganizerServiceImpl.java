package ro.itschool.ema.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.itschool.ema.exceptions.OrganizerCreateException;
import ro.itschool.ema.models.dtos.AddressDTO;
import ro.itschool.ema.models.dtos.OrganizerDTO;
import ro.itschool.ema.models.entities.Address;
import ro.itschool.ema.models.entities.Organizer;
import ro.itschool.ema.repositories.AddressRepository;
import ro.itschool.ema.repositories.OrganizerRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrganizerServiceImpl implements OrganizerService {
    private final OrganizerRepository organizerRepository;
    private final AddressRepository addressRepository;
    private final ObjectMapper objectMapper;

    public OrganizerServiceImpl(OrganizerRepository organizerRepository, AddressRepository addressRepository, ObjectMapper objectMapper) {
        this.organizerRepository = organizerRepository;
        this.addressRepository = addressRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public OrganizerDTO createOrganizer(OrganizerDTO organizerDTO) {
        if (organizerRepository.existsByOrganizerName(organizerDTO.getOrganizerName()) || organizerRepository.existsByWebsite(organizerDTO.getWebsite())) {
            throw new OrganizerCreateException("Organizer already exists.");
        }
        Organizer organizerEntity = objectMapper.convertValue(organizerDTO, Organizer.class);

        if (organizerEntity.getAddress() != null) {
            Address savedAddress = addressRepository.save(organizerEntity.getAddress());
            organizerEntity.setAddress(savedAddress);
        }

        Organizer organizerResponseEntity = organizerRepository.save(organizerEntity);
        return objectMapper.convertValue(organizerResponseEntity, OrganizerDTO.class);
    }

    @Override
    public List<OrganizerDTO> getAllOrganizers() {
        List<OrganizerDTO> organizerDTOList = new ArrayList<>();
        List<Organizer> organizers = organizerRepository.findAll();
        for (Organizer organizer : organizers) {
            OrganizerDTO organizerDTO = objectMapper.convertValue(organizer, OrganizerDTO.class);
            organizerDTOList.add(organizerDTO);
        }
        return organizerDTOList;
    }
}
