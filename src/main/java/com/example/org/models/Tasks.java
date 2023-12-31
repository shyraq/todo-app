package com.example.org.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


@Entity
@Table(name = "task")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    @NotNull(message = "Задача не должна быть пустой!")
    private String description;

    @Column(name = "day")
    @NotNull(message = "День не должна быть пустой!")
    private String day;

    @ManyToOne
    @JoinColumn(name = "creator", referencedColumnName = "username")
    private User user;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @Column(name = "completed")
    private boolean completed;

    @JoinColumn(name = "month", referencedColumnName = "month")
    @ManyToOne
    private Month month;

    @Column(name = "year")
    private String year;

    public Tasks(String description, String day, Month month) {
        this.description = description;
        this.day = day;
        this.month = month;
    }

    public Tasks() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
