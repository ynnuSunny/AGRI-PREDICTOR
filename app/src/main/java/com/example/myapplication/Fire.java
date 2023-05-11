package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Fire extends AppCompatActivity {

    DatabaseReference databaseReference ;

    Button button;
    TextView textView;
    public String npk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire);
        FirebaseApp.initializeApp(this);


        button = findViewById(R.id.button3);
        textView = findViewById(R.id.textView2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("NPK");

                myRef.child("n").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                            textView.setText("Error");
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            npk = String.valueOf(task.getResult().getValue());

                        }
                    }
                });

                myRef.child("p").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                            textView.setText("Error");
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            npk += " , "+ String.valueOf(task.getResult().getValue());

                        }
                    }
                });

                myRef.child("k").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                            textView.setText("Error");
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            npk += " , "+ String.valueOf(task.getResult().getValue());
                            textView.setText(npk);

                        }
                    }
                });


            }
        });
    }
}