package com.example.org.dto;

import jakarta.validation.constraints.NotNull;

public class MonthDTO {

    @NotNull
    private int id;

    private String month;

    public MonthDTO() {
    }

    public MonthDTO(String month) {
        this.month = month;
    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
