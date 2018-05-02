package com.example.android.privedu;

/**
 * Created by Abay on 3/2/2018.
 *
 */

public class User {
    String fullName, email, password, phoneNumber, role;

    public User(String fullName, String email, String password, String phoneNumber, String role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }
}
