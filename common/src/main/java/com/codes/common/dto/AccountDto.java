package com.codes.common.dto;


import com.codes.common.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto{
    private Long id;
    private String accountNumber;
    private Long remaining;
    private AccountStatus accountStatus;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private CustomerDto customerDto;
}
