package com.example.resumeanalyzer.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.resumeanalyzer.model.User;
import com.example.resumeanalyzer.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    public String save(User user) {
        final User save = repo.save(user);
        return "User Registered";
    }
}