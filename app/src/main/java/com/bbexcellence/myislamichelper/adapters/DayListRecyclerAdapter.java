package com.bbexcellence.myislamichelper.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bbexcellence.myislamichelper.R;
import com.bbexcellence.myislamichelper.models.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DayListRecyclerAdapter extends RecyclerView.Adapter<DayListRecyclerAdapter.DayViewHolder> {

    private final LayoutInflater mInflater;
    ArrayList<Day> mDays = new ArrayList<>();
    Context mContext;

    public DayListRecyclerAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.day_list_item, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        if (mDays != null) {
            holder.updateWithDay(mDays.get(position));
        }
    }

    public void setDays(ArrayList<Day> days){
        mDays = days;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mDays != null) {
            return mDays.size();
        } else {
            return 0;
        }
    }

    public Day getDayAtIndex(int position) {
        return mDays.get(position);
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView dayTextView;
        Button fajrButton;
        Button dhohrButton;
        Button asrButton;
        Button maghrebButton;
        Button ishaaButton;

        public DayViewHolder(View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.day_text_view);
            fajrButton = itemView.findViewById(R.id.fajr_button);
            dhohrButton = itemView.findViewById(R.id.dhohr_button);
            asrButton = itemView.findViewById(R.id.asr_button);
            maghrebButton = itemView.findViewById(R.id.maghreb_button);
            ishaaButton = itemView.findViewById(R.id.ishaa_button);
        }

        public void updateWithDay(Day day) {
            dayTextView.setText(String.format(Locale.getDefault(),"Day %d", day.getDayNumber()));
            updateButtons(day.getDuePrayersCode());
        }

        private void updateButtons(String code) {
            final String FAJR = "1";
            final String DHOHR = "2";
            final String ASR = "3";
            final String MAGHREB = "4";
            final String ISHAA = "5";

            Resources resources = this.fajrButton.getContext().getResources();
            ColorStateList redColor = ColorStateList.valueOf(resources.getColor(R.color.golden));
            int goldenColor = resources.getColor(R.color.darkRed);
            ColorStateList lightGrayColor = ColorStateList.valueOf(resources.getColor(R.color.lightGray));
            int darkGrayColor = resources.getColor(R.color.darkGray);

            if (code.contains(FAJR)) {
                this.fajrButton.setBackgroundTintList(redColor);
                this.fajrButton.setTextColor(goldenColor);
            } else {
                this.fajrButton.setBackgroundTintList(lightGrayColor);
                this.fajrButton.setTextColor(darkGrayColor);
            }

            if (code.contains(DHOHR)) {
                this.dhohrButton.setBackgroundTintList(redColor);
                this.dhohrButton.setTextColor(goldenColor);
            } else {
                this.dhohrButton.setBackgroundTintList(lightGrayColor);
                this.dhohrButton.setTextColor(darkGrayColor);
            }

            if (code.contains(ASR)) {
                this.asrButton.setBackgroundTintList(redColor);
                this.asrButton.setTextColor(goldenColor);
            } else {
                this.asrButton.setBackgroundTintList(lightGrayColor);
                this.asrButton.setTextColor(darkGrayColor);
            }

            if (code.contains(MAGHREB)) {
                this.maghrebButton.setBackgroundTintList(redColor);
                this.maghrebButton.setTextColor(goldenColor);
            } else {
                this.maghrebButton.setBackgroundTintList(lightGrayColor);
                this.maghrebButton.setTextColor(darkGrayColor);
            }

            if (code.contains(ISHAA)) {
                this.ishaaButton.setBackgroundTintList(redColor);
                this.ishaaButton.setTextColor(goldenColor);
            } else {
                this.ishaaButton.setBackgroundTintList(lightGrayColor);
                this.ishaaButton.setTextColor(darkGrayColor);
            }
        }
    }
}
