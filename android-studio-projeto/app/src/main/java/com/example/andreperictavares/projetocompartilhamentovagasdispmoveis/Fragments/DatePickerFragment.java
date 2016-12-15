package com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.Entities.Horario;
import com.example.andreperictavares.projetocompartilhamentovagasdispmoveis.R;

import me.tittojose.www.timerangepicker_library.TimeRangePickerDialog;

public class DatePickerFragment extends Fragment implements TimeRangePickerDialog.OnTimeRangeSelectedListener {
    Button selectTimeRangeButton;
    TextView timeRangeSelectedTextView;
    public static final String TIMERANGEPICKER_TAG = "timerangepicker";


    OnHeadlineSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(Horario horarioInicio, Horario horarioFim);
    }

    public DatePickerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_date_picker, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        selectTimeRangeButton = (Button) getActivity().findViewById(R.id.bSelectTimeRangeFragment);
        timeRangeSelectedTextView = (TextView) getActivity().findViewById(R.id.tvSelectedTimeRangeFragment);
        if (savedInstanceState != null) {
            TimeRangePickerDialog tpd = (TimeRangePickerDialog) getActivity().getSupportFragmentManager()
                    .findFragmentByTag(TIMERANGEPICKER_TAG);
            if (tpd != null) {
                tpd.setOnTimeRangeSetListener(this);
            }
        }
        selectTimeRangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TimeRangePickerDialog timePickerDialog = TimeRangePickerDialog.newInstance(
                        DatePickerFragment.this, false);
                timePickerDialog.show(getActivity().getSupportFragmentManager(), TIMERANGEPICKER_TAG);
            }
        });
    }

    @Override
    public void onTimeRangeSelected(int startHour, int startMin, int endHour, int endMin) {
        String startTime = startHour + " : " + startMin;
        String endTime = endHour + " : " + endMin;
        timeRangeSelectedTextView.setText(startTime + "\n" + endTime);
    }

        // TODO: refactorar (deprecated)
        // http://stackoverflow.com/questions/32083053/android-fragment-onattach-deprecated
        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                mCallback = (OnHeadlineSelectedListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnHeadlineSelectedListener");
            }
        }

}
