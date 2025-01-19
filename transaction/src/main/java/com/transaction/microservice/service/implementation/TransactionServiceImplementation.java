package com.transaction.microservice.service.implementation;


import com.transaction.microservice.config.Config;
import com.codes.common.dto.Mapper;
import com.codes.common.dto.Response;
import com.codes.common.dto.TransactionDto;
import com.codes.common.entity.Transaction;
import com.codes.common.enums.TransactionStatus;
import com.codes.common.enums.TransactionType;
import com.codes.common.exception.NotFoundException;
import com.transaction.microservice.repository.TransactionRepository;
import com.transaction.microservice.service.TransactionService;
import com.transaction.microservice.service.implementation.accountStrategy.OperationStrategy;
import com.transaction.microservice.service.implementation.accountStrategy.OperatorSelector;
import com.transaction.microservice.service.implementation.transactionStrategy.TransactionCreatorStrategy;
import com.transaction.microservice.service.implementation.transactionStrategy.TransactionStrategy;
import com.transaction.microservice.specifications.AccountTurnoverSpecification;
import com.transaction.microservice.utils.TransactionUtils;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionServiceImplementation implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final OperatorSelector operatorSelector;
    private final TransactionUtils transactionUtils;
    private final Config config;
    private final Mapper mapper;


    public TransactionServiceImplementation(TransactionRepository transactionRepository,
                                            OperatorSelector operatorSelector,
                                            TransactionUtils transactionUtils,
                                            Config config, Mapper mapper) {
        this.transactionRepository = transactionRepository;
        this.operatorSelector = operatorSelector;
        this.transactionUtils = transactionUtils;
        this.config = config;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public Response<TransactionDto> createTransaction(TransactionDto transactionDto) {

        TransactionCreatorStrategy strategy = TransactionStrategy.getTransaction(transactionDto.getTransactionType());
        Transaction transaction = strategy.createTransaction(transactionDto.getSourceAccountNumber(),
                transactionDto.getDestinationAccountNumber());
        transaction.setValue(transactionDto.getValue());
        transaction.setTrackingCode(generate10DigitNumber());

        OperationStrategy operationStrategy = operatorSelector.getStrategy(transactionDto.getTransactionType());

        Transaction savedTransaction;

        try{
            operationStrategy.operate(transactionDto.getSourceAccountNumber(),
                    transactionDto.getDestinationAccountNumber(), transactionDto.getValue());
            if (transactionDto.getTransactionType().equals(TransactionType.TRANSFER)){
                operationStrategy.operate(transactionDto.getSourceAccountNumber(),
                        config.mainBankAccount, transactionUtils.calculateFee(transactionDto.getValue()).longValue());
            }
            transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);

        } catch (Exception exception){
            exception.printStackTrace();
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            log.error("transaction failed: {}", exception.getMessage());
            transaction.setStatusReason(exception.getMessage());
        } finally {
            savedTransaction = transactionRepository.save(transaction);
        }

        log.info("transaction saved and completed!");
        return Response.<TransactionDto>builder()
                .statusCode(200)
                .message("تراکنش ثبت شد!")
                .data(mapper.mapToTransactionDto(savedTransaction))
                .build();
    }

    public String generate10DigitNumber() {
        Random random = new Random();
        long number;

        do {
            number = 1_000_000_000L + (long) (random.nextDouble() * 9_999_999_999L);
        } while (!isUniqueInDatabase(number));

        return String.valueOf(number);
    }

    private boolean isUniqueInDatabase(long number) {
        Optional<Transaction> transactionOptional = transactionRepository.findTransactionByTrackingCode(String.valueOf(number));
        return transactionOptional.isEmpty();
    }

    @Override
    public Response<TransactionDto> getTransactionStatusByTrackingCode(Long trackingCode) {
        Optional<Transaction> transactionOptional = transactionRepository.findTransactionByTrackingCode(String.valueOf(trackingCode));
        if (transactionOptional.isPresent()){
            return Response.<TransactionDto>builder()
                    .data(mapper.mapToTransactionDto(transactionOptional.get()))
                    .message("وضعیت تراکنش")
                    .statusCode(200)
                    .build();
        } else throw new NotFoundException("کد پیگیری نامعتبر است!");
    }

    @Override
    public Response<List<TransactionDto>> findAllTransactionsByFilter(int page,
                                                                      int size,
                                                                      TransactionType type,
                                                                      String accountNumber,
                                                                      Long fromValue,
                                                                      Long toValue,
                                                                      LocalDate fromDate,
                                                                      LocalDate toDate) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Specification<Transaction> specification = Specification.
                where(AccountTurnoverSpecification
                        .filterTransactions(type, accountNumber, fromValue, toValue, fromDate, toDate));

        Page<Transaction> transactionPage = transactionRepository.findAll(specification, pageable);
        if (!transactionPage.isEmpty()){
            return Response.<List<TransactionDto>>builder()
                    .statusCode(200)
                    .message("تراکنش های فیلتر شده")
                    .data(transactionPage.getContent()
                            .stream()
                            .map(mapper::mapToTransactionDto)
                            .collect(Collectors.toList()))
                    .build();
        } else throw new NotFoundException("تراکنشی پیدا نشد!");
    }
}
