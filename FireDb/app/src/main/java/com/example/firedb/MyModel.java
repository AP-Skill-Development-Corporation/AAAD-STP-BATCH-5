package com.example.firedb;

public class MyModel {
    String name,roll,number;

    public String getName() {
        return name;
    }

    public String getRoll() {
        return roll;
    }

    public String getNumber() {
        return number;
    }

    public MyModel(String name, String roll, String number) {
        this.name = name;
        this.roll = roll;
        this.number = number;
    }

    public MyModel() {
    }
}
