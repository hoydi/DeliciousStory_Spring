package com.i5.ds.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User registerUser(String userId, String userPw, String userName, String userEmail) {
        User user = new User(userEmail, userId, userName, userPw, new Date(System.currentTimeMillis()));
        return userRepository.save(user);
    }

    public User findUserById(String userId) {
        return userRepository.findByUserId(userId);
    }

    public User findUserByEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }

    @Transactional
    public boolean isUserExist(String userId) {
        return userRepository.findByUserId(userId) != null;
    }
}
