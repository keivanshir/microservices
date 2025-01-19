package com.transaction.microservice.service;



import com.codes.common.dto.Response;
import com.codes.common.dto.TransactionDto;
import com.codes.common.enums.TransactionStatus;
import com.codes.common.enums.TransactionType;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {

    Response<TransactionDto> createTransaction(TransactionDto transactionDto);
    Response<TransactionDto> getTransactionStatusByTrackingCode(Long trackingCode);

    Response<List<TransactionDto>> findAllTransactionsByFilter(int page,
                                                     int size,
                                                     TransactionType type,
                                                     String accountNumber,
                                                     Long fromValue,
                                                     Long toValue,
                                                     LocalDate fromDate,
                                                     LocalDate toDate);

}
