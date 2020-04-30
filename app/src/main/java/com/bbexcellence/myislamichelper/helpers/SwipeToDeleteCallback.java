package com.bbexcellence.myislamichelper.helpers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bbexcellence.myislamichelper.R;
import com.bbexcellence.myislamichelper.adapters.DayListRecyclerAdapter;
import com.bbexcellence.myislamichelper.models.Day;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    //private DayListRecyclerAdapter mAdapter;
    private SwipeToDeleteCallbackListener mSwipeToDeleteCallback;

    public interface SwipeToDeleteCallbackListener {
        void onItemViewSwiped(int position);
    }

    public SwipeToDeleteCallback(SwipeToDeleteCallbackListener callback) {
        super(0, ItemTouchHelper.LEFT);
        mSwipeToDeleteCallback = callback;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mSwipeToDeleteCallback.onItemViewSwiped(position);
    }
}
