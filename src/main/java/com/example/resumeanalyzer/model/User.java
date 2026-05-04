package com.example.resumeanalyzer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
@Entity

public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;

    // getters & setters
}