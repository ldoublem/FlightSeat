package com.ldoublem.flightseat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ldoublem.flightseatselect.FlightSeatView;

public class MainActivity extends AppCompatActivity {
    FlightSeatView mFlightSeatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFlightSeatView = (FlightSeatView) findViewById(R.id.fsv);
        setTestData();
    }

    public void zoom(View v) {
        mFlightSeatView.startAnim(true);
    }

    public void clear(View v) {
        mFlightSeatView.setEmptySelecting();
    }


    private void setTestData() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 9; j = j + 2) {
                mFlightSeatView.setSeatSelected(j, i);

            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 8; j = j + 2) {
                mFlightSeatView.setSeatSelected(i + 20, j);

            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 8; j = j + 3) {
                mFlightSeatView.setSeatSelected(i + 35, j);

            }
        }


        for (int i = 11; i < 20; i++) {
            for (int j = 0; j < 8; j = j + 4) {
                mFlightSeatView.setSeatSelected(i + 35, j);

            }
        }
        mFlightSeatView.invalidate();


    }


}
