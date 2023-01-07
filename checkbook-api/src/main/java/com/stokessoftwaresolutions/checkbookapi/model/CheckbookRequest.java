package com.stokessoftwaresolutions.checkbookapi.model;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CheckbookRequest {

    @Size(min=2, message = "Name should have at least 2 characters")
    private String name;
    private BigDecimal currentBalance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

}
