package com.laioffer.delivery.service;

import com.laioffer.delivery.model.User;
import com.laioffer.delivery.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // example
    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    // example
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
