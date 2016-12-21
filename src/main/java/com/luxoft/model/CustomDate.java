package com.luxoft.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by user on 21.12.2016.
 */
@Entity
public class CustomDate {
    @Id
    @GeneratedValue
    private Long id;
    private int year;
    private int month;
    private int day;

    public CustomDate() {}

    public CustomDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public Long getId() {
        return id;
    }
}
