package com.account.microservice.service.implementation;


import com.account.microservice.repository.AccountRepository;
import com.account.microservice.repository.CustomerRepository;
import com.account.microservice.service.CustomerService;
import com.codes.common.dto.CustomerDto;
import com.codes.common.dto.Mapper;
import com.codes.common.dto.Response;
import com.codes.common.entity.Customer;
import com.codes.common.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImplementation implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final Mapper mapper;

    public CustomerServiceImplementation(CustomerRepository customerRepository, AccountRepository accountRepository, Mapper mapper) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    @Override
    public Response<CustomerDto> createCustomer(CustomerDto customerDto) {
        Customer customer = mapper.mapToCustomer(customerDto);
        return Response.<CustomerDto>builder()
                .data(mapper.mapToCustomerDto(customerRepository.save(customer)))
                .message("مشتری ثبت شد!")
                .statusCode(201)
                .build();
    }


    @Override
    public Response<String> deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("مشتری پیدا نشد!"));
        customerRepository.delete(customer);
        return Response.<String>builder()
                .message("مشتری حذف شد!")
                .statusCode(200)
                .build();
    }
}
