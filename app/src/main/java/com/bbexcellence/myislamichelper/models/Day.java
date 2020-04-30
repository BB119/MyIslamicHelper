package com.bbexcellence.myislamichelper.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "day_table")
public class Day {
    //@PrimaryKey(autoGenerate = true)
    //public Integer id;

    @PrimaryKey
    private int dayNumber;

    @ColumnInfo(name = "due_prayers_code")
    private String duePrayersCode; // 123 means prayers number 1, 2 and 3; 45 means prayers number 4 and 5

    public Day(int dayNumber, String duePrayersCode) {
        this.dayNumber = dayNumber;
        this.duePrayersCode = duePrayersCode;
    }
    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getDuePrayersCode() {
        return duePrayersCode;
    }

    public void setDuePrayersCode(String duePrayersCode) {
        this.duePrayersCode = duePrayersCode;
    }
}
