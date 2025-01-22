package com.codes.common.entity;

import com.codes.common.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
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
    @Pattern(regexp = "\\d{14}", message = "شماره حساب باید 14 رقمی باشد")
    private String accountNumber; // unmodifiable

    @PositiveOrZero(message = "مانده باید صفر یا بیشتر باشد")
    private Long remaining; // مانده

    @Column(name = "ACCOUNT_STATUS")
    private AccountStatus accountStatus = AccountStatus.ENABLED;

    @CreatedDate
    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    private LocalDateTime createdDate; // unmodifiable

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE", nullable = false)
    private LocalDateTime modifiedDate;

    @Version
    private Long version = 0L; // for optimistic locking mechanism

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

}
