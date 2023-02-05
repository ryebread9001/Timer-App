package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.DatePickerDialog;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.os.Bundle;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView txtDay, txtHour, txtMinute, txtSecond;
    private int dayDivisor = 24*60*60*1000;
    private int hourDivisor = 60*60*1000;
    private int minuteDivisor = 60*1000;
    private int secondDivisor = 1000;

    private Button pickDateBtn;
    private Handler handler;
    private Runnable runnable;

    private String dateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pickDateBtn = findViewById(R.id.idBtnPickDate);
        txtDay = (TextView) findViewById(R.id.txtDay);
        txtHour = (TextView) findViewById(R.id.txtHour);
        txtMinute = (TextView) findViewById(R.id.txtMinute);
        txtSecond = (TextView) findViewById(R.id.txtSecond);

        pickDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateString = (year + "-" + (monthOfYear+1) +"-" + dayOfMonth);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        countDown();
    }

    public void countDown() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd");
                    Date futureDate = dateFormat.parse(dateString);
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        txtDay.setVisibility(View.VISIBLE);
                        txtHour.setVisibility(View.VISIBLE);
                        txtMinute.setVisibility(View.VISIBLE);
                        txtSecond.setVisibility(View.VISIBLE);
                        long diff = futureDate.getTime() - currentDate.getTime();
                        long days = diff / dayDivisor;
                        diff -= days * dayDivisor;
                        long hours = diff / hourDivisor;
                        diff -= hours * hourDivisor;
                        long minutes = diff / minuteDivisor;
                        diff -= minutes * minuteDivisor;
                        long seconds = diff / secondDivisor;
                        txtDay.setText(String.valueOf(days));
                        txtHour.setText(String.valueOf(hours));
                        txtMinute.setText(String.valueOf(minutes));
                        txtSecond.setText(String.valueOf(seconds));
                    } else {
                        txtDay.setText("Countdown Complete!");
                        textViewGone();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    public void textViewGone() {
        //txtDay.setVisibility(View.GONE);
        txtHour.setVisibility(View.GONE);
        txtMinute.setVisibility(View.GONE);
        txtSecond.setVisibility(View.GONE);
    }
}