package io.project.app.addressbook.repositories;

import io.project.app.addressbook.domain.Account;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;


@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    
    Optional<Account>findByChatId(Long chatId);
 
}
