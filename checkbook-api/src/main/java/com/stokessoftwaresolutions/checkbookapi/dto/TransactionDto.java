package com.stokessoftwaresolutions.checkbookapi.dto;

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
public class TransactionDto {

    private long id;
    private String description;
    @Positive(message = "Amount must be a positive number > 0.")
    private BigDecimal amount;
    @NotBlank(message = "Transaction Type is required.")
    private String transactionType;
    private Date payedDate;
    private Date createDate;
}
