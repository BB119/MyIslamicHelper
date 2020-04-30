package com.bbexcellence.myislamichelper.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.bbexcellence.myislamichelper.R;
import com.bbexcellence.myislamichelper.models.Day;
import com.bbexcellence.myislamichelper.models.Progress;
import com.bbexcellence.myislamichelper.ui.home.HomeViewModel;

import java.util.Arrays;
import java.util.List;

public class AddDayPrayersDialogFragment extends DialogFragment{
    private String mPrayersCode = "";
    private CheckBox mFajrCheckbox;
    private CheckBox mDhohrCheckbox;
    private CheckBox mAsrCheckbox;
    private CheckBox mMaghrebCheckbox;
    private CheckBox mIshaaCheckbox;
    private Button mSaveContinueButton;
    private Button mSaveFinishButton;
    private Button mCancelButton;
    private CallbackListener mCallbackListener;
    private int mNextIndex;

    public interface CallbackListener {
        void onDataReceived(String code, int nextIndex);
    }

    public AddDayPrayersDialogFragment(CallbackListener callbackListener, int nextIndex) {
        super();
        mCallbackListener = callbackListener;
        mNextIndex = nextIndex;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout to use as dialog or embedded fragment

        View root = inflater.inflate(R.layout.dialog_fragment_add_day_prayers, container, false);

        // Checkboxes
        mFajrCheckbox = root.findViewById(R.id.checkbox_fajr);
        mFajrCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onCheckboxClicked(mFajrCheckbox);
            }
        });

        mDhohrCheckbox = root.findViewById(R.id.checkbox_dhohr);
        mDhohrCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onCheckboxClicked(mDhohrCheckbox);
            }
        });

        mAsrCheckbox = root.findViewById(R.id.checkbox_asr);
        mAsrCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onCheckboxClicked(mAsrCheckbox);
            }
        });

        mMaghrebCheckbox = root.findViewById(R.id.checkbox_maghreb);
        mMaghrebCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onCheckboxClicked(mMaghrebCheckbox);
            }
        });

        mIshaaCheckbox = root.findViewById(R.id.checkbox_ishaa);
        mIshaaCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onCheckboxClicked(mIshaaCheckbox);
            }
        });

        // Buttons
        mSaveFinishButton = root.findViewById(R.id.save_button);
        mSaveFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndFinish(root);
            }
        });

        mCancelButton = root.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return root;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void saveAndFinish(View rootView) {
        if (!mPrayersCode.isEmpty()) {
            // convert input string to char array
            char[] tempArray = mPrayersCode.toCharArray();

            // sort tempArray
            Arrays.sort(tempArray);

            // return new sorted string
            mPrayersCode = new String(tempArray);

            mCallbackListener.onDataReceived(mPrayersCode, mNextIndex);
            dismiss();
        } else {
            Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
            rootView.startAnimation(shake); // starts animation
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_fajr:
                if (checked) {
                   mPrayersCode += "1";
                } else {
                    mPrayersCode = mPrayersCode.replace("1","");
                }
                break;
            case R.id.checkbox_dhohr:
                if (checked) {
                    mPrayersCode += "2";
                } else {
                    mPrayersCode = mPrayersCode.replace("2","");
                }
                break;
            case R.id.checkbox_asr:
                if (checked) {
                    mPrayersCode += "3";
                } else {
                    mPrayersCode = mPrayersCode.replace("3","");
                }
                break;
            case R.id.checkbox_maghreb:
                if (checked) {
                    mPrayersCode += "4";
                } else {
                    mPrayersCode = mPrayersCode.replace("4","");
                }
                break;
            case R.id.checkbox_ishaa:
                if (checked) {
                    mPrayersCode += "5";
                } else {
                    mPrayersCode = mPrayersCode.replace("5","");
                }
                break;
        }
    }
}