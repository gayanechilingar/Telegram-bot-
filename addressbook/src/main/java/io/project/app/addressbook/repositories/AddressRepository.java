package io.project.app.addressbook.repositories;

import io.project.app.addressbook.domain.Account;
import io.project.app.addressbook.domain.Address;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;


@Repository
public interface AddressRepository extends MongoRepository<Address, String> {
    Optional<Address> findByPhoneNumber(String phoneNumber);
    Optional<Address> findByContactId(Long contactId);
    
    List<Address> findByChatId(long chatId);
    List<Address>findByContactNameStartsWith(String contactName);
    Optional<Address> deleteByContactId(Long contactId);


 
}
