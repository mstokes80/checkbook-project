package com.stokessoftwaresolutions.checkbookapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
