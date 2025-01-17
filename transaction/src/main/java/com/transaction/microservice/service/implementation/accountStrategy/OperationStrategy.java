package com.transaction.microservice.service.implementation.accountStrategy;

public interface OperationStrategy {

    void operate(String sourceAccountNumber, String destinationAccountNumber, Long value);
}
