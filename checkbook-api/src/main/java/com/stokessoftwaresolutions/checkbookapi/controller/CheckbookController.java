package com.stokessoftwaresolutions.checkbookapi.controller;

import com.stokessoftwaresolutions.checkbookapi.dto.CheckbookDto;
import com.stokessoftwaresolutions.checkbookapi.dto.TransactionDto;
import com.stokessoftwaresolutions.checkbookapi.model.Checkbook;
import com.stokessoftwaresolutions.checkbookapi.model.Transaction;
import com.stokessoftwaresolutions.checkbookapi.repository.CheckbookRepository;
import com.stokessoftwaresolutions.checkbookapi.repository.TransactionRepository;
import com.stokessoftwaresolutions.checkbookapi.security.model.User;
import com.stokessoftwaresolutions.checkbookapi.security.repository.UserRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CheckbookController {

    @Autowired
    private CheckbookRepository checkbookRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/checkbooks")
    public List<CheckbookDto> getCheckbooks() {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        List<CheckbookDto> checkbookDtos = checkbookRepository.findByUser(user).stream()
                .map(checkbook -> modelMapper.map(checkbook, CheckbookDto.class)).collect(Collectors.toList());
        return checkbookDtos;
    }

    @GetMapping("/checkbooks/{id}")
    public ResponseEntity<CheckbookDto> getCheckbook(@PathVariable Long id) {
        Checkbook checkbook = checkbookRepository.findById(id).orElse(null);
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        if(checkbook == null || !checkbook.getUser().equals(user)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(modelMapper.map(checkbook, CheckbookDto.class));
    }

    @PostMapping("/checkbooks")
    public ResponseEntity<CheckbookDto> saveCheckbook(@Valid @RequestBody CheckbookDto checkbookDto) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        Checkbook checkbook = modelMapper.map(checkbookDto, Checkbook.class);
        checkbook.setUser(user);
        checkbook.setCreateDate(new Date());
        Checkbook savedCheckbook = checkbookRepository.save(checkbook);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCheckbook.getId())
                .toUri();
        return ResponseEntity.created(location).body(modelMapper.map(savedCheckbook, CheckbookDto.class));
    }

    @DeleteMapping("/checkbooks/{id}")
    public void deleteCheckbook(@PathVariable long id) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        Checkbook checkbook = checkbookRepository.getReferenceById(id);
        if(checkbook.getUser().equals(user)) {
            checkbookRepository.deleteById(id);
        }
    }

    @GetMapping("/checkbooks/{id}/transactions")
    public ResponseEntity<List<TransactionDto>> getAllTransactions(@PathVariable long id) {
        Checkbook checkbook = checkbookRepository.findById(id).orElse(null);
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        if(!checkbook.getUser().equals(user)) {
            return ResponseEntity.notFound().build();
        }
        List<TransactionDto> transactions = transactionRepository.findByCheckbook(checkbook).stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/checkbooks/transactions/{id}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable long id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        if (transaction == null || !transaction.getCheckbook().getUser().equals(user)) {
            return ResponseEntity.notFound().build();
        }
        TransactionDto transactionDto = modelMapper.map(transaction, TransactionDto.class);
        return ResponseEntity.ok().body(transactionDto);
    }

    @PostMapping("/checkbooks/{id}/transactions")
    public ResponseEntity<TransactionDto> saveTransaction(@PathVariable long id, @Valid @RequestBody TransactionDto transactionDto) {
        Checkbook checkbook = checkbookRepository.findById(id).orElse(null);
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        if(checkbook == null || !checkbook.getUser().equals(user)) {
            return ResponseEntity.notFound().build();
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
        else {
            return  ResponseEntity.badRequest().build();
        }

        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
        transaction.setCreateDate(new Date());
        transaction.setCheckbook(checkbook);
        Transaction savedTransaction = transactionRepository.save(transaction);
        //TODO: Fix Path here.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTransaction.getId())
                .toUri();
        return ResponseEntity.created(location).body(modelMapper.map(savedTransaction, TransactionDto.class));
    }

    @DeleteMapping("/checkbooks/transactions/{id}")
    public void deleteTransaction(@PathVariable long id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        if(transaction != null && transaction.getCheckbook().getUser().equals(user)) {
            Checkbook checkbook = transaction.getCheckbook();
            if(transaction.getTransactionType().equals(Transaction.TRANSACTION_TYPE_DEPOSIT)) {
                checkbook.setCurrentBalance(checkbook.getCurrentBalance().subtract(transaction.getAmount()));
            } else {
                checkbook.setCurrentBalance(checkbook.getCurrentBalance().add(transaction.getAmount()));
            }
            transactionRepository.delete(transaction);
        }
    }

}
