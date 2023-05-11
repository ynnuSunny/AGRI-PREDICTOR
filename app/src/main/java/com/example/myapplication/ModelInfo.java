package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ModelInfo extends AppCompatActivity {
    private TextView dateAndTime,acc,cls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_info);

        String date = getIntent().getStringExtra("date");
        String cl = getIntent().getStringExtra("class");
        String ac = getIntent().getStringExtra("acc");

        dateAndTime = findViewById(R.id.textView3);
        dateAndTime.setText("Date and Time: \n"+date);

        cls = findViewById(R.id.textView8);
        cls.setText("Predicted Class: "+cl);

        acc = findViewById(R.id.textView7);
        acc.setText("Predicted Class Accuracy: "+ac);

    }
}