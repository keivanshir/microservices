package com.account.microservice.service;


import com.codes.common.dto.CustomerDto;
import com.codes.common.dto.Response;

public interface CustomerService {

    Response<CustomerDto> createCustomer(CustomerDto customerDto);
    Response<String> deleteCustomer(Long customerId);
}
