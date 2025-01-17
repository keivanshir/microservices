package com.transaction.microservice.service.implementation.accountStrategy;


import com.codes.common.entity.Account;
import com.codes.common.exception.AccountDisabledException;
import com.codes.common.exception.NotFoundException;
import com.transaction.microservice.repository.AccountHistoryRepository;
import com.transaction.microservice.repository.AccountRepository;
import com.transaction.microservice.utils.TransactionUtils;
import org.springframework.stereotype.Component;

@Component
public class Increase implements OperationStrategy {

    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;
    private final TransactionUtils transactionUtils;

    public Increase(AccountRepository accountRepository, AccountHistoryRepository accountHistoryRepository, TransactionUtils transactionUtils) {
        this.accountRepository = accountRepository;
        this.accountHistoryRepository = accountHistoryRepository;
        this.transactionUtils = transactionUtils;
    }


    @Override
    public void operate(String sourceAccountNumber, String destinationAccountNumber, Long value) {
        Account destinationAccount = accountRepository.findAccountByAccountNumber(destinationAccountNumber)
                .orElseThrow(() -> new NotFoundException("شماره حساب موجود نیست!"));

        if (transactionUtils.isEnabled(destinationAccount.getAccountStatus())){
            Long oldRemaining = destinationAccount.getRemaining();
            destinationAccount.setRemaining(destinationAccount.getRemaining() + value);
            Transmit.saveToAccount(accountRepository, accountHistoryRepository, destinationAccount, oldRemaining);
        } else {
            throw new AccountDisabledException("شماره حساب شما مسدود است!");
        }
    }
}
