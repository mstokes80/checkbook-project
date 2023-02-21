package com.stokessoftwaresolutions.checkbookapi.repository;


import com.stokessoftwaresolutions.checkbookapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByCheckbookId(long id);
}
