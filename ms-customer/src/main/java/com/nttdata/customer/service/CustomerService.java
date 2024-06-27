package com.nttdata.customer.service;

import com.nttdata.customer.openapi.model.CustomerDTO;
import com.nttdata.customer.openapi.model.ResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<CustomerDTO> getAllCustomer();
    Mono<CustomerDTO> getCustomerById(String id);
    Mono<CustomerDTO> createCustomer(CustomerDTO customerDTO);
    Mono<CustomerDTO> updateCustomerById(String id, CustomerDTO customerDTO);
    Mono<ResponseDTO> deleteCustomerById(String id);
}
