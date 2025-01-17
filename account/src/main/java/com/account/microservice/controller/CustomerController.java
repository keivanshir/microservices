package com.account.microservice.controller;

import com.account.microservice.service.CustomerService;
import com.codes.common.dto.CustomerDto;
import com.codes.common.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "creates customer",
            responses = {
                    @ApiResponse(responseCode = "201", description = "customer created")
            })
    @PostMapping("/add")
    public ResponseEntity<Response<CustomerDto>> createCustomer(@RequestBody CustomerDto customerDto) {
        return new ResponseEntity<>(customerService.createCustomer(customerDto), HttpStatus.CREATED);
    }

    @Operation(summary = "deletes customer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "customer deleted"),
                    @ApiResponse(responseCode = "404", description = "customer not found"),
            })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response<String>> deleteCustomer(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.deleteCustomer(id), HttpStatus.OK);
    }

}
