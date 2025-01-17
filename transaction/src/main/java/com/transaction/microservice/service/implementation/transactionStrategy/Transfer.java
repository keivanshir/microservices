package com.transaction.microservice.service.implementation.transactionStrategy;


import com.codes.common.entity.Transaction;
import com.codes.common.enums.TransactionType;

public class Transfer implements TransactionCreatorStrategy {
    @Override
    public Transaction createTransaction(String sourceAccountNumber, String destinationAccountNumber) {
        Transaction transaction = new Transaction();

        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setSourceAccountNumber(sourceAccountNumber);
        transaction.setDestinationAccountNumber(destinationAccountNumber);

        return transaction;
    }
}
