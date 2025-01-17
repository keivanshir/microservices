package com.codes.common.dto;

import com.codes.common.enums.TransactionStatus;
import com.codes.common.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto{
    private Long id;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private String trackingCode;
    private Long value;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
}
