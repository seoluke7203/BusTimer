package com.example.bustimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static java.lang.Integer.valueOf;

public class MainActivity extends AppCompatActivity {

    TextView txt_next_bus_time;
    TextView txt_left_time;

    TextView txt_next_bus_time_n;
    TextView txt_left_time_n;

    TextView txt_info;

    TextView txt_info_n;

    ImageView logo;

    String line;
    String str_left_time;
    int time_diff;
    int holiday_flag;
    Button btn_week_sch;
    Button btn_weekend_sch;



    private SwipeRefreshLayout refreshLayout = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        CheckBox checkBox = (CheckBox) findViewById(R.id.chk_holiday);
        checkBox.setOnClickListener(new CheckBox.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "아래로 당겨서 새로고침", Toast.LENGTH_SHORT).show();
                if (((CheckBox)v).isChecked()) {
                    holiday_flag = 1;
                } else {
                    holiday_flag = 0;
                }
            }

        });

        ImageView logo = (ImageView) findViewById(R.id.img_logo);
        logo.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "공식 어플이 아닙니다.", Toast.LENGTH_SHORT).show();}
        });



        function2();
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                function2();
                refreshLayout.setRefreshing(false);
            }
        });


        btn_week_sch = (Button) findViewById(R.id.btn_week_sch);
        btn_week_sch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), Scheduler.class);
                startActivity(intent);
            }
        });


        btn_weekend_sch = (Button) findViewById(R.id.btn_weekend_sch);
        btn_weekend_sch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View x){
                Intent intent2 = new Intent(getApplicationContext(), Schduler_end.class);
                startActivity(intent2);
            }
        });


    }


    public void function2() {
        Calendar curr_time = Calendar.getInstance();
        SimpleDateFormat hhmm = new SimpleDateFormat("HH:mm");
        Calendar comp_time = Calendar.getInstance();


        try {
            BufferedReader bufferedReader;
            txt_info = (TextView) findViewById(R.id.txt_info);
            txt_info_n = (TextView) findViewById(R.id.txt_info_n);
            if ((curr_time.get(Calendar.DAY_OF_WEEK) != 6 || curr_time.get(Calendar.DAY_OF_WEEK) != 7) && holiday_flag == 0) {

                txt_info.setText("다음 버스 오는 시간 (평일)");
                txt_info_n.setText("그 다음 버스 오는 시간 (평일)");
                bufferedReader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.bus_sch)));

            } else{

                txt_info.setText("다음 버스 오는 시간 (주말)");
                txt_info_n.setText("그 다음 버스 오는 시간 (주말)");
                bufferedReader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.bus_sch_end)));
            }

            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(":");
                comp_time.set(Calendar.HOUR_OF_DAY, Integer.parseInt(parts[0]));
                comp_time.set(Calendar.MINUTE, Integer.parseInt(parts[1]));

                int comp_hour = comp_time.get(Calendar.HOUR_OF_DAY);
                int comp_min  = comp_time.get(Calendar.MINUTE);

                txt_next_bus_time = (TextView) findViewById(R.id.txt_time);

                String init_time = String.format("%02d:%02d", comp_hour, comp_min);
                txt_next_bus_time.setText(init_time);

                if (curr_time.before(comp_time)) {
                int next_hour_time = valueOf(comp_time.get(Calendar.HOUR_OF_DAY));
                int curr_min_time = valueOf(curr_time.get(Calendar.MINUTE));
                int next_min_time = valueOf(comp_time.get(Calendar.MINUTE));
                String next_time = String.format("%02d:%02d", next_hour_time, next_min_time);

                txt_next_bus_time.setText(next_time);
                txt_left_time = (TextView) findViewById(R.id.txt_left_time);

                if (next_min_time - curr_min_time > 0){
                    time_diff = next_min_time - curr_min_time;
                } else {
                    time_diff = curr_min_time - next_min_time;
                }

                str_left_time = String.format("%02d", time_diff);
                txt_left_time.setText(str_left_time + " 분");

                break;
                    }
                }
            } catch(Exception c){
                c.printStackTrace();

            }
        }
    }

