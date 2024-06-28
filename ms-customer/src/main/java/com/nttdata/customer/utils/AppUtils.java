package com.nttdata.customer.utils;

import com.nttdata.customer.openapi.model.CustomerDTO;
import com.nttdata.customer.pesistence.entity.Customer;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static CustomerDTO entityToDto(Customer customer) {
        CustomerDTO newCustomerDTO = new CustomerDTO();
        newCustomerDTO.setId(customer.getId());
        newCustomerDTO.setName(customer.getName());
        newCustomerDTO.setLastName(customer.getLastName());
        newCustomerDTO.setRazon(customer.getRazon());
        newCustomerDTO.setDocumentType(customer.getDocumentType());
        newCustomerDTO.setDocumentNumber(customer.getDocumentNumber());
        newCustomerDTO.setClientType(customer.getClientType());
        return newCustomerDTO;
    }

    public static Customer dtoToEntity(CustomerDTO customerDTO) {
        Customer newCustomer = new Customer();
        newCustomer.setId(customerDTO.getId());
        newCustomer.setName(customerDTO.getName());
        newCustomer.setLastName(customerDTO.getLastName());
        newCustomer.setRazon(customerDTO.getRazon());
        newCustomer.setDocumentType(customerDTO.getDocumentType());
        newCustomer.setDocumentNumber(customerDTO.getDocumentNumber());
        newCustomer.setClientType(customerDTO.getClientType());
        return newCustomer;
    }
}
