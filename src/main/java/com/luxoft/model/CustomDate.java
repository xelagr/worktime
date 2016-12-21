package com.luxoft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by user on 21.12.2016.
 */
@Entity
@Table(name = "customdate")
public class CustomDate {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;
    @Transient
    private int year;
    @Transient
    private int month;
    @Transient
    private int day;
    @JsonIgnore
    private Date date;

    protected CustomDate() {
    }

    public CustomDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;

        LocalDate localdate = LocalDate.of(year, month, day);
        Instant instant = Instant.from(localdate.atStartOfDay(ZoneId.systemDefault()));
        this.date = Date.from(instant);
    }

    public CustomDate(LocalDate localDate) {
        this.year = localDate.getYear();
        this.month = localDate.getMonth().getValue();
        this.day = localDate.getDayOfMonth();

        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        this.date = Date.from(instant);
    }

    public int getYear() {
        LocalDate localdate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localdate.getYear();
    }

    public int getMonth() {
        LocalDate localdate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localdate.getMonthValue();
    }

    public int getDay() {
        LocalDate localdate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localdate.getDayOfMonth();
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CustomDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}