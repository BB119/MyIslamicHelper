package com.bbexcellence.myislamichelper.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bbexcellence.myislamichelper.models.Day;

import java.util.List;

@Dao
public interface DayDao {
    // GET
    @Query("SELECT * FROM day_table ORDER BY dayNumber ASC")
    LiveData<List<Day>> getOrderedDays();

    @Query("SELECT * FROM day_table WHERE dayNumber = :dayId")
    Day getDayWithId(int dayId);

    // ADD
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addDay(Day newDay);

    // UPDATE
    @Query("UPDATE day_table SET due_prayers_code = :newCode WHERE dayNumber = :dayId")
    void updateDayWithId(int dayId, int newCode);

    // DELETE
    @Query("DELETE FROM day_table")
    void deleteAll();

    @Delete
    void deleteDay(Day day);

    @Query("DELETE FROM day_table WHERE dayNumber = :dayId")
    void deleteDayWithId(int dayId);
}
