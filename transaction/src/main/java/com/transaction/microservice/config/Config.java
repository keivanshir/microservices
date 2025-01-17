package com.transaction.microservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Config {

    @Value("${app.fee.minimum}")
    public int minimumFee; // x

    @Value("${app.fee.maximum}")
    public int maximumFee; // y

    @Value("${app.fee.percentage}")
    public double feePercentage; // z

    @Value("${app.username}")
    public String systemUsername;

    @Value("${app.password}")
    public String systemPassword;

    @Value("${app.bank.account}")
    public String mainBankAccount;

}
