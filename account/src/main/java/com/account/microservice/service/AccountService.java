package com.account.microservice.service;


import com.codes.common.dto.AccountDto;
import com.codes.common.dto.CustomerDto;
import com.codes.common.dto.Response;

public interface AccountService {
    Response<AccountDto> createAccount(CustomerDto customerDto);
    Response<AccountDto> updateAccount(AccountDto accountDto);
    Response<String> getAccountNumberByIdentificationNumber(String identificationNumber);
    Response<CustomerDto> getCustomerByAccountNumber(String accountNumber);
    Response<String> getRemainingCashByAccountNumber(String accountNumber);
    Response<String> deleteAccount(String accountNumber);
}
