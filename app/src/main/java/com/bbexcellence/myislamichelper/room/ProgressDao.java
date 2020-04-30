package com.bbexcellence.myislamichelper.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.bbexcellence.myislamichelper.models.Progress;

import java.util.List;

@Dao
public interface ProgressDao {
    // GET
    @Query("SELECT * FROM progress_table")
    LiveData<List<Progress>> getProgress();

    // ADD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProgress(Progress newProgress);

    // UPDATE
    @Query("UPDATE progress_table SET days_left = days_left + 1, " +
            "total_prayers_left_number = total_prayers_left_number + :prayersNumber")
    void dayAdded(int prayersNumber);

    @Query("UPDATE progress_table SET days_accomplished = days_accomplished + 1, days_left = days_left - 1" +
            ", total_prayers_left_number = total_prayers_left_number - 1" +
            ", total_prayers_accomplished_number = total_prayers_accomplished_number + 1")
    void dayAccomplished();

    @Query("UPDATE progress_table SET current_day_index = :dayIndex, next_prayers_code = :next" +
            ", total_prayers_left_number = total_prayers_left_number - :accomplishedPrayers" +
            ", total_prayers_accomplished_number = total_prayers_accomplished_number + :accomplishedPrayers")
    void updateNextPrayer(int next, int dayIndex, int accomplishedPrayers);

    // DELETE
    @Query("DELETE FROM progress_table")
    void deleteAll();

    @Query("UPDATE progress_table SET total_prayers_left_number = total_prayers_left_number - :prayersNumber, days_left = days_left - 1")
    void dayDeleted(int prayersNumber);
}
