package com.codes.common.dto;

import com.codes.common.enums.CustomerType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto{
    private Long id;
    private String name;
    private String identificationNumber;
    private LocalDate birthOrEstablishmentDate;
    private CustomerType customerType;
    private String phoneNumber;
    private String address;
    private String postalCode;
}
