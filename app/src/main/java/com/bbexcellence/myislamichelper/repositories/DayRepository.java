package com.bbexcellence.myislamichelper.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import com.bbexcellence.myislamichelper.models.Day;
import com.bbexcellence.myislamichelper.models.Progress;
import com.bbexcellence.myislamichelper.room.DayDao;
import com.bbexcellence.myislamichelper.room.DayRoomDatabase;
import com.bbexcellence.myislamichelper.room.ProgressDao;

import java.util.List;

public class DayRepository {
    private DayDao mWordDao;
    private ProgressDao mProgressDao;
    private LiveData<List<Progress>> mCurrentProgress;
    private LiveData<List<Day>> mAllDays;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public DayRepository(Application application) {
        DayRoomDatabase db = DayRoomDatabase.getDatabase(application);
        mWordDao = db.dayDao();
        mProgressDao = db.progressDao();

        mAllDays = mWordDao.getOrderedDays();
        mCurrentProgress = mProgressDao.getProgress();
    }

    // region GET
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Day>> getAllDays() {
        return mAllDays;
    }

    public Day getDay(int dayId) {
        return mWordDao.getDayWithId(dayId);
    }

    public LiveData<List<Progress>> getCurrentProgress() {
        return mCurrentProgress;
    }

    // endregion

    // region ADD
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void addDay(Day day) {
        DayRoomDatabase.databaseWriteExecutor.execute(() ->
            mWordDao.addDay(day)
        );
    }

    public void addProgress(Progress progress) {
        DayRoomDatabase.databaseWriteExecutor.execute(() ->
                mProgressDao.addProgress(progress)
        );
    }
    //endregion

    // region UPDATE
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void updateDayWithId(int id, int code) {
        DayRoomDatabase.databaseWriteExecutor.execute(() ->
            mWordDao.updateDayWithId(id, code)
        );
    }

    public void addProgressDay(int prayersNumber) {
        DayRoomDatabase.databaseWriteExecutor.execute(() ->
                mProgressDao.dayAdded(prayersNumber)
        );
    }

    public void accomplishDay() {
        DayRoomDatabase.databaseWriteExecutor.execute(() ->
                mProgressDao.dayAccomplished()
        );
    }

    public void updateNextPrayer(int prayerCode, int dayIndex, int accomplishedPrayers) {
        DayRoomDatabase.databaseWriteExecutor.execute(() ->
                mProgressDao.updateNextPrayer(prayerCode, dayIndex, accomplishedPrayers)
        );
    }
    //endregion

    // region DELETE
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void clear() {
        DayRoomDatabase.databaseWriteExecutor.execute(() ->
                mWordDao.deleteAll()
        );
    }

    public void removeDay(Day day) {
        DayRoomDatabase.databaseWriteExecutor.execute(() ->
                mWordDao.deleteDay(day)
        );
    }

    public void removeDayWithId(int dayId) {
        DayRoomDatabase.databaseWriteExecutor.execute(() ->
                mWordDao.deleteDayWithId(dayId)
        );
    }

    public void deleteProgress() {
        DayRoomDatabase.databaseWriteExecutor.execute(() ->
                mProgressDao.deleteAll()
        );
    }

    public void removeDayFromProgress(int dayPrayers) {
        DayRoomDatabase.databaseWriteExecutor.execute(() ->
                mProgressDao.dayDeleted(dayPrayers)
        );
    }
    //endregion
}
