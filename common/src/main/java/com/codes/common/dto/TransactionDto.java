package com.codes.common.dto;

import com.codes.common.enums.TransactionStatus;
import com.codes.common.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto{
    private Long id;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private String trackingCode;
    private Long value;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private String statusReason;
    private LocalDateTime createdAt;
}
