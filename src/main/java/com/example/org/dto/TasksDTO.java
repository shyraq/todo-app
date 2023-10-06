package com.example.org.dto;

import com.example.org.models.Month;
import jakarta.validation.constraints.NotNull;



public class TasksDTO {

    private int id;

    @NotNull(message = "Задача не должна быть пустой!")
    private String description;


    @NotNull(message = "День не должна быть пустой!")
    private String day;

    @NotNull
    private String year;


    private boolean completed;

    private Month month;


    public TasksDTO(String description, String day, Month month, String year) {
        this.description = description;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public TasksDTO() {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
