package com.example.org.services;

import com.example.org.models.Month;
import com.example.org.repositories.MonthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MonthService {

    private final MonthRepo repo;

    @Autowired
    public MonthService(MonthRepo repo) {
        this.repo = repo;
    }

    public Month findById(int id) throws Exception {
        Optional<Month> optionalMonth = repo.findById(id);
        if(optionalMonth.isPresent()){
            return optionalMonth.get();
        }
        throw new Exception("Month not found");
    }

    public List<Month> getAll(){
        return repo.findAll();
    }
}
