package io.project.app.addressbook.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Account")
public class Account {
  
    @Id
    private String id;
    
    private Long chatId;
    
    private String firstname;
    
    private String lastname;
    
    private LocalDateTime registerDate = LocalDateTime.now();
    
}
