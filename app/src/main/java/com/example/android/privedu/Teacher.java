package com.example.android.privedu;

/**
 * Created by Abay on 3/30/2018.
 */

public class Teacher {
    String name, email, phone, skill;

    public Teacher(String name, String email, String phone, String skill) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.skill = skill;
    }

    public String getFullName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSkill() {
        return skill;
    }

    public String getPhoneNumber() {
        return phone;

    }

}
