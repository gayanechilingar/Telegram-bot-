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
public class AddressDTO {

    private Long chatId;
    
    private Long contactId;

    private String contactName;

    private String email;

    private String phoneNumber;
    
    private String zoomId;

    private LocalDateTime recordDate = LocalDateTime.now();

}
