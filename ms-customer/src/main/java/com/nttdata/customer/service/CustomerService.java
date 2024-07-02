package com.nttdata.customer.service;

import com.banking.openapi.model.CustomerDTO;
import com.banking.openapi.model.ResponseDTO;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<ResponseEntity<Flux<CustomerDTO>>> getAllCustomer();
    Mono<ResponseEntity<CustomerDTO>> getCustomerById(String id);
    Mono<ResponseEntity<CustomerDTO>> createCustomer(CustomerDTO customerDTO);
    Mono<CustomerDTO> updateCustomerById(String id, CustomerDTO customerDTO);
    Mono<ResponseDTO> deleteCustomerById(String id);
}
