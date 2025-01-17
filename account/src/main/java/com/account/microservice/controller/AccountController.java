package com.account.microservice.controller;

import com.account.microservice.service.AccountService;
import com.codes.common.dto.AccountDto;
import com.codes.common.dto.CustomerDto;
import com.codes.common.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "creates account for customer",
            responses = {
                    @ApiResponse(responseCode = "201", description = "account created")
            })
    @PostMapping("/add")
    public ResponseEntity<Response<AccountDto>> createAccount(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(accountService.createAccount(customerDto), HttpStatus.CREATED);
    }

    @Operation(summary = "updates account",
            responses = {
                    @ApiResponse(responseCode = "201", description = "account updated"),
                    @ApiResponse(responseCode = "404", description = "account not found")
            })
    @PutMapping("/update")
    public ResponseEntity<Response<AccountDto>> updateAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.updateAccount(accountDto), HttpStatus.CREATED);
    }

    @Operation(summary = "returns account number of a customer with customer's identification number",
            responses = {
                    @ApiResponse(responseCode = "200", description = "account number returned"),
                    @ApiResponse(responseCode = "404", description = "account or customer not found")
            })
    @GetMapping("/getAccountNumber")
    public ResponseEntity<Response<String>> getAccountNumberByIdentificationNumber(@RequestParam String identificationNumber) {
        return new ResponseEntity<>(accountService.getAccountNumberByIdentificationNumber(identificationNumber), HttpStatus.OK);
    }

    @Operation(summary = "returns customer with account number",
            responses = {
                    @ApiResponse(responseCode = "200", description = "account number returned"),
                    @ApiResponse(responseCode = "404", description = "account or customer not found")
            })
    @GetMapping("/getCustomer")
    public ResponseEntity<Response<AccountDto>> getCustomerByAccountNumber(@RequestParam String accountNumber) {
        return new ResponseEntity<>(accountService.getCustomerByAccountNumber(accountNumber), HttpStatus.OK);
    }

    @Operation(summary = "returns remaining cash of an account number",
            responses = {
                    @ApiResponse(responseCode = "200", description = "remaining cash returned"),
                    @ApiResponse(responseCode = "404", description = "account not found")
            })
    @GetMapping("/getRemainingCash")
    public ResponseEntity<Response<String>> getRemainingCashByAccountNumber(@RequestParam String accountNumber) {
        return new ResponseEntity<>(accountService.getRemainingCashByAccountNumber(accountNumber), HttpStatus.OK);
    }

    @Operation(summary = "deletes an account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "account deleted"),
                    @ApiResponse(responseCode = "404", description = "account not found")
            })
    @DeleteMapping("/delete")
    public ResponseEntity<Response<String>> deleteAccount(@RequestParam String accountNumber) {
        return new ResponseEntity<>(accountService.deleteAccount(accountNumber), HttpStatus.OK);
    }

}
