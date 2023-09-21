package com.example.org.services;

import com.example.org.models.User;
import com.example.org.repositories.UserRepo;
import com.example.org.security.UsersDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class UsersDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Autowired
    public UsersDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByUsername(username);

        if(user.isEmpty())
            throw new UsernameNotFoundException("User not found!");

        return new UsersDetails(user.get());
    }
}
