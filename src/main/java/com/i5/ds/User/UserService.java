package com.i5.ds.User;

import com.i5.ds.User.User;
import com.i5.ds.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(String userId, String encodedPassword, String userName, String userEmail) {
        
        com.i5.ds.User.User user = new com.i5.ds.User.User(userEmail, userId, userName, encodedPassword, new Date(System.currentTimeMillis()));
        return userRepository.save(user);
    }

    public User findUserById(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Transactional
    public boolean isUserExist(String userId) {
        return userRepository.findByUserId(userId) != null;
    }

    @Transactional
    public void updateUserInfo(String userId, String userEmail, String userPw) {
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            user.setUserEmail(userEmail);
            user.setUserPw(userPw);
            userRepository.save(user);
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
