package com.transaction.microservice.service.implementation.transactionStrategy;


import com.codes.common.entity.Transaction;

public interface TransactionCreatorStrategy {
    Transaction createTransaction(String sourceAccountNumber, String destinationAccountNumber);
}
