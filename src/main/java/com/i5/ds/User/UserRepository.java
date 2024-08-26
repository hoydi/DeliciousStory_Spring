package com.i5.ds.User;


import com.i5.ds.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);

    User findByUserEmail(String userEmail);
}