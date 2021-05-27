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
@Document(collection = "Address")
public class Address {

    @Id
    private String id;

    private long chatId;

    private long contactId;

    private String contactName;

    private String email;

    private String phoneNumber;
    
    private String zoomId;

    private LocalDateTime recordDate = LocalDateTime.now();

}
