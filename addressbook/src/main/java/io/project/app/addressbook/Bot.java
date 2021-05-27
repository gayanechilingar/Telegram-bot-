package io.project.app.addressbook;

import io.project.app.addressbook.domain.Account;
import io.project.app.addressbook.domain.Address;
import io.project.app.addressbook.dto.AccountDTO;
import io.project.app.addressbook.dto.AddressDTO;
import io.project.app.addressbook.services.AccountService;
import io.project.app.addressbook.services.AddressService;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {

    @Autowired
    AccountService accountService;

    @Autowired
    AddressService addressService;

    private static final Logger log = LoggerFactory.getLogger(Bot.class);

    public String getBotToken() {
        return "YOUR TOKEN";
    }

    @Override
    public String getBotUsername() {
        return "bazhak_bot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getMessage().getText());
        long chat_id = update.getMessage().getChatId();

        AccountDTO accountDTO = new AccountDTO();
        AddressDTO addressDTO = new AddressDTO();
        if (update.hasMessage()) {
            Message message = update.getMessage();

            String messageText = update.getMessage().getText();

            if (messageText.startsWith("/help")) {

                sendMsg("Here is the functionality of this bot \n"
                        //                        + "/register firstname lastname: creates new account \n"
                        //                        + "/login firstname lastname: logins to existing account \n"
                        + "/contact create firstname lastname phone zoom: creates new contact \n"
                        + "/contact update email contactId email: updates email of that contact \n"
                        + "/contact update zoom  contactId zoom: updates zoom of that contact \n"
                        + "/contact delete contactId: deletes that contact \n"
                        + "/contact all: finds all contacts \n"
                        + "/contact search firstname lastname: searches by name of contact \n", chat_id);

            }

            if (messageText.startsWith("/contact create")) {
                String[] separated = messageText.split(" ");
                addressDTO.setChatId(chat_id);

                addressDTO.setContactName(separated[2] + " " + separated[3]);
                addressDTO.setPhoneNumber(separated[4]);
                addressDTO.setZoomId(separated[5]);
                Optional<Address> savedAddress = addressService.createAddress(addressDTO);
                if (!savedAddress.isPresent()) {
                    sendMsg("Contact with phone number is " + addressDTO.getPhoneNumber() + ", already exists", chat_id);
                }
                sendMsg("You've created contact with phone number " + addressDTO.getPhoneNumber(), chat_id);
            }

            if (messageText.startsWith("/contact all")) {

                List<Address> addressList = addressService.findAll();
                if (addressList.isEmpty()) {
                    sendMsg("There aren't any contacts yet", chat_id);
                } else {
                    for (Address addressItem : addressList) {
                        sendMsg("Contact id: " + addressItem.getContactId() + "\n"
                                + "Contact name: " + addressItem.getContactName() + "\n"
                                + "Contact phone: " + addressItem.getPhoneNumber() + "\n"
                                + "Contact email: " + addressItem.getEmail() + "\n"
                                + "Contact zoom id: " + addressItem.getZoomId() + "\n"
                                + "Contact register date: " + addressItem.getRecordDate() + "\n", chat_id);
                    }
                }

            }

            if (messageText.startsWith("/contact update email")) {

                String[] separated = messageText.split(" ");

                addressDTO.setContactId(Long.parseLong(separated[3]));
                addressDTO.setEmail(separated[4]);

                Optional<Address> savedAddress = addressService.
                        updateEmail(addressDTO.getContactId(), addressDTO.getEmail());
                if (!savedAddress.isPresent()) {
                    sendMsg("Contact with contact id " + addressDTO.getContactId() + " not found", chat_id);
                } else {
                    sendMsg("You've updated contact with contact id " + addressDTO.getContactId(), chat_id);
                }

            }
            if (messageText.startsWith("/contact update zoom")) {

                String[] separated = messageText.split(" ");

                addressDTO.setContactId(Long.parseLong(separated[3]));
                addressDTO.setZoomId(separated[4]);

                Optional<Address> savedAddress = addressService.
                        updateZoom(addressDTO.getContactId(), addressDTO.getZoomId());
                if (!savedAddress.isPresent()) {
                    sendMsg("Contact with contact id " + addressDTO.getContactId() + " not found", chat_id);
                } else {
                    sendMsg("You've updated contact with contact id " + addressDTO.getContactId(), chat_id);
                }

            }
            if (messageText.startsWith("/contact delete")) {
                String[] separated = messageText.split(" ");

                addressDTO.setContactId(Long.parseLong(separated[2]));

                String deletion = addressService.delete(addressDTO.getContactId());
                if (deletion.equalsIgnoreCase("Contact not found")) {
                    sendMsg("Contact with contactId " + addressDTO.getContactId() + " not found", chat_id);
                } else {
                    sendMsg("You've deleted contact with contactId " + addressDTO.getContactId(), chat_id);
                }

            }
            if (messageText.startsWith("/contact search")) {
                String[] separated = messageText.split(" ");
//
                addressDTO.setContactName(separated[2]);
                List<Address> search = addressService.findByName(addressDTO.getContactName());

                for (Address addressItem : search) {
                    sendMsg("Contact id: " + addressItem.getContactId() + "\n"
                            + "Contact name: " + addressItem.getContactName() + "\n"
                            + "Contact phone: " + addressItem.getPhoneNumber() + "\n"
                            + "Contact email: " + addressItem.getEmail() + "\n"
                            + "Contact zoom id: " + addressItem.getZoomId() + "\n"
                            + "Contact register date: " + addressItem.getRecordDate() + "\n", chat_id);
                }
            }
        }

    }

    @SneakyThrows
    public synchronized void sendMsg(String s, long chat_id) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chat_id + "");
        sendMessage.setText(s);
        sendMessage.setReplyMarkup(null);
        execute(sendMessage);
    }

}
