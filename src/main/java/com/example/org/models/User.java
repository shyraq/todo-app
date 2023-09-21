package com.example.org.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Логин не должен быть пустым!")
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    @NotNull(message = "Пароль не должен быть пустым!")
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
