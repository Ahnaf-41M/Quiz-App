package com.main.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "users")
public class User {

    @Id
    private int userId = -1;
    private String userName;
    private String userPass;
    private int totalCorrect;
    private boolean submitted = false;

    public User() {}

    public User(int userId, String userName, String userPass, int totalCorrect, boolean submitted) {
        this.userId = userId;
        this.userName = userName;
        this.userPass = userPass;
        this.totalCorrect = totalCorrect;
        this.submitted = submitted;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public int getTotalCorrect() {
        return totalCorrect;
    }

    public void setTotalCorrect(int totalCorrect) {
        this.totalCorrect = totalCorrect;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }



}
