package com.example.redis.service;

import com.example.redis.model.User;
import com.example.redis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    @Cacheable(value = "users", key = "#id")
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
    @CacheEvict(value = "users", key = "#user.id")
    public User save(User user) {
        return userRepository.save(user);
    }
    @CacheEvict(value = "users", key = "#user.id")
    public User update(User user) {
        findById(user.getId()); // Ensure the user exists
        return userRepository.save(user);
    }
    @CacheEvict(value = "users", allEntries = true)
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}