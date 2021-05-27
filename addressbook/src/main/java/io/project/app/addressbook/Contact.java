package io.project.app.addressbook;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@SpringBootApplication
@EnableMongoRepositories("io.project.app.addressbook.repositories")
@ComponentScan(basePackages = {"io.project.app.addressbook"})
@EntityScan("io.project.app.addressbook.domain")
@EnableAsync
public class Contact {

    public static void main(String[] args) {
        
        SpringApplication.run(Contact.class, args);
      
//       
//            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
//        try {
//            telegramBotsApi.registerBot(new Bot());
//          
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
    }

}
