package com.easybytes.accounts.entity;

import com.easybytes.accounts.audit.AuditAwareImpl;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@EntityListeners(AuditAwareImpl.class)
public class Accounts extends BaseEntity{

    @Column(name="customer_id")
    private Long customerId;

    @Column(name="account_number")
    @Id
    private Long accountNumber;

    @Column(name="account_type")
    private String accountType;

    @Column(name="branch_address")
    private String branchAddress;
}
