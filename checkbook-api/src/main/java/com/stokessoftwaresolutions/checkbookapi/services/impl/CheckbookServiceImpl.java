package com.stokessoftwaresolutions.checkbookapi.services.impl;

import com.stokessoftwaresolutions.checkbookapi.dto.CheckbookDto;
import com.stokessoftwaresolutions.checkbookapi.exceptions.PermissionDeniedException;
import com.stokessoftwaresolutions.checkbookapi.model.Checkbook;
import com.stokessoftwaresolutions.checkbookapi.repository.CheckbookRepository;
import com.stokessoftwaresolutions.checkbookapi.security.model.User;
import com.stokessoftwaresolutions.checkbookapi.services.CheckbookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckbookServiceImpl implements CheckbookService {

    @Autowired
    private CheckbookRepository checkbookRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CheckbookDto saveCheckbook(CheckbookDto checkbookDto, User user) {
        Checkbook checkbook = modelMapper.map(checkbookDto, Checkbook.class);
        checkbook.setUser(user);
        checkbook = checkbookRepository.save(checkbook);
        return modelMapper.map(checkbook, CheckbookDto.class);
    }

    @Override
    public List<CheckbookDto> getCheckbooksByUser(User user) {
        List<Checkbook> checkbooks = checkbookRepository.findByUser(user);
        List<CheckbookDto> checkbookDtos = checkbooks.stream().map(checkbook -> {
            return modelMapper.map(checkbook, CheckbookDto.class);
        }).collect(Collectors.toList());
        return checkbookDtos;
    }

    @Override
    public CheckbookDto getCheckbookById(Long id, User user) {
        Checkbook checkbook = checkbookRepository.findById(id).orElse(null);
        if(checkbook == null || !checkbook.getUser().equals(user)) {
            throw new PermissionDeniedException("You do not have permission to access this checkbook.");
        }
        return modelMapper.map(checkbook, CheckbookDto.class);
    }

    @Override
    public void deleteCheckbookById(Long id, User user) {
        Checkbook checkbook = checkbookRepository.findById(id).orElse(null);
        if(checkbook == null || !checkbook.getUser().equals(user)) {
            throw new PermissionDeniedException("You do not have permission to delete this checkbook.");
        } else {
            checkbookRepository.deleteById(id);
        }
    }
}
