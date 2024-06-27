package com.nttdata.customer.service.Impl;

import com.nttdata.customer.openapi.model.CustomerDTO;
import com.nttdata.customer.openapi.model.ResponseDTO;
import com.nttdata.customer.pesistence.entity.Customer;
import com.nttdata.customer.pesistence.repository.CustomerRepository;
import com.nttdata.customer.service.CustomerService;
import com.nttdata.customer.utils.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public Flux<CustomerDTO> getAllCustomer() {
        logger.info("service customerRepository - ini");
        return this.customerRepository.findAll()
                .doOnError(error -> logger.error("Error getAllCustomer: ", error))
                .map(AppUtils::entityToDto)
                .doOnNext(customerDTO -> logger.info("customer all service: {}", customerDTO))
                .doOnTerminate(() -> logger.info("service customerRepository - end"));
    }

    @Override
    public Mono<CustomerDTO> getCustomerById(String id) {
        logger.info("service getCustomerById - ini");
        return this.customerRepository.findById(id)
                .doOnError(error -> logger.error("Error getCustomerById: ", error))
                .doOnNext(customer -> logger.info("customer by id service: {}", customer))
                .map(AppUtils::entityToDto)
                .doOnTerminate(() -> logger.info("service getCustomerById - end"));
    }

    @Override
    public Mono<CustomerDTO> createCustomer(CustomerDTO customerDTO) {
        logger.info("service createCustomer - ini");
        // Using functional programing - reference method
        return Mono.just(customerDTO)
                .doOnError(error -> logger.error("Error createCustomer: ", error))
                .map(AppUtils::dtoToEntity)
                .doOnNext(customerBefore -> logger.info("customer before create: {}", customerBefore))
                .flatMap(this.customerRepository::insert)
                .map(AppUtils::entityToDto)
                .doOnNext(customerAfter -> logger.info("customer after create: {}", customerAfter))
                .doOnTerminate(() -> logger.info("service createCustomer - end"));
    }

    @Override
    public Mono<CustomerDTO> updateCustomerById(String id, CustomerDTO customerDTO) {
        logger.info("service updateCustomerById - ini");
        // Using functional programing
        return this.customerRepository.findById(id)
                .doOnError(error -> logger.error("Error updateCustomerById: ", error))
                .flatMap(existCustomer -> {
                    Customer updatedCustomer = AppUtils.dtoToEntity(customerDTO);
                    updatedCustomer.setId(existCustomer.getId());
                    logger.info("customer by id: {}, body customer: {}", existCustomer.getId(), updatedCustomer);
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
                .then(Mono.just(new ResponseDTO().message("Customer deleted successfully")))
                .doOnTerminate(() -> logger.info("service deleteCustomerById - end"));
    }
}
