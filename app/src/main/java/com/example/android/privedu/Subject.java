package com.example.android.privedu;

/**
 * Created by Abay on 3/24/2018.
 */

public class Subject {
    int price;
    String name, lecture;

    public Subject(int price, String name, String lecture) {
        this.price = price;
        this.name = name;
        this.lecture = lecture;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getLecture() {
        return lecture;
    }
}
