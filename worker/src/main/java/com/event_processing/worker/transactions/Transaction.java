package com.event_processing.worker.transactions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String type;

    @NotNull
    @Column(nullable = false)
    private Integer accountId;

    private Integer merchantId;

    private Integer referenceEventId;

    @NotNull
    @Column(nullable = false)
    private Integer amountCents;

    @NotBlank
    @Column(nullable = false)
    private String currency;


    // default contructor
    public Transaction() {

    }

    // constructors
    public Transaction(String type, Integer accountId, Integer amountCents, String currency) {
        this.type = type;
        this.accountId = accountId;
        this.amountCents = amountCents;
        this.currency = currency;
    }

    public Transaction(String type, Integer accountId, Integer merchantId, Integer amountCents, String currency) {
        this.type = type;
        this.accountId = accountId;
        this.merchantId = merchantId;
        this.amountCents = amountCents;
        this.currency = currency;
    }

    public Transaction(String type, Integer accountId, Integer merchantId, Integer referenceEventId, Integer amountCents, String currency) {
        this.type = type;
        this.accountId = accountId;
        this.merchantId = merchantId;
        this.referenceEventId = referenceEventId;
        this.amountCents = amountCents;
        this.currency = currency;
    }

    // getters
    public Long id() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public Integer getAccountId() {
        return this.accountId;
    }

    public Integer getMerchantId() {
        return this.merchantId;
    }

    public Integer getReferenceEventId() {
        return this.referenceEventId;
    }

    public Integer getAmountCents() {
        return this.amountCents;
    }

    public String getCurrency() {
        return this.currency;
    }

    // setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }
    
    public void setReferenceEventId(Integer referenceEventId) {
        this.referenceEventId = referenceEventId;
    }

    public void setAmountCents(Integer amountCents) {
        this.amountCents = amountCents;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
