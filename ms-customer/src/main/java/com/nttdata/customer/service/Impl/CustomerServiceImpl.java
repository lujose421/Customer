package com.nttdata.customer.service.Impl;

import com.banking.openapi.model.CustomerRequest;
import com.banking.openapi.model.CustomerResponse;
import com.banking.openapi.model.ResponseDTO;
import com.nttdata.customer.exception.types.NotFoundException;
import com.nttdata.customer.pesistence.entity.CustomerEntity;
import com.nttdata.customer.pesistence.repository.CustomerRepository;
import com.nttdata.customer.service.CustomerService;
import com.nttdata.customer.utils.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMER_NOT_FOUND_MESSAGE = "CustomerEntity with ID %s does not exist";
    private static final String CUSTOMER_ALREADY_EXISTS = "CustomerEntity with Document %s already exist";

    @Autowired
    private CustomerRepository customerRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public Mono<ResponseEntity<Flux<CustomerResponse>>> getCustomer() {
        logger.info("service customerRepository - ini");

        Flux<CustomerResponse> customerDTOFlux = this.customerRepository.findAll()
                .doOnError(error -> logger.error("Error getAllCustomer: ", error))
                .map(AppUtils::entityToDto)
                //.doOnNext(customerDTO -> logger.info("customer all service: {}", customerDTO))
                .doOnTerminate(() -> logger.info("service customerRepository - end"));

        return Mono.just(ResponseEntity.ok(customerDTOFlux));
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> getCustomerById(String id) {
        logger.info("service getCustomerById - ini");

        return this.customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(CUSTOMER_NOT_FOUND_MESSAGE, id)))
                .doOnNext(customerEntity -> logger.info("customerEntity by id service: {}", customerEntity))
                .flatMap(customerEntity -> Mono.just(ResponseEntity.ok(AppUtils.entityToDto(customerEntity))))
                .doOnTerminate(() -> logger.info("service getCustomerById - end"));
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> createCustomer(CustomerRequest customerRequest) {
        logger.info("service createCustomer - ini");
//        Mono<CustomerDTO> dto = this.customerRepository.findByDocumentNumber(customerDTO.getDocumentNumber())
//                .map(AppUtils::entityToDto)
//                .switchIfEmpty(Mono.error(new NotFoundException(CUSTOMER_ALREADY_EXISTS, customerDTO.getDocumentNumber())));
//        return Mono.just(ResponseEntity.ok(new CustomerDTO()));

        return Mono.just(customerRequest)
                //.doOnError(Mono.error(new IllegalArgumentException())
                .doOnError(error -> logger.error("Error createCustomer: ", error))
                .map(AppUtils::dtoToEntity)
                .doOnNext(customerBefore -> logger.info("customer before create: {}", customerBefore))
                .flatMap(this.customerRepository::insert)
                .map(AppUtils::entityToDto)
                .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto))
                .doOnNext(customerAfter -> logger.info("customer after create: {}", customerAfter))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build())
                .doOnTerminate(() -> logger.info("service createCustomer - end"));
    }

    @Override
    public Mono<CustomerResponse> updateCustomerById(String id, CustomerRequest customerRequest) {
        logger.info("service updateCustomerById - ini");
        // Using functional programing
        return this.customerRepository.findById(id)
                .doOnError(error -> logger.error("Error updateCustomerById: ", error))
                .flatMap(existCustomerEntity -> {
                    CustomerEntity updatedCustomer = AppUtils.dtoToEntity(customerRequest);
                    updatedCustomer.setId(existCustomerEntity.getId());
                    logger.info("customer by id: {}, body customer: {}", existCustomerEntity.getId(), updatedCustomer);
                    return this.customerRepository.save(updatedCustomer);
                })
                .map(AppUtils::entityToDto)
                .doOnTerminate(() -> logger.info("service updateCustomerById - end"));
    }

    @Override
    public Mono<ResponseDTO> deleteCustomerById(String id) {
        logger.info("service deleteCustomerById - ini");
        return this.customerRepository.deleteById(id)
                .doOnError(error -> logger.error("Error deleteCustomerById: ", error))
                .then(Mono.just(new ResponseDTO().message("CustomerEntity deleted successfully")))
                .doOnTerminate(() -> logger.info("service deleteCustomerById - end"));
    }
}
