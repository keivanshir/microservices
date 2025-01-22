package com.codes.common.entity;

import com.codes.common.enums.CustomerType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "\\d{10}", message = "کد ملی / شناسه ملی / کد فراگیر باید 10 رقمی باشد")
    private String identificationNumber;

    @Column(name = "BIRTH_OR_ESTABLISHMENT_DATE")
    @Past(message = "تاریخ تولد یا تاسیس باید کمتر از تاریخ کنونی باشد")
    private LocalDate birthOrEstablishmentDate;

    @Column(name = "CUSTOMER_TYPE")
    private CustomerType customerType;

    @Column(name = "PHONE_NUMBER")
    @Pattern(regexp = "\\d{11}", message = "شماره تلفن همراه نامعتبر است")
    private String phoneNumber;

    private String address;

    @Column(name = "POSTAL_CODE")
    @Pattern(regexp = "\\d{10}", message = "کد پستی باید 10 رقمی باشد")
    private String postalCode;

}
