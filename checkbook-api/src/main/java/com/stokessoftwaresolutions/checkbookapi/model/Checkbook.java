package com.stokessoftwaresolutions.checkbookapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stokessoftwaresolutions.checkbookapi.security.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
}
