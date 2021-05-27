package io.project.app.addressbook.dto;

import io.project.app.addressbook.domain.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
  
    private Long chatId;
    
    private String firstname;
    
    private String lastname;
    
    private LocalDateTime registerDate = LocalDateTime.now();
    
}
