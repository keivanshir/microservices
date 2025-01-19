package com.transaction.microservice.utils;



import com.codes.common.enums.AccountStatus;
import com.transaction.microservice.config.Config;
import org.springframework.stereotype.Component;

@Component
public class TransactionUtils {

    private final Config config;

    public static final long MIN_THRESHOLD = 500_000L;
    public static final long MAX_THRESHOLD = 10_000_000L;

    public TransactionUtils(Config config) {
        this.config = config;
    }

    public Integer calculateFee(long value){
        if( value >= MIN_THRESHOLD && value < MAX_THRESHOLD){
            return (int) (value * config.feePercentage);
        } else if (value < MIN_THRESHOLD){
            return config.minimumFee;
        } else {
            return config.maximumFee;
        }
    }

    public boolean isEnabled(AccountStatus accountStatus){
        return !accountStatus.equals(AccountStatus.BLOCKED) && !accountStatus.equals(AccountStatus.DISABLED);
    }

    public boolean accountsNotEqual(String sourceAccountNumber, String destinationAccountNumber){
        return !sourceAccountNumber.equals(destinationAccountNumber);
    }

    public boolean isRemainingSufficient(long remaining, long value){
        return value <= (remaining + calculateFee(value));
    }

}
