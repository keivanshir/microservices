package com.codes.common.dto;

import com.codes.common.entity.*;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public AccountDto mapToAccountDto(Account account){
        AccountDto accountDto = new AccountDto();

        accountDto.setId(account.getId());
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setRemaining(account.getRemaining());
        accountDto.setAccountStatus(account.getAccountStatus());
        accountDto.setCreatedDate(account.getCreatedDate());
        accountDto.setModifiedDate(account.getModifiedDate());
        if (account.getCustomer() != null){
            accountDto.setCustomerDto(mapToCustomerDto(account.getCustomer()));
        }

        return accountDto;
    }

    public Account mapToAccount(AccountDto accountDto){
        Account account = new Account();

        account.setId(accountDto.getId());
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setRemaining(accountDto.getRemaining());
        account.setAccountStatus(accountDto.getAccountStatus());
        account.setCreatedDate(accountDto.getCreatedDate());
        account.setModifiedDate(accountDto.getModifiedDate());
        if (accountDto.getCustomerDto() != null){
            account.setCustomer(mapToCustomer(accountDto.getCustomerDto()));
        }

        return account;
    }

    public AccountHistoryDto mapToAccountHistoryDto(AccountHistory accountHistory){
        AccountHistoryDto accountHistoryDto = new AccountHistoryDto();

        accountHistoryDto.setId(accountHistory.getId());
        accountHistoryDto.setOldAccountStatus(accountHistory.getOldAccountStatus());
        accountHistoryDto.setOldCash(accountHistory.getOldCash());
        accountHistoryDto.setNewAccountStatus(accountHistory.getNewAccountStatus());
        accountHistoryDto.setNewCash(accountHistory.getNewCash());
        if (accountHistory.getCreatedBy() != null){
            accountHistoryDto.setCreatedBy(mapToUserDto(accountHistory.getCreatedBy()));
        }
        accountHistoryDto.setCreatedDate(accountHistory.getCreatedDate());
        if (accountHistory.getAccount() != null){
            accountHistoryDto.setAccount(mapToAccountDto(accountHistory.getAccount()));
        }

        return accountHistoryDto;
    }

    public AccountHistory mapToAccountHistory(AccountHistoryDto accountHistoryDto){
        AccountHistory accountHistory = new AccountHistory();

        accountHistory.setId(accountHistoryDto.getId());
        accountHistory.setOldAccountStatus(accountHistoryDto.getOldAccountStatus());
        accountHistory.setOldCash(accountHistoryDto.getOldCash());
        accountHistory.setNewAccountStatus(accountHistoryDto.getNewAccountStatus());
        accountHistory.setNewCash(accountHistoryDto.getNewCash());
        if (accountHistoryDto.getCreatedBy() != null){
            accountHistory.setCreatedBy(mapToUser(accountHistoryDto.getCreatedBy()));
        }
        accountHistory.setCreatedDate(accountHistory.getCreatedDate());
        if (accountHistoryDto.getAccount() != null){
            accountHistory.setAccount(mapToAccount(accountHistoryDto.getAccount()));
        }

        return accountHistory;
    }


    public UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());

        return userDto;
    }


    public User mapToUser(UserDto userDto) {
        User user = new User();

        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());

        return user;
    }

    public CustomerDto mapToCustomerDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();

        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setIdentificationNumber(customer.getIdentificationNumber());
        customerDto.setCustomerType(customer.getCustomerType());
        customerDto.setAddress(customer.getAddress());
        customerDto.setPostalCode(customer.getPostalCode());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setBirthOrEstablishmentDate(customer.getBirthOrEstablishmentDate());

        return customerDto;
    }

    public Customer mapToCustomer(CustomerDto customerDto){
        Customer customer = new Customer();

        customer.setId(customerDto.getId());
        customer.setName(customerDto.getName());
        customer.setIdentificationNumber(customerDto.getIdentificationNumber());
        customer.setCustomerType(customerDto.getCustomerType());
        customer.setAddress(customerDto.getAddress());
        customer.setPostalCode(customerDto.getPostalCode());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setBirthOrEstablishmentDate(customerDto.getBirthOrEstablishmentDate());

        return customer;
    }

    public TransactionDto mapToTransactionDto(Transaction transaction){
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setTransactionStatus(transaction.getTransactionStatus());
        transactionDto.setTransactionType(transaction.getTransactionType());
        transactionDto.setTrackingCode(transaction.getTrackingCode());
        transactionDto.setSourceAccountNumber(transaction.getSourceAccountNumber());
        transactionDto.setDestinationAccountNumber(transaction.getDestinationAccountNumber());

        return transactionDto;
    }

    public Transaction mapToTransaction(TransactionDto transactionDto){
        Transaction transaction = new Transaction();
        transaction.setId(transactionDto.getId());
        transaction.setTransactionStatus(transactionDto.getTransactionStatus());
        transaction.setTransactionType(transactionDto.getTransactionType());
        transaction.setTrackingCode(transactionDto.getTrackingCode());
        transaction.setSourceAccountNumber(transactionDto.getSourceAccountNumber());
        transaction.setDestinationAccountNumber(transactionDto.getDestinationAccountNumber());

        return transaction;
    }

}
