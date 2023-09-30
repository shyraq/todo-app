package com.example.org.services;

import com.example.org.models.User;
import com.example.org.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//Для валидатора
@Service
@Transactional
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<User> findByUsername(String username){
        return userRepo.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Optional<User> findByPassword(String password) {
        return userRepo.findByPassword(password);
    }

    public void addCompletedTask(User user){
        user.setCompletedTasks(user.getCompletedTasks() + 1);
        userRepo.save(user);
    }

    public User findByContext() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Optional<User> user = findByUsername(username);

        if (user.isPresent())
            return user.get();
        throw new Exception("User not found");
    }
}
