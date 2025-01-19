package com.transaction.microservice.service.implementation.accountStrategy;



import com.codes.common.entity.Account;
import com.codes.common.entity.AccountHistory;
import com.codes.common.exception.AccountDisabledException;
import com.codes.common.exception.AccountsEqualException;
import com.codes.common.exception.InsufficientRemainingException;
import com.codes.common.exception.NotFoundException;
import com.transaction.microservice.repository.AccountHistoryRepository;
import com.transaction.microservice.repository.AccountRepository;
import com.transaction.microservice.utils.TransactionUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Transmit implements OperationStrategy {

    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;
    private final TransactionUtils transactionUtils;


    public Transmit(AccountRepository accountRepository, AccountHistoryRepository accountHistoryRepository, TransactionUtils transactionUtils) {
        this.accountRepository = accountRepository;
        this.accountHistoryRepository = accountHistoryRepository;
        this.transactionUtils = transactionUtils;
    }


    @Override
    @Transactional
    public void operate(String sourceAccountNumber, String destinationAccountNumber, Long value) {
        Account sourceAccount = accountRepository.findAccountByAccountNumber(sourceAccountNumber)
                .orElseThrow(() -> new NotFoundException("حساب بانکی مبدأ پیدا نشد!"));

        Account destinationAccount = accountRepository.findAccountByAccountNumber(destinationAccountNumber)
                .orElseThrow(() -> new NotFoundException("حساب بانکی مقصد پیدا نشد!"));

        if (transactionUtils.accountsNotEqual(sourceAccountNumber, destinationAccountNumber)){
            if (transactionUtils.isEnabled(sourceAccount.getAccountStatus())){
                if (transactionUtils.isEnabled(destinationAccount.getAccountStatus())){
                    if (transactionUtils.isRemainingSufficient(sourceAccount.getRemaining(), value)){
                        decreaseFromSource(accountRepository, accountHistoryRepository, sourceAccount, value);
                        increaseToDestination(accountRepository, accountHistoryRepository, destinationAccount, value);
                    } else {
                        throw new InsufficientRemainingException("موجودی کمتر از مقدار انتقال است!");
                    }
                } else {
                    throw new AccountDisabledException("شماره حساب مقصد مسدود است!");
                }
            } else {
                throw new AccountDisabledException("شماره حساب مبدأ مسدود است!");
            }
        } else {
            throw new AccountsEqualException("حساب بانکی مبدأ و مقصد نباید برابر باشد!");
        }
    }

    public static void decreaseFromSource(AccountRepository accountRepository, AccountHistoryRepository accountHistoryRepository, Account sourceAccount, Long value){
        Long oldRemaining = sourceAccount.getRemaining();
        sourceAccount.setRemaining(sourceAccount.getRemaining() - value);
        saveToAccount(accountRepository, accountHistoryRepository, sourceAccount, oldRemaining);

    }

    static void saveToAccount(AccountRepository accountRepository, AccountHistoryRepository accountHistoryRepository, Account sourceAccount, Long oldRemaining) {
        accountRepository.save(sourceAccount);

        AccountHistory accountHistory = new AccountHistory();
        accountHistory.setAccount(sourceAccount);
        accountHistory.setOldCash(oldRemaining);
        accountHistory.setNewCash(sourceAccount.getRemaining());
        accountHistory.setOldAccountStatus(sourceAccount.getAccountStatus());
        accountHistory.setNewAccountStatus(sourceAccount.getAccountStatus());
        accountHistoryRepository.save(accountHistory);
    }

    public static void increaseToDestination(AccountRepository accountRepository, AccountHistoryRepository accountHistoryRepository, Account destinationAccount, Long value){
        Long oldRemaining = destinationAccount.getRemaining();
        destinationAccount.setRemaining(destinationAccount.getRemaining() + value);
        saveToAccount(accountRepository, accountHistoryRepository, destinationAccount, oldRemaining);
    }
}
