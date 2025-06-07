package com.example.assignment_5;

public class User {
    public String name, email, phone,bloodType,city,Id;

    public User() {
        // Required empty constructor for Firebase
    }

    public User(String Id,String name, String email, String phone,String bloodType,String city) {
        this.Id=Id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bloodType=bloodType;
        this.city=city;
    }
}
