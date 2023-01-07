package com.stokessoftwaresolutions.checkbookapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Transaction {

    public static String TRANSACTION_TYPE_DEPOSIT = "D";
    public static String TRANSACTION_TYPE_WITHDRAWAL = "W";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @NotBlank(message = "Description is required.")
    private String description;
    @Positive(message = "Amount must be a positive number > 0.")
    private BigDecimal amount;
    @NotBlank(message = "Transaction Type is required.")
    private String transactionType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Checkbook checkbook;
    private Date payedDate;
    private Date createDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Checkbook getCheckbook() {
        return checkbook;
    }

    public void setCheckbook(Checkbook checkbook) {
        this.checkbook = checkbook;
    }

    public Date getPayedDate() {
        return payedDate;
    }

    public void setPayedDate(Date payedDate) {
        this.payedDate = payedDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
