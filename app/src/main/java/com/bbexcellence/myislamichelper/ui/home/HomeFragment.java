package com.bbexcellence.myislamichelper.ui.home;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bbexcellence.myislamichelper.R;
import com.bbexcellence.myislamichelper.adapters.DayListRecyclerAdapter;
import com.bbexcellence.myislamichelper.fragments.AddDayPrayersDialogFragment;
import com.bbexcellence.myislamichelper.helpers.SwipeToDeleteCallback;
import com.bbexcellence.myislamichelper.models.Day;
import com.bbexcellence.myislamichelper.models.Progress;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment implements OnChartValueSelectedListener, AddDayPrayersDialogFragment.CallbackListener, SwipeToDeleteCallback.SwipeToDeleteCallbackListener {

    private HomeViewModel homeViewModel;
    private Progress mCurrentProgress;
    private PieChart mChart;
    private final String[] parties = new String[]{
            "Done", "Left"
    };
    private static final int[] MY_COLORS = {
            Color.rgb(187, 152, 136), Color.rgb(217, 80, 138), Color.rgb(106, 247, 120),
            Color.rgb(62, 203, 128), Color.rgb(53, 194, 209), Color.rgb(217, 184, 124),
            Color.rgb(217, 184, 162), Color.rgb(191, 134, 134), Color.rgb(179, 48, 80),
            Color.rgb(255, 1, 82), Color.rgb(193, 37, 82), Color.rgb(255, 102, 0),
            Color.rgb(140, 234, 140), Color.rgb(232, 24, 59)
    };
    private Typeface tfLight;
    private Typeface tfRegular;
    private Typeface tfBold;
    private float mPieChartTextSize = 2f;
    private Button mNextPrayerButton;
    private boolean mStartAccomplishingPrayers = false;
    private int mNextDayIndex = 0;
    private DayListRecyclerAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        TextView progressTextView = root.findViewById(R.id.progress_text_view);
        mNextPrayerButton = root.findViewById(R.id.next_prayer_button);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        mAdapter = new DayListRecyclerAdapter(getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(this));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        homeViewModel.getAllDays().observe(this, new Observer<List<Day>>() {
            @Override
            public void onChanged(List<Day> days) {
                mAdapter.setDays(new ArrayList(days));
                if (!days.isEmpty()) {
                    //Integer.parseInt(String.valueOf(days.get(0)).substring(0, 1))
                    mNextDayIndex = days.get(days.size() - 1).getDayNumber();
                    Day currentDay = days.get(0);
                    homeViewModel.updatePrayer(Integer.parseInt(currentDay.getDuePrayersCode()),
                            currentDay.getDayNumber(), mStartAccomplishingPrayers ? 1 : 0);
                    mStartAccomplishingPrayers = true;
                } else {
                    mCurrentProgress = null;
                    homeViewModel.deleteProgress();
                }
            }
        });

        homeViewModel.getProgress().observe(this, new Observer<List<Progress>>() {
            @Override
            public void onChanged(List<Progress> progresses) {
                if (!progresses.isEmpty()) {
                    mCurrentProgress = progresses.get(0);
                    int daysLeft = mCurrentProgress.getDaysLeft();

                    // Updating the number of days left to accomplish
                    if (daysLeft > 1) {
                        progressTextView.setText(mCurrentProgress.getTotalPrayersLeftNumber() + "\nPrayers Left");
                    } else {
                        progressTextView.setText(mCurrentProgress.getTotalPrayersLeftNumber() + "\nPrayers Left");
                    }

                    updateNextPrayer();
                    updatePieChart();
                } else {
                    homeViewModel.createProgress(new Progress(0,
                            0, 0, 0, 0, 0));
                }
            }
        });

        mChart = root.findViewById(R.id.prayers_chart);
        updatePieChart();

        FloatingActionButton fab = root.findViewById(R.id.fab);
        HomeFragment fragment = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStartAccomplishingPrayers = false;
                /*homeViewModel.addDay(new Day(mCurrentProgress == null ? 1 : mCurrentProgress.getCurrentDayIndex() + 1,
                        "234"));*/
                FragmentManager fragmentManager = getChildFragmentManager();
                AddDayPrayersDialogFragment prayerPicker = new AddDayPrayersDialogFragment(fragment, mNextDayIndex + 1);
                prayerPicker.show(fragmentManager, "prayers");

                /*FragmentTransaction transaction = fragmentManager.beginTransaction();
                // For a little polish, specify a transition animation
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                // To make it fullscreen, use the 'content' root view as the container
                // for the fragment, which is always the root view for the activity
                transaction.add(android.R.id.content, prayerPicker).addToBackStack(null).commit();*/

                /*if (mCurrentProgress != null) {
                    homeViewModel.addProgressDay(3);
                } else {
                    homeViewModel.createProgress(new Progress(0,
                            0, 0, 0, 0, 0));
                }*/
            }
        });

        ImageView checkMark = root.findViewById(R.id.check_mark);
        mNextPrayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentProgress.getTotalPrayersLeftNumber() > 0) {
                    mNextPrayerButton.setBackground(getResources().getDrawable(R.drawable.checked_round_button));
                    checkMark.setVisibility(View.VISIBLE);
                }
                mNextPrayerButton.setText("");
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mNextPrayerButton.setBackground(getResources().getDrawable(R.drawable.round_button));
                        checkMark.setVisibility(View.GONE);

                        String currentCode = String.valueOf(mCurrentProgress.getNextPrayersCode());
                        if (currentCode.length() > 1) {
                            homeViewModel.updateDayWithId(mCurrentProgress.getCurrentDayIndex(), Integer.parseInt(currentCode.substring(1)));
                        } else {
                            mStartAccomplishingPrayers = false;
                            int prayersLeft = mCurrentProgress.getTotalPrayersLeftNumber();
                            if (prayersLeft > 0) {
                                homeViewModel.removeDayWithId(mCurrentProgress.getCurrentDayIndex());
                                homeViewModel.accomplishDay();
                                if (prayersLeft == 1) {
                                    mNextDayIndex = 0;
                                }
                            }
                        }
                    }
                }, 500);
            }
        });

        return root;
    }

    private void updateNextPrayer() {
        int newIndex = Integer.parseInt(String.valueOf(mCurrentProgress.getNextPrayersCode()).substring(0, 1)) - 1;
        String next = "";
        if (newIndex != -1) {
            String[] prayers = {"Fajr", "Dhohr", "Asr", "Maghreb", "Ishaa"};
            next = prayers[newIndex];
        }
        mNextPrayerButton.setText(next);
    }

    private void updatePieChart() {
        tfLight = ResourcesCompat.getFont(getContext(), R.font.open_sans_light);
        tfRegular = ResourcesCompat.getFont(getContext(), R.font.open_sans_regular);
        tfBold = ResourcesCompat.getFont(getContext(), R.font.open_sans_extra_bold);

        mChart.setUsePercentValues(true);

        mChart.getDescription().setEnabled(false);
        //mChart.setExtraOffsets(5, 5, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterTextTypeface(tfLight);
        //mChart.setCenterText(generateCenterSpannableText());
        mChart.setDrawCenterText(false);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.TRANSPARENT);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(48f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        mChart.animateY(1400, Easing.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        /*Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);*/
        // Hiding the legend
        mChart.getLegend().setEnabled(false);   // Hide the legend

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTypeface(tfRegular);
        mChart.setEntryLabelTextSize(mPieChartTextSize);
        setData(2, 10);
    }

    /*private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }*/

    private void setData(int count, float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        /*for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * range) + range / 5),
                    parties[i % parties.length],
                    getResources().getDrawable(R.drawable.star)));

        }*/
        entries.add(0, new PieEntry(mCurrentProgress != null ? mCurrentProgress.getTotalPrayersAccomplishedNumber() : 0, parties[0]));
        entries.add(0, new PieEntry(mCurrentProgress != null ? mCurrentProgress.getTotalPrayersLeftNumber() : 0, parties[1]));

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(4f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        //dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : MY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        Collections.shuffle(colors);
        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(mChart));
        mPieChartTextSize = 15f;
        data.setValueTextSize(mPieChartTextSize);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tfRegular);
        mChart.setData(data);

        // Rounded slices
        //toggleCurvedSlices();

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.pie, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionClear:
                mNextDayIndex = 0;
                mCurrentProgress = null;
                homeViewModel.clear();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleCurvedSlices() {
        boolean toSet = !mChart.isDrawRoundedSlicesEnabled() || !mChart.isDrawHoleEnabled();
        mChart.setDrawRoundedSlices(toSet);
        if (toSet && !mChart.isDrawHoleEnabled()) {
            mChart.setDrawHoleEnabled(true);
        }
        if (toSet && mChart.isDrawSlicesUnderHoleEnabled()) {
            mChart.setDrawSlicesUnderHole(false);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        //toggleCurvedSlices();
    }

    @Override
    public void onNothingSelected() {
    }

    @Override
    public void onDataReceived(String code, int nextIndex) {
        mNextDayIndex = nextIndex;
        mStartAccomplishingPrayers = false;
        homeViewModel.addDay(new Day(nextIndex,
                code));

        if (mCurrentProgress != null || nextIndex > 1) {
            homeViewModel.addProgressDay(code.length());
        } else {
            homeViewModel.createProgress(new Progress(0,
                    0, 0, 0, 0, 0));
        }
    }

    @Override
    public void onItemViewSwiped(int position) {
        Day dayToRemove = mAdapter.getDayAtIndex(position);
        mStartAccomplishingPrayers = false;
        int daysLeft = mCurrentProgress.getDaysLeft();
        homeViewModel.removeDayWithId(dayToRemove.getDayNumber());
        homeViewModel.deleteDayFromProgress(dayToRemove.getDuePrayersCode().length());
        if (daysLeft == 1) {
            mNextDayIndex = 0;
        }
    }
}