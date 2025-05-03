package com.example.app_foodstore.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_foodstore.R;

import java.util.Calendar;

public class ReservateScreenActivity extends AppCompatActivity {
    Button btnSelectDate, btnSelectTime;
    EditText txtDate, txtTime, txtNumberOfGuest;
    private int mYear, mMonth, mDay, mHour, mMinute;
    ImageView imgAdd, imgMinus;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservate_screen);

        AnhXa();
    }

    private void AnhXa() {
        setupBtnDateTime();
        setupBtnAdjust();
    }

    private void setupBtnAdjust() {
        imgAdd = findViewById(R.id.reservateScreen_imgAdd);
        imgMinus = findViewById(R.id.reservateScreen_imgMinus);
        txtNumberOfGuest = findViewById(R.id.reservateSceen_et_numberOfGuest);
        imgAdd.setOnClickListener(v -> {
            if (txtNumberOfGuest.getText().toString().isEmpty()) {
                txtNumberOfGuest.setText("1");
            }
            int currentNumber = Integer.parseInt(txtNumberOfGuest.getText().toString());
            currentNumber++;
            txtNumberOfGuest.setText(String.valueOf(currentNumber));
        });
        imgMinus.setOnClickListener(v -> {
            if (txtNumberOfGuest.getText().toString().isEmpty()) {
                txtNumberOfGuest.setText("1");
            }
            int currentNumber = Integer.parseInt(txtNumberOfGuest.getText().toString());
            if (currentNumber > 1) {
                currentNumber--;
                txtNumberOfGuest.setText(String.valueOf(currentNumber));
            }
        });
    }

    private void setupBtnDateTime() {
        btnSelectDate = findViewById(R.id.reservateScreen_btn_selectDate);
        btnSelectTime = findViewById(R.id.reservateScreen_btn_selectTime);
        txtDate = findViewById(R.id.reservateSceen_et_date);
        txtTime = findViewById(R.id.reservateSceen_et_time);
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        btnSelectDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    R.style.MyDatePickerDialogTheme,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {


                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        });
        btnSelectTime.setOnClickListener(v ->
        {
            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    R.style.MyDatePickerDialogTheme,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        });

    }
}
