package com.example.psasa.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String phoneNumber;
    private String password;
    private String uid;
    private long wallet;

    public User() {
    }

    public User(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getUid() {
        return uid;
    }


    public long getWallet() {
        return wallet;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setWallet(long wallet) {
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return "User{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", uid='" + uid + '\'' +
                ", wallet=" + wallet +
                '}';
    }

}
