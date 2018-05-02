package com.example.android.privedu;

/**
 * Created by Abay on 3/24/2018.
 */

public class Subject {
    int price, id_teacher;
    String name;

    public Subject(int price, int id_teacher, String name) {
        this.price = price;
        this.id_teacher = id_teacher;
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public int getId_teacher() {
        return id_teacher;
    }

    public String getName() {
        return name;
    }
}
