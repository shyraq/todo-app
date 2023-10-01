package com.example.org.repositories;

import com.example.org.models.Month;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthRepo  extends JpaRepository<Month, Integer> {


}
