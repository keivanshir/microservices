package com.codes.common.entity;

import com.codes.common.enums.CustomerType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "IDENTITFICATION_NUMBER", unique = true)
    private String identificationNumber;

    @Column(name = "BIRTH_OR_ESTABLISHMENT_DATE")
    private LocalDate birthOrEstablishmentDate;

    @Column(name = "CUSTOMER_TYPE")
    private CustomerType customerType;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    private String address;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

}
