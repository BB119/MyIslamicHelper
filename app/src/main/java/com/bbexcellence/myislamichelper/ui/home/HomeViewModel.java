package com.bbexcellence.myislamichelper.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bbexcellence.myislamichelper.models.Day;
import com.bbexcellence.myislamichelper.models.Progress;
import com.bbexcellence.myislamichelper.repositories.DayRepository;
import com.bbexcellence.myislamichelper.room.DayRoomDatabase;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private DayRepository mRepository;

    private LiveData<List<Day>> mAllDays;
    private LiveData<List<Progress>> mCurrentProgress;

    public HomeViewModel (Application application) {
        super(application);
        mRepository = new DayRepository(application);
        mAllDays = mRepository.getAllDays();
        mCurrentProgress = mRepository.getCurrentProgress();
    }

    // region GET
    LiveData<List<Day>> getAllDays() { return mAllDays; }

    public Day getDay(int dayId) {
        return mRepository.getDay(dayId);
    }

    public LiveData<List<Progress>> getProgress() {
        return mCurrentProgress;
    }
    // endregion

    // region ADD
    public void addDay(Day day) {
        mRepository.addDay(day);
    }

    public void createProgress(Progress progress) {
        mRepository.addProgress(progress);
    }
    //endregion

    // region UPDATE
    public void updateDayWithId(int id, int code) {
        mRepository.updateDayWithId(id, code);
    }

    public void addProgressDay(int prayersNumber) {
        mRepository.addProgressDay(prayersNumber);
    }

    public void accomplishDay() {
        mRepository.accomplishDay();
    }

    public void updatePrayer(int code, int dayIndex, int accomplishedPrayers) {
        mRepository.updateNextPrayer(code, dayIndex, accomplishedPrayers);
    }
    //endregion

    // region DELETE
    public void clear() {
        mRepository.clear();
    }

    public void removeDay(Day day) {
        mRepository.removeDay(day);
    }

    public void removeDayWithId(int dayId) {
        mRepository.removeDayWithId(dayId);
    }

    public void deleteProgress() {
        mRepository.deleteProgress();
    }

    public void deleteDayFromProgress(int dayPrayers) {
        mRepository.removeDayFromProgress(dayPrayers);
    }
    // endregion
}