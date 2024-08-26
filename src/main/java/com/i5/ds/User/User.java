package com.i5.ds.User;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ds_user")
public class User {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_pw")
    private String userPw;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "user_register")
    private Date userRegister;

    public User() {
        // 기본 생성자
    }

    public User(String userEmail, String userId, String userName, String userPw, Date userRegister) {
        this.userEmail = userEmail;
        this.userId = userId;
        this.userName = userName;
        this.userPw = userPw;
        this.userRegister = userRegister;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public Date getUserRegister() {
        return userRegister;
    }

    public void setUserRegister(Date userRegister) {
        this.userRegister = userRegister;
    }
}