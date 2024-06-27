package com.nttdata.customer.controller;

import com.nttdata.customer.openapi.api.CustomerApi;
import com.nttdata.customer.openapi.model.CustomerDTO;
import com.nttdata.customer.openapi.model.ResponseDTO;
import com.nttdata.customer.service.CustomerService;
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
        logger.info("endpoint getCustomer - ini");
        Flux<CustomerDTO> customerFlux = this.customerService.getAllCustomer()
                .doOnError(error -> logger.error("Error getCustomer: ", error));

        return customerFlux
                .collectList()
                .flatMap(customers -> {
                    logger.info("Number of customers : {}", customers.size());
                    if (customers.isEmpty()) {
                        logger.info("Not content customers");
                        return Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body(Flux.just()));
                    } else {
                        return Mono.just(ResponseEntity.ok(Flux.fromIterable(customers)
                                .doOnTerminate(() -> logger.info("endpoint getCustomer - end"))));
                    }
                });
    }

    @Override
    public Mono<ResponseEntity<CustomerDTO>> getCustomerById(
            @PathVariable("id") String id, ServerWebExchange exchange) {
        logger.info("endpoint getCustomerById - ini");
        return this.customerService.getCustomerById(id)
                .doOnError(error -> logger.error("Error getCustomerById: ", error))
                .map(ResponseEntity::ok)
                .doOnNext(customer -> logger.info("Get customer... {}", customer))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NO_CONTENT).build())
                .doOnTerminate(() -> logger.info("endpoint getCustomerById - end"));
    }

    @Override
    public Mono<ResponseEntity<CustomerDTO>> createCustomer(
            @RequestBody Mono<CustomerDTO> customerDTO, ServerWebExchange exchange) {
        logger.info("endpoint createCustomer - ini");
        return customerDTO
                .flatMap(this.customerService::createCustomer)
                .doOnError(error -> logger.error("Error createCustomer: ", error))
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build())
                .doOnTerminate(() -> logger.info("endpoint createCustomer - end"));
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
