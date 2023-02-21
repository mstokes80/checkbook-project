package com.stokessoftwaresolutions.checkbookapi.controller;

import com.stokessoftwaresolutions.checkbookapi.dto.CheckbookDto;
import com.stokessoftwaresolutions.checkbookapi.dto.TransactionDto;
import com.stokessoftwaresolutions.checkbookapi.security.model.User;
import com.stokessoftwaresolutions.checkbookapi.security.repository.UserRepository;
import com.stokessoftwaresolutions.checkbookapi.services.CheckbookService;
import com.stokessoftwaresolutions.checkbookapi.services.TransactionService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CheckbookController {

    @Autowired
    private CheckbookService checkbookService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/checkbooks")
    public List<CheckbookDto> getCheckbooks() {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        List<CheckbookDto> checkbookDtos = checkbookService.getCheckbooksByUser(user);
        return checkbookDtos;
    }

    @GetMapping("/checkbooks/{id}")
    public ResponseEntity<CheckbookDto> getCheckbook(@PathVariable Long id) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        CheckbookDto checkbook = checkbookService.getCheckbookById(id, user);
        return ResponseEntity.ok().body(checkbook);
    }

    @PostMapping("/checkbooks")
    public ResponseEntity<CheckbookDto> saveCheckbook(@Valid @RequestBody CheckbookDto checkbookDto) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());

        CheckbookDto savedCheckbook = checkbookService.saveCheckbook(checkbookDto, user);
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
        checkbookService.deleteCheckbookById(id, user);
    }

    @GetMapping("/checkbooks/{id}/transactions")
    public ResponseEntity<List<TransactionDto>> getAllTransactions(@PathVariable long id) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        List<TransactionDto> transactions = transactionService.getTransactionsByCheckbookId(id, user);
        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/checkbooks/transactions/{id}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable long id) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        TransactionDto transactionDto = transactionService.getTransactionById(id, user);
        return ResponseEntity.ok().body(transactionDto);
    }

    @PostMapping("/checkbooks/{id}/transactions")
    public ResponseEntity<TransactionDto> saveTransaction(@PathVariable long id, @Valid @RequestBody TransactionDto transactionDto) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        TransactionDto savedTransaction = transactionService.saveTransaction(transactionDto, id, user);
        return ResponseEntity.created(null).body(savedTransaction);
    }

    @DeleteMapping("/checkbooks/transactions/{id}")
    public void deleteTransaction(@PathVariable long id) {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElse(new User());
        transactionService.deleteTransactionById(id, user);
    }

}
