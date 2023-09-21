package com.example.org.repositories;

import com.example.org.models.Tasks;
import com.example.org.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Tasks, Integer> {

    List<Tasks> getAllByUser(User user);
}
