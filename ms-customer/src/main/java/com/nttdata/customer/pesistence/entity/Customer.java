package com.nttdata.customer.pesistence.entity;

import com.nttdata.customer.openapi.model.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private String id;
    private String name;
    private String lastName;
    private CustomerDTO.DocumentTypeEnum documentType;
    private String documentNumber;
    private CustomerDTO.ClientTypeEnum clientType;
}
