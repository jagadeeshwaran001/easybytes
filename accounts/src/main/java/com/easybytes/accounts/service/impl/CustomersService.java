package com.easybytes.accounts.service.impl;

import com.easybytes.accounts.client.CardsFeignClient;
import com.easybytes.accounts.client.LoansFeignClient;
import com.easybytes.accounts.dto.AccountsDto;
import com.easybytes.accounts.dto.CardsDto;
import com.easybytes.accounts.dto.CustomerDetailsDto;
import com.easybytes.accounts.dto.LoansDto;
import com.easybytes.accounts.entity.Accounts;
import com.easybytes.accounts.entity.Customer;
import com.easybytes.accounts.exception.ResourceNotFoundException;
import com.easybytes.accounts.mapper.AccountsMapper;
import com.easybytes.accounts.mapper.CustomerMapper;
import com.easybytes.accounts.repository.AccountsRepository;
import com.easybytes.accounts.repository.CustomerRepository;
import com.easybytes.accounts.service.ICustomersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomersService implements ICustomersService {

    private final CustomerRepository customerRepository;
    private final AccountsRepository accountsRepository;
    private final LoansFeignClient loansFeignClient;
    private final CardsFeignClient cardsFeignClient;


    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
