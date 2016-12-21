package com.luxoft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 21.12.2016.
 */
@Entity
@Table(name="customdate")
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

    public CustomDate() {}

    public CustomDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;

        LocalDate localdate = LocalDate.of(year, month, day);
        Instant instant = Instant.from(localdate.atStartOfDay(ZoneId.systemDefault()));
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

    public Date getDate() {
        return date;
    }
}