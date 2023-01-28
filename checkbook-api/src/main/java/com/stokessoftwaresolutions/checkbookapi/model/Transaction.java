package com.stokessoftwaresolutions.checkbookapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {

    public static String TRANSACTION_TYPE_DEPOSIT = "D";
    public static String TRANSACTION_TYPE_WITHDRAWAL = "W";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private LocalDate payedDate;
    @CreationTimestamp
    private LocalDateTime createDate;
}
