package com.stokessoftwaresolutions.checkbookapi.repository;

import com.stokessoftwaresolutions.checkbookapi.model.Checkbook;
import com.stokessoftwaresolutions.checkbookapi.security.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckbookRepository extends JpaRepository<Checkbook, Long> {

    List<Checkbook> findByUser(User user);
}
