package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

   private Button goFertilizer,goPesticide,goCrop,goFire;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goFertilizer = findViewById(R.id.goFertilizer);
        goFertilizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fertilizer.class);
                startActivity(intent);
                //System.out.println("Button is Click");
            }
        });

        goPesticide = findViewById(R.id.goPesticide);
        goPesticide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Pesticide.class);
                startActivity(intent);
                //System.out.println("Button is Click");
            }
        });

        goCrop = findViewById(R.id.goCrop);
        goCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Crop.class);
                startActivity(intent);
                //System.out.println("Button is Click");
            }
        });

//        goFire = findViewById(R.id.goFire);
//        goFire.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Fire.class);
//                startActivity(intent);
//                //System.out.println("Button is Click");
//            }
//        });
    }
}