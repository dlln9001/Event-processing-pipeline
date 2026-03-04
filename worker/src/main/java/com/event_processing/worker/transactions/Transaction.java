package com.event_processing.worker.transactions;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "transactions")
public class Transaction {

    // number id, database will automatically increment and handle these
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp(source = SourceType.DB)
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    private OffsetDateTime createdAt;

    // the type of transaction (like if its a purchase or refund etc.)
    @NotBlank
    @Column(nullable = false)
    private String type;

    // the accountid of the user of that transaction
    @NotNull
    @Column(nullable = false)
    private Integer accountId;

    // merchantid that the user made the transaction with. May be null for non
    // purchase transaction types.
    private Integer merchantId;

    // id that references a previous transaction for refunds, for example if theres
    // a refund you want to reference which transaction you are refunding
    private Integer referenceEventId;

    // transaction amout in cents so there's no rounding errors
    @NotNull
    @Column(nullable = false)
    private Integer amountCents;

    // type of currency used, will be three characters, ex. USD or JPY
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

    public Transaction(String type, Integer accountId, Integer merchantId, Integer referenceEventId,
            Integer amountCents, String currency) {
        this.type = type;
        this.accountId = accountId;
        this.merchantId = merchantId;
        this.referenceEventId = referenceEventId;
        this.amountCents = amountCents;
        this.currency = currency;
    }

    // getters
    public Long getId() {
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
