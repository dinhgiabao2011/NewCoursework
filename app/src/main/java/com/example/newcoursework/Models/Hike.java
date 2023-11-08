package com.example.newcoursework.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "hikes")
public class Hike implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    public String hikeName;
    public String hikeLocation;
    public String hikeDate;
    public Boolean isParking;
    public Float hikeLength;
    public Integer hikeLevel;
    public String hikeDescription;
}