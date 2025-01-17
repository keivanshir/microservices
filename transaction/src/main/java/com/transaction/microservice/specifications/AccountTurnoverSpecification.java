package com.transaction.microservice.specifications;


import com.codes.common.entity.Transaction;
import com.codes.common.enums.TransactionType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountTurnoverSpecification {

    public static Specification<Transaction> filterTransactions(
            TransactionType type,
            String accountNumber,
            Long fromValue,
            Long toValue,
            LocalDate fromDate,
            LocalDate toDate
    ){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (type != null){
                predicateList.add(criteriaBuilder.equal(root.get("transactionType"), type));
            }

            if (accountNumber != null){
                predicateList.add(criteriaBuilder.or(
                        criteriaBuilder.equal(root.get("sourceAccountNumber"), accountNumber),
                        criteriaBuilder.equal(root.get("destinationAccountNumber"), accountNumber)
                ));
            }

            if (fromValue != null && toValue != null){
                predicateList.add(criteriaBuilder.between(
                        root.get("value"),
                        fromValue,
                        toValue
                ));
            }

            if (fromDate != null && toDate != null){
                predicateList.add(criteriaBuilder.between(
                        root.get("createdAt"),
                        fromDate,
                        toDate
                ));
            }

            return criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
        };
    }
}
