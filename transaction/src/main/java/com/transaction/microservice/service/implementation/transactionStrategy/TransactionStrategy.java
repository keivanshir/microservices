package com.transaction.microservice.service.implementation.transactionStrategy;


import com.codes.common.enums.TransactionType;

public class TransactionStrategy {

    public static TransactionCreatorStrategy getTransaction(TransactionType type){
        return switch (type){
            case DEPOSIT -> new Deposit();
            case TRANSFER ->  new Transfer();
            case WITHDRAW ->  new Withdraw();
        };
    }
}
