package com.stokessoftwaresolutions.checkbookapi.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckbookDto {

    private long id;
    @Size(min=2, message = "Name should have at least 2 characters")
    private String name;
    private BigDecimal currentBalance;
    private LocalDateTime createDate;

}
