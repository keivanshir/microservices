package com.codes.common.dto;

import com.codes.common.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHistoryDto{
    private Long id;
    private Long oldCash;
    private Long newCash;
    private AccountStatus oldAccountStatus;
    private AccountStatus newAccountStatus;
    private UserDto createdBy;
    private LocalDateTime createdDate;
    private AccountDto account;
}
