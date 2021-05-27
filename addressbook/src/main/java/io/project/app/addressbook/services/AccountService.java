package io.project.app.addressbook.services;

import io.project.app.addressbook.domain.Account;
import io.project.app.addressbook.dto.AccountDTO;
import io.project.app.addressbook.repositories.AccountRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> createAccount(AccountDTO accountDTO) {
        log.info("AccountService: creating account");
        Account convertedData = convertEntityToDto(accountDTO);

        Optional<Account> findAccountByChatId = accountRepository.findByChatId(convertedData.getChatId());

        if (findAccountByChatId.isPresent()) {
            log.error("user with that chat id already exists, you should login instead");
            return Optional.empty();
        }

        final Account savedAccount = accountRepository.save(convertedData);

        return Optional.ofNullable(savedAccount);
    }

    public Optional<Account> login(AccountDTO accountDTO) {
        log.info("login user" + accountDTO.getChatId());
        Account convertedData = convertEntityToDto(accountDTO);
        Optional<Account> existingUser = accountRepository.findByChatId(convertedData.getChatId());
        if (!existingUser.isPresent()) {
            log.error("Didn't found user, register instead");
            return Optional.empty();
        }

        if (existingUser.isPresent()) {
            log.info("User present");
            return Optional.ofNullable(existingUser.get());
        }

        return Optional.empty();
    }

    public static AccountDTO convertEntityToDto(Account account) {
        AccountDTO accountDTOResponse = new AccountDTO();
        try {
            BeanUtils.copyProperties(account, accountDTOResponse);
        } catch (BeansException e) {
            throw new RuntimeException("Error creating AccountDTO response from Account", e);
        }
        return accountDTOResponse;
    }

    public static Account convertEntityToDto(AccountDTO accountDTO) {
        Account accountResponse = new Account();
        try {
            BeanUtils.copyProperties(accountDTO, accountResponse);
        } catch (BeansException e) {
            throw new RuntimeException("Error creating Account from AccountDTO", e);
        }
        return accountResponse;
    }
}
