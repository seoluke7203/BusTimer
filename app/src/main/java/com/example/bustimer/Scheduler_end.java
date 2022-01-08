package com.example.bustimer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Scheduler_end extends AppCompatActivity {

    BufferedReader bR = null;
    String bus_date;
    int i = 0;
    Button btn_prev;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<String> stringArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);
        read(bus_date);
//        System.out.println("asd");

        btn_prev = (Button) findViewById(R.id.btn_prev);
        btn_prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager((this)));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));
        mAdapter = new MainAdapter(stringArray);
        recyclerView.setAdapter(mAdapter);
    }

    public void read(String fname) {

        Scanner scanner = new Scanner(new InputStreamReader(getResources().openRawResource(R.raw.bus_sch_end)));
//        List<String[]> collection = new ArrayList<String[]>();
        stringArray = new ArrayList<String>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] myList = line.split("\n");
            for (i = 0; i < myList.length; i++) {
                stringArray.add(myList[i]);
//                System.out.println(myList[i]);
            }

        }



    }
}
