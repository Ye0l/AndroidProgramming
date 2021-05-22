package com.example.typetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    String sDate, sInt, sStr;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = findViewById(R.id.calendarView);
        EditText etDate = findViewById(R.id.etDate);
        EditText etInteger = findViewById(R.id.etInteger);
        EditText etString = findViewById(R.id.etString);
        Button btnSend = findViewById(R.id.btnSend);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                etDate.setText(simpleDateFormat.format(new Date(year-1900,month,dayOfMonth)));
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sDate = etDate.getText().toString();
                sInt = etInteger.getText().toString();
                sStr = etString.getText().toString();
                dataInsert();
                Toast.makeText(MainActivity.this, sDate + "send.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void dataInsert() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL setURL = new URL("http://10.0.2.2/typeInsert.php/");
                    HttpURLConnection http;
                    http = (HttpURLConnection) setURL.openConnection();
                    http.setDefaultUseCaches(false);
                    http.setDoInput(true);
                    http.setRequestMethod("POST");
                    http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("data").append("=").append(sDate).append("/").append(sInt).append("/").append(sStr);
                    OutputStreamWriter
                            outStream = new OutputStreamWriter(http.getOutputStream(), "euc-kr");
                    outStream.write(buffer.toString());
                    outStream.flush();
                    InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "euc-kr");
                    final BufferedReader reader = new BufferedReader(tmp);
                    while(reader.readLine() != null) {
                        System.out.println("로그:" + reader.readLine());
                    }
                }
                catch (Exception e) {
                    Log.e("", "에러", e);
                }
            }
        }.start();
    }
}