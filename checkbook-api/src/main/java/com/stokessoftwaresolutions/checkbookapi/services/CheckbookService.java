package com.stokessoftwaresolutions.checkbookapi.services;

import com.stokessoftwaresolutions.checkbookapi.dto.CheckbookDto;
import com.stokessoftwaresolutions.checkbookapi.security.model.User;

import java.util.List;

public interface CheckbookService {

    CheckbookDto saveCheckbook(CheckbookDto checkbookDto, User user);
    List<CheckbookDto> getCheckbooksByUser(User user);
    CheckbookDto getCheckbookById(Long id, User user);
    void deleteCheckbookById(Long id, User user);
}
