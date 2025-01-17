package com.codes.common.entity;

import com.codes.common.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ACCOUNT_NUMBER", unique = true) // must be 14 digits
    private String accountNumber; // unmodifiable

    private Long remaining; // مانده

    @Column(name = "ACCOUNT_STATUS")
    private AccountStatus accountStatus = AccountStatus.ENABLED;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate; // unmodifiable

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

}
