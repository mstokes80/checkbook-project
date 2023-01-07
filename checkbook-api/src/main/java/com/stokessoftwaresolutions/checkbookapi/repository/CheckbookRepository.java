package com.stokessoftwaresolutions.checkbookapi.repository;

import com.stokessoftwaresolutions.checkbookapi.model.Checkbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckbookRepository extends JpaRepository<Checkbook, Long> {
}
