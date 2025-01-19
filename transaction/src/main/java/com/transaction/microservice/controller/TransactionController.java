package com.transaction.microservice.controller;


import com.codes.common.dto.Response;
import com.codes.common.dto.TransactionDto;
import com.codes.common.enums.TransactionStatus;
import com.codes.common.enums.TransactionType;
import com.transaction.microservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/transactions")
@PreAuthorize("hasAuthority('ADMIN')")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "creates transaction based on type",
            responses = {
                    @ApiResponse(responseCode = "201", description = "transaction created"),
                    @ApiResponse(responseCode = "400", description = "bad request"),
                    @ApiResponse(responseCode = "404", description = "not found"),
            })
    @PostMapping("/add")
    public ResponseEntity<Response<TransactionDto>> createTransaction(@RequestBody TransactionDto transactionDto) {
        return new ResponseEntity<>(transactionService.createTransaction(transactionDto), HttpStatus.CREATED);
    }

    @GetMapping("/getTransactionStatus")
    public ResponseEntity<Response<TransactionDto>> getTransactionStatus(@RequestParam Long trackingCode) {
        return new ResponseEntity<>(transactionService.getTransactionStatusByTrackingCode(trackingCode), HttpStatus.OK);
    }

    @GetMapping("/findAllTransactionsByFilter")
    public ResponseEntity<Response<List<TransactionDto>>> findAllTransactionsByFilter(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) Long fromValue,
            @RequestParam(required = false) Long toValue,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate

    ) {
        return new ResponseEntity<>(
                transactionService.findAllTransactionsByFilter(
                        page, size, type, accountNumber, fromValue, toValue, fromDate, toDate),
                HttpStatus.OK);
    }

}
