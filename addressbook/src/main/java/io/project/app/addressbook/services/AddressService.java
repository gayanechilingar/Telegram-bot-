package io.project.app.addressbook.services;

import io.project.app.addressbook.domain.Account;
import io.project.app.addressbook.domain.Address;
import io.project.app.addressbook.dto.AccountDTO;
import io.project.app.addressbook.dto.AddressDTO;
//import io.project.app.addressbook.dto.AddressUpdateEmailDTO;
//import io.project.app.addressbook.dto.AddressUpdateZoomidDTO;
//import io.project.app.addressbook.dto.DTO;
import io.project.app.addressbook.repositories.AccountRepository;
import io.project.app.addressbook.repositories.AddressRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public List<Address> findByChatId(long ChatId) {
        return addressRepository.findByChatId(ChatId);
    }

    public Optional<Address> createAddress(AddressDTO addressDTO) {
        log.info("AddressService: creating address");
        addressDTO.setContactId(System.currentTimeMillis());
        Address convertedData = convertDtotoEntity(addressDTO);

        Optional<Address> findAddressByPhoneNumber = addressRepository.findByPhoneNumber(convertedData.getPhoneNumber());

        if (findAddressByPhoneNumber.isPresent()) {
            log.error("address with that phone number already exists");
            return Optional.empty();
        }
        final Address savedAddress = addressRepository.save(convertedData);

        return Optional.ofNullable(savedAddress);
    }

    public Optional<Address> updateEmail(Long contactId, String email) {
        Optional<Address> updatedEmail = addressRepository.findByContactId(contactId);
        if (!updatedEmail.isPresent()) {
            log.error("Contact with contact id " + contactId + " not found");
            return Optional.empty();
        }
        Address existingAddress = updatedEmail.get();
        existingAddress.setEmail(email);
        Address updatedEmailAddress = addressRepository.save(existingAddress);
        return Optional.of(updatedEmailAddress);
    }

    public Optional<Address> updateZoom(Long contactId, String zoomId) {
        Optional<Address> updatedZoom = addressRepository.findByContactId(contactId);
        if (!updatedZoom.isPresent()) {
            log.error("Contact with contact id " + contactId + " not found");
            return Optional.empty();
        }
        Address existingAddress = updatedZoom.get();
        existingAddress.setZoomId(zoomId);
        Address updatedZoomAddress = addressRepository.save(existingAddress);
        return Optional.of(updatedZoomAddress);
    }

    public String delete(Long contactId) {
        Optional<Address> updatedEmail = addressRepository.findByContactId(contactId);
        if (!updatedEmail.isPresent()) {
            log.error("Contact with contact id " + contactId + " not found");
            return "Contact not found";
        }
        Address existingAddress = updatedEmail.get();
        addressRepository.deleteByContactId(existingAddress.getContactId());

        return "deleted successfully";
    }

    public List<Address> findByName(String contactName) {
//        
        List<Address> foundContact = addressRepository.findByContactNameStartsWith(contactName);
        return foundContact;
    }

    public static AddressDTO convertEntityToDto(Address address) {
        AddressDTO addressDTOResponse = new AddressDTO();
        try {
            BeanUtils.copyProperties(address, addressDTOResponse);
        } catch (BeansException e) {
            throw new RuntimeException("Error creating AddressDTO response from Address", e);
        }
        return addressDTOResponse;
    }

    public static Address convertDtotoEntity(AddressDTO addressDTO) {
        Address addressResponse = new Address();
        try {
            BeanUtils.copyProperties(addressDTO, addressResponse);
        } catch (BeansException e) {
            throw new RuntimeException("Error creating Address from AddressDTO", e);
        }
        return addressResponse;
    }
}
