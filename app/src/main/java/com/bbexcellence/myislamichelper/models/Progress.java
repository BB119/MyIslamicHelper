package com.bbexcellence.myislamichelper.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "progress_table")
public class Progress {
    @PrimaryKey
    public int id = 1;

    @ColumnInfo(name = "days_accomplished")
    private int mDaysAccomplished;

    @ColumnInfo(name = "days_left")
    private int mDaysLeft;

    @ColumnInfo(name = "next_prayers_code")
    private int mNextPrayersCode;

    @ColumnInfo(name = "current_day_index")
    private int mCurrentDayIndex;

    @ColumnInfo(name = "total_prayers_left_number")
    private int mTotalPrayersLeftNumber;

    @ColumnInfo(name = "total_prayers_accomplished_number")
    private int mTotalPrayersAccomplishedNumber;

    public Progress(int daysAccomplished, int daysLeft, int nextPrayersCode,
                    int currentDayIndex, int totalPrayersLeftNumber, int totalPrayersAccomplishedNumber) {
        mDaysAccomplished = daysAccomplished;
        mDaysLeft = daysLeft;
        mNextPrayersCode = nextPrayersCode;
        mCurrentDayIndex = currentDayIndex;
        mTotalPrayersLeftNumber = totalPrayersLeftNumber;
        mTotalPrayersAccomplishedNumber = totalPrayersAccomplishedNumber;
    }

    public int getDaysAccomplished() {
        return mDaysAccomplished;
    }

    public void setDaysAccomplished(int daysAccomplished) {
        mDaysAccomplished = daysAccomplished;
    }

    public int getDaysLeft() {
        return mDaysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        mDaysLeft = daysLeft;
    }

    public int getNextPrayersCode() {
        return mNextPrayersCode;
    }

    public void setNextPrayersCode(int nextPrayersCode) {
        mNextPrayersCode = nextPrayersCode;
    }

    public int getCurrentDayIndex() {
        return mCurrentDayIndex;
    }

    public void setCurrentDayIndex(int currentDayIndex) {
        mCurrentDayIndex = currentDayIndex;
    }

    public int getTotalPrayersAccomplishedNumber() {
        return mTotalPrayersAccomplishedNumber;
    }

    public void setTotalPrayersAccomplishedNumber(int totalPrayersAccomplishedNumber) {
        mTotalPrayersAccomplishedNumber = totalPrayersAccomplishedNumber;
    }

    public int getTotalPrayersLeftNumber() {
        return mTotalPrayersLeftNumber;
    }

    public void setTotalPrayersLeftNumber(int totalPrayersLeftNumber) {
        mTotalPrayersLeftNumber = totalPrayersLeftNumber;
    }
}
