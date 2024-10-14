package com.example.backend.login;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;

import jakarta.persistence.*;

// Marking this class as an entity to be managed by JPA
@Entity
// Specifying the table name in the database
@Table(name = "users")
public class User {
    // Marking this field as the primary key
    @Id
    // Specifying the primary key generation strategy
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Defining a column with constraints: not null, unique, and max length of 45
    @Column(nullable = false, unique = true, length = 45)
    private String email;

    // Defining a column with constraints: not null and max length of 64
    @Column(nullable = false, length = 64)
    private String password;

    // Defining a column with constraints: not null, max length of 20, and custom column name
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    // Defining a column with constraints: not null, max length of 20, and custom column name
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    // Getter method for id
    public Long getId() {
        return id;
    }

    // Setter method for id
    public void setId(Long id) {
        this.id = id;
    }

    // Getter method for email
    public String getEmail() {
        return email;
    }

    // Setter method for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter method for password
    public String getPassword() {
        return password;
    }

    // Setter method for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter method for firstName
    public String getFirstName() {
        return firstName;
    }

    // Setter method for firstName
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter method for lastName
    public String getLastName() {
        return lastName;
    }

    // Setter method for lastName
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
