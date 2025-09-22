package com.easybytes.accounts.controller.impl;

import com.easybytes.accounts.controller.ICustomerController;
import com.easybytes.accounts.dto.CustomerDetailsDto;
import com.easybytes.accounts.service.ICustomersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController implements ICustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final ICustomersService iCustomersService;

    @Override
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(String correlationId, String mobileNumber) {
        log.debug("eazyBank-correlation-id found: {} ", correlationId);
        CustomerDetailsDto customerDetailsDto = iCustomersService.fetchCustomerDetails(mobileNumber);
        return ResponseEntity.status(HttpStatus.SC_OK).body(customerDetailsDto);
    }
}
