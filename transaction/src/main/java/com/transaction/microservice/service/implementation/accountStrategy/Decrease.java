package com.transaction.microservice.service.implementation.accountStrategy;


import com.codes.common.entity.Account;
import com.codes.common.exception.AccountDisabledException;
import com.codes.common.exception.InsufficientRemainingException;
import com.codes.common.exception.NotFoundException;
import com.transaction.microservice.repository.AccountHistoryRepository;
import com.transaction.microservice.repository.AccountRepository;
import com.transaction.microservice.utils.TransactionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Decrease implements OperationStrategy {

    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;
    private final TransactionUtils transactionUtils;

    public Decrease(AccountRepository accountRepository, AccountHistoryRepository accountHistoryRepository, TransactionUtils transactionUtils) {
        this.accountRepository = accountRepository;
        this.accountHistoryRepository = accountHistoryRepository;
        this.transactionUtils = transactionUtils;
    }

    @Override
    @Transactional
    public void operate(String sourceAccountNumber, String destinationAccountNumber, Long value) {
        Account sourceAccount = accountRepository.findAccountByAccountNumber(sourceAccountNumber)
                .orElseThrow(() -> new NotFoundException("شماره حساب موجود نیست!"));

        if (transactionUtils.isEnabled(sourceAccount.getAccountStatus())){
            if (transactionUtils.isRemainingSufficient(sourceAccount.getRemaining(), value)){
                Long oldRemaining = sourceAccount.getRemaining();
                sourceAccount.setRemaining(sourceAccount.getRemaining() - value);
                Transmit.saveToAccount(accountRepository, accountHistoryRepository, sourceAccount, oldRemaining);
            } else {
                throw new InsufficientRemainingException("موجودی کمتر از مقدار انتقال است!");
            }
        } else {
            throw new AccountDisabledException("شماره حساب شما مسدود است!");
        }
    }
}
