package com.nttdata.customer.controller;

import com.banking.openapi.api.CustomerApi;
import com.banking.openapi.model.CustomerDTO;
import com.banking.openapi.model.ResponseDTO;
import com.nttdata.customer.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController

public class CustomerController implements CustomerApi {

    @Autowired
    private CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Override
    public Mono<ResponseEntity<Flux<CustomerDTO>>> getCustomer(ServerWebExchange exchange) {
        return this.customerService.getAllCustomer();
    }

    @Override
    public Mono<ResponseEntity<CustomerDTO>> getCustomerById(
            @PathVariable("id") String id, ServerWebExchange exchange) {
        return this.customerService.getCustomerById(id);
    }

    @Override
    public Mono<ResponseEntity<CustomerDTO>> createCustomer(
            @RequestBody Mono<CustomerDTO> customerDTO, ServerWebExchange exchange) {
        return customerDTO.flatMap(this.customerService::createCustomer);
    }

    @Override
    public Mono<ResponseEntity<CustomerDTO>> updateCustomerById(
            @PathVariable("id") String id,
            @RequestBody Mono<CustomerDTO> customerDTO, ServerWebExchange exchange) {
        logger.info("endpoint updateCustomerById - ini");
        return customerDTO
                .flatMap(dto -> this.customerService.updateCustomerById(id, dto))
                .doOnNext(customer -> logger.info("New customer: {}", customer))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build())
                .doOnTerminate(() -> logger.info("endpoint updateCustomerById - end"));
    }

    @Override
    public Mono<ResponseEntity<ResponseDTO>> deleteCustomerById(
            @PathVariable("id") String id, ServerWebExchange exchange) {
        logger.info("endpoint deleteCustomerById - ini");
        return this.customerService.deleteCustomerById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build())
                .doOnTerminate(() -> logger.info("endpoint deleteCustomerById - end"));
    }
}
