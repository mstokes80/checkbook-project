package com.stokessoftwaresolutions.checkbookapi.controller;

import com.stokessoftwaresolutions.checkbookapi.model.Checkbook;
import com.stokessoftwaresolutions.checkbookapi.model.CheckbookRequest;
import com.stokessoftwaresolutions.checkbookapi.model.Transaction;
import com.stokessoftwaresolutions.checkbookapi.repository.CheckbookRepository;
import com.stokessoftwaresolutions.checkbookapi.repository.TransactionRepository;
import com.stokessoftwaresolutions.checkbookapi.security.model.User;
import com.stokessoftwaresolutions.checkbookapi.security.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CheckbookController {

    @Autowired
    private CheckbookRepository checkbookRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/checkbooks")
    public List<Checkbook> getCheckbooks() {
        return checkbookRepository.findAll();
    }

    @GetMapping("/checkbooks/{id}")
    public ResponseEntity<Checkbook> getCheckbook(@PathVariable Long id) {
        Checkbook checkbook = checkbookRepository.findById(id).orElse(null);
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        if(checkbook == null || !checkbook.getUser().equals(user)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(checkbook);
    }

    @PostMapping("/checkbooks")
    public ResponseEntity<Checkbook> saveCheckbook(@Valid @RequestBody CheckbookRequest checkbookRequest) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        Checkbook checkbook = new Checkbook();
        checkbook.setCurrentBalance(checkbookRequest.getCurrentBalance());
        checkbook.setName(checkbookRequest.getName());
        checkbook.setUser(user);
        checkbook.setCreateDate(new Date());
        Checkbook savedCheckbook = checkbookRepository.save(checkbook);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCheckbook.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedCheckbook);
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
    public ResponseEntity<List<Transaction>> getAllTransactions(@PathVariable long id) {
        Checkbook checkbook = checkbookRepository.findById(id).orElse(null);
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        if(!checkbook.getUser().equals(user)) {
            return ResponseEntity.notFound().build();
        }
        List<Transaction> transactions = transactionRepository.findByCheckbook(checkbook);
        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/checkbooks/transactions/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable long id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        if (transaction == null || !transaction.getCheckbook().getUser().equals(user)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(transaction);
    }

    @PostMapping("/checkbooks/{id}/transactions")
    public ResponseEntity<Transaction> saveTransaction(@PathVariable long id, @Valid @RequestBody Transaction transaction) {
        Checkbook checkbook = checkbookRepository.findById(id).orElse(null);
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        if(checkbook == null || !checkbook.getUser().equals(user)) {
            return ResponseEntity.notFound().build();
        }
        if(transaction.getTransactionType().equals(Transaction.TRANSACTION_TYPE_WITHDRAWAL)
                || transaction.getTransactionType().equals(Transaction.TRANSACTION_TYPE_DEPOSIT)) {
            if(transaction.getTransactionType().equals(Transaction.TRANSACTION_TYPE_DEPOSIT)){
                checkbook.setCurrentBalance(checkbook.getCurrentBalance().add(transaction.getAmount()));
            } else {
                checkbook.setCurrentBalance(checkbook.getCurrentBalance().subtract(transaction.getAmount()));
            }
            checkbook = checkbookRepository.save(checkbook);
        }
        else {
            return  ResponseEntity.badRequest().build();
        }

        transaction.setCreateDate(new Date());
        transaction.setCheckbook(checkbook);
        Transaction savedTransaction = transactionRepository.save(transaction);
        //TODO: Fix Path here.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTransaction.getId())
                .toUri();
        return ResponseEntity.created(location).body(transaction);
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
