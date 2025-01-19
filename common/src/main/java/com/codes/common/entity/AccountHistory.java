package com.codes.common.entity;

import com.codes.common.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "OLD_CASH")
    private Long oldCash;

    @Column(name = "NEW_CASH")
    private Long newCash;

    @Column(name = "OLD_ACCOUNT_STATUS")
    private AccountStatus oldAccountStatus;

    @Column(name = "NEW_ACCOUNT_STATUS")
    private AccountStatus newAccountStatus;

    @CreatedBy
    @ManyToOne
    private User createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @Version
    private Long version = 0L;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;


}
