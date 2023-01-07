package com.stokessoftwaresolutions.checkbookapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stokessoftwaresolutions.checkbookapi.security.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
public class Checkbook {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Size(min=2, message = "Name should have at least 2 characters")
    private String name;
    private BigDecimal currentBalance;
    @OneToMany(mappedBy = "checkbook", orphanRemoval = true)
    @JsonIgnore
    private List<Transaction> transactions;
    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private User user;
    private Date createDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
