package com.stokessoftwaresolutions.checkbookapi.services;

import com.stokessoftwaresolutions.checkbookapi.dto.TransactionDto;
import com.stokessoftwaresolutions.checkbookapi.security.model.User;

import java.util.List;

public interface TransactionService {

    TransactionDto saveTransaction(TransactionDto transactionDto, long checkbookId, User user);
    List<TransactionDto> getTransactionsByCheckbookId(long checkbookId, User user);
    TransactionDto getTransactionById(Long transactionId, User user);
    void deleteTransactionById(Long transactionId, User user);

}
