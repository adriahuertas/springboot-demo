package com.example.springbootdemo.student;

public class StudentUpdateDTO {
    private String name;
    private String email;

    public StudentUpdateDTO() {
        // Constructor vacío
    }

    public StudentUpdateDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
