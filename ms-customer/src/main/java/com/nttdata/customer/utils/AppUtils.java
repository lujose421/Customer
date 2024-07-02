package com.nttdata.customer.utils;

import com.banking.openapi.model.CustomerDTO;
import com.nttdata.customer.pesistence.entity.CustomerEntity;
import com.nttdata.customer.pesistence.entity.enums.ClientType;
import com.nttdata.customer.pesistence.entity.enums.DocumentType;

public class AppUtils {

    public static CustomerDTO entityToDto(CustomerEntity customer) {
        CustomerDTO newCustomerDTO = new CustomerDTO();
        newCustomerDTO.setId(customer.getId());
        newCustomerDTO.setName(customer.getName());
        newCustomerDTO.setLastName(customer.getLastName());
        newCustomerDTO.setReason(customer.getReason());
        newCustomerDTO.setDocumentType(CustomerDTO.DocumentTypeEnum.fromValue(customer.getDocumentType().getValue()));
        newCustomerDTO.setDocumentNumber(customer.getDocumentNumber());
        newCustomerDTO.setClientType(CustomerDTO.ClientTypeEnum.fromValue(customer.getClientType().getValue()));
        return newCustomerDTO;
    }

    public static CustomerEntity dtoToEntity(CustomerDTO customerDTO) {
        CustomerEntity newCustomer = new CustomerEntity();
        newCustomer.setId(customerDTO.getId());
        newCustomer.setName(customerDTO.getName());
        newCustomer.setLastName(customerDTO.getLastName());
        newCustomer.setReason(customerDTO.getReason());
        newCustomer.setDocumentType(DocumentType.valueOf(customerDTO.getDocumentType().getValue()));
        newCustomer.setDocumentNumber(customerDTO.getDocumentNumber());
        newCustomer.setClientType(ClientType.valueOf(customerDTO.getClientType().getValue()));
        return newCustomer;
    }
}
