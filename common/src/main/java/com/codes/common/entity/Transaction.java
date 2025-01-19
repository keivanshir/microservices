package com.codes.common.entity;

import com.codes.common.enums.TransactionStatus;
import com.codes.common.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TRANSACTION_TYPE")
    private TransactionType transactionType;

    @Column(name = "TRANSACTION_STATUS")
    private TransactionStatus transactionStatus;

    @Column(name = "STATUS_REASON")
    private String statusReason;

    @Column(name = "TRACKING_CODE")
    private String trackingCode;

    private Long value;

    private String sourceAccountNumber;
    private String destinationAccountNumber;

    @CreatedDate
    private LocalDateTime createdAt;


}
