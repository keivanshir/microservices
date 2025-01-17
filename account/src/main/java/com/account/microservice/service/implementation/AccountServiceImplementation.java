package com.account.microservice.service.implementation;

import com.account.microservice.repository.AccountHistoryRepository;
import com.account.microservice.repository.AccountRepository;
import com.account.microservice.repository.CustomerRepository;
import com.account.microservice.service.AccountService;
import com.codes.common.dto.AccountDto;
import com.codes.common.dto.CustomerDto;
import com.codes.common.dto.Mapper;
import com.codes.common.dto.Response;
import com.codes.common.entity.Account;
import com.codes.common.entity.AccountHistory;
import com.codes.common.entity.Customer;
import com.codes.common.enums.AccountStatus;
import com.codes.common.exception.IDExistsException;
import com.codes.common.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImplementation implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;

    private final CustomerRepository customerRepository;
    private final Mapper mapper;

    public AccountServiceImplementation(AccountRepository accountRepository,
                                        AccountHistoryRepository accountHistoryRepository,
                                        CustomerRepository customerRepository,
                                        Mapper mapper) {
        this.accountRepository = accountRepository;
        this.accountHistoryRepository = accountHistoryRepository;
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }


    @Override
    public Response<AccountDto> createAccount(CustomerDto customerDto) {
        Account account = new Account();
        if (customerRepository.findCustomerByIdentificationNumber(customerDto.getIdentificationNumber()).isEmpty()){
            account.setCustomer(customerRepository.save(mapper.mapToCustomer(customerDto)));
        } else throw new IDExistsException("کد ملی یا شناسه ملی یا کد فراگیر اتباع غیر ایرانی تکراری است!");
        account.setAccountNumber(generate14DigitNumber());
        account.setAccountStatus(AccountStatus.ENABLED);
        account.setRemaining(0L);

        return Response.<AccountDto>builder()
                .message("حساب بانکی ذخیره شد!")
                .data(mapper.mapToAccountDto(account))
                .statusCode(201)
                .build();
    }

    public String generate14DigitNumber() {
        Random random = new Random();
        long number;

        do {
            number = 10_000_000_000_000L + (long) (random.nextDouble() * 90_000_000_000_000L);
        } while (!isUniqueInDatabase(number));

        return String.valueOf(number);
    }

    private boolean isUniqueInDatabase(long number) {
        Optional<Account> accountOptional = accountRepository.findAccountByAccountNumber(String.valueOf(number));
        return accountOptional.isEmpty();
    }

    @Transactional
    @Override
    public Response<AccountDto> updateAccount(AccountDto accountDto) {
        Account account = accountRepository.findAccountByAccountNumber(accountDto.getAccountNumber())
                .orElseThrow(() -> new NotFoundException("حساب بانکی پیدا نشد!"));
        AccountHistory accountHistory = new AccountHistory();

        accountHistory.setAccount(account);
        accountHistory.setOldAccountStatus(account.getAccountStatus());
        accountHistory.setOldCash(account.getRemaining());

        account.setRemaining(accountDto.getRemaining());
        account.setAccountStatus(accountDto.getAccountStatus());
        account.setModifiedDate(LocalDateTime.now());

        accountHistory.setNewAccountStatus(accountDto.getAccountStatus());
        accountHistory.setNewCash(accountDto.getRemaining());

        AccountDto updatedAccount = mapper.mapToAccountDto(accountRepository.save(account));
        accountHistoryRepository.save(accountHistory);

        return Response.<AccountDto>builder()
                .statusCode(201)
                .message("حساب بانکی ویرایش شد!")
                .data(updatedAccount)
                .build();
    }

    @Transactional
    @Override
    public Response<String> getAccountNumberByIdentificationNumber(String identificationNumber) {
        Customer customer = customerRepository.findCustomerByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new NotFoundException("مشتری پیدا نشد!"));

        Account account = accountRepository.findAccountByCustomer_Id(customer.getId())
                .orElseThrow(() -> new NotFoundException("حساب بانکی برای مشتری پیدا نشد!"));

        return Response.<String>builder()
                .message("شماره حساب: " + account.getAccountNumber())
                .statusCode(200)
                .build();
    }

    @Override
    public Response<AccountDto> getCustomerByAccountNumber(String accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new NotFoundException("شماره حساب مورد نظر پیدا نشد!"));
        return Response.<AccountDto>builder()
                .data(mapper.mapToAccountDto(account))
                .message("اطلاعات مشتری")
                .statusCode(200)
                .build();
    }


    @Override
    public Response<String> getRemainingCashByAccountNumber(String accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new NotFoundException("شماره حساب مورد نظر پیدا نشد!"));
        return Response.<String>builder()
                .statusCode(200)
                .data("مانده: " + account.getRemaining())
                .build();
    }

    @Override
    public Response<String> deleteAccount(String accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new NotFoundException("شماره حساب مورد نظر پیدا نشد!"));
        accountRepository.delete(account);
        return Response.<String>builder()
                .message("حساب بانکی با موفقیت حذف شد!")
                .statusCode(200)
                .build();
    }
}
