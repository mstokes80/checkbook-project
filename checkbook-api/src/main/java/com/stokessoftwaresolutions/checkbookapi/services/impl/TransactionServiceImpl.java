package com.stokessoftwaresolutions.checkbookapi.services.impl;

import com.stokessoftwaresolutions.checkbookapi.dto.TransactionDto;
import com.stokessoftwaresolutions.checkbookapi.exceptions.PermissionDeniedException;
import com.stokessoftwaresolutions.checkbookapi.model.Checkbook;
import com.stokessoftwaresolutions.checkbookapi.model.Transaction;
import com.stokessoftwaresolutions.checkbookapi.repository.CheckbookRepository;
import com.stokessoftwaresolutions.checkbookapi.repository.TransactionRepository;
import com.stokessoftwaresolutions.checkbookapi.security.model.User;
import com.stokessoftwaresolutions.checkbookapi.services.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CheckbookRepository checkbookRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TransactionDto saveTransaction(TransactionDto transactionDto, long checkbookId, User user) {
        Checkbook checkbook = checkbookRepository.findById(checkbookId).get();
        if(checkbook == null || !checkbook.getUser().equals(user)) {
            throw new PermissionDeniedException("You do not have permission to add a transaction to this checkbook.");
        }
        if(transactionDto.getTransactionType().equals(Transaction.TRANSACTION_TYPE_WITHDRAWAL)
                || transactionDto.getTransactionType().equals(Transaction.TRANSACTION_TYPE_DEPOSIT)) {
            if(transactionDto.getTransactionType().equals(Transaction.TRANSACTION_TYPE_DEPOSIT)){
                checkbook.setCurrentBalance(checkbook.getCurrentBalance().add(transactionDto.getAmount()));
            } else {
                checkbook.setCurrentBalance(checkbook.getCurrentBalance().subtract(transactionDto.getAmount()));
            }
            checkbook = checkbookRepository.save(checkbook);
        }
        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
        transaction.setCheckbook(checkbook);
        transaction = transactionRepository.save(transaction);
        return modelMapper.map(transaction, TransactionDto.class);
    }

    @Override
    public List<TransactionDto> getTransactionsByCheckbookId(long checkbookId, User user) {
       List<Transaction> transactions = transactionRepository.findByCheckbookId(checkbookId);
       transactions.stream().findFirst().ifPresent(transaction -> {
           if(!transaction.getCheckbook().getUser().equals(user)) {
               throw new PermissionDeniedException("You do not have permission to view these transactions.");
           }
       });
       List<TransactionDto> transactionDtos = transactions.stream().map(transaction -> modelMapper.map(transaction, TransactionDto.class)).collect(Collectors.toList());
       return transactionDtos;
    }

    @Override
    public TransactionDto getTransactionById(Long transactionId, User user) {
        Transaction transaction = transactionRepository.findById(transactionId).get();
        if(transaction == null || !transaction.getCheckbook().getUser().equals(user)) {
            throw new PermissionDeniedException("You do not have permission to view this transaction.");
        }
        return modelMapper.map(transaction, TransactionDto.class);
    }

    @Override
    public void deleteTransactionById(Long transactionId, User user) {
        Transaction transaction = transactionRepository.findById(transactionId).get();
        if(transaction != null && transaction.getCheckbook().getUser().equals(user)) {
            Checkbook checkbook = transaction.getCheckbook();
            if(transaction.getTransactionType().equals(Transaction.TRANSACTION_TYPE_DEPOSIT)) {
                checkbook.setCurrentBalance(checkbook.getCurrentBalance().subtract(transaction.getAmount()));
            } else {
                checkbook.setCurrentBalance(checkbook.getCurrentBalance().add(transaction.getAmount()));
            }
            transactionRepository.delete(transaction);
        } else {
            throw new PermissionDeniedException("You do not have permission to delete this transaction.");
        }
    }
}
