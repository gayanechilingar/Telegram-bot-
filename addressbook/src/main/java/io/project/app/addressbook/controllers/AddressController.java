package io.project.app.addressbook.controllers;

import io.project.app.addressbook.domain.Account;
import io.project.app.addressbook.domain.Address;
import io.project.app.addressbook.dto.AccountDTO;
import io.project.app.addressbook.dto.AddressDTO;
import io.project.app.addressbook.services.AccountService;
import io.project.app.addressbook.services.AddressService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/addresses")
@Slf4j
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping(path = "/find/All", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll() {
        List<Address> savedAddresses = addressService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(savedAddresses);
    }

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAddress(@RequestBody AddressDTO addressDTO) {
        Optional<Address> createdAddress = addressService.createAddress(addressDTO);
        return ResponseEntity.status(HttpStatus.OK).body(createdAddress);
    }

    @PutMapping(path = "/update/email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateEmail(@RequestParam Long contactId, @RequestParam String email) {
        Optional<Address> updatedEmailAddress = addressService.updateEmail(contactId, email);
        return ResponseEntity.status(HttpStatus.OK).body(updatedEmailAddress);
    }

    @PutMapping(path = "/update/zoom", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateZoom(@RequestParam Long contactId, @RequestParam String zoomId) {
        Optional<Address> updatedZoomAddress = addressService.updateZoom(contactId, zoomId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedZoomAddress);
    }

    @DeleteMapping(path = "/delete/by/contactId", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@RequestParam Long contactId) {

        addressService.delete(contactId);
    }
}
