package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class Fertilizer extends AppCompatActivity {

    private Button buttonCalculate;
    private Button buttonCollect;
    private EditText nEditText;
    private EditText pEditText;
    private EditText kEditText;

    private String prediction;
    private String n,p,k;
    boolean isSet=false;

    private ProgressBar progressBar;
    private String timeAndDate;
    private TextView outputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer);

        nEditText = findViewById(R.id.editTextN);
        pEditText = findViewById(R.id.editTextP);
        kEditText = findViewById(R.id.editTextK);

        outputView = findViewById(R.id.textViewFertilizer);

        buttonCalculate = findViewById(R.id.buttonCalculate);

        progressBar = findViewById(R.id.progressBar);



        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSet==true) {

                    progressBar.setVisibility(View.VISIBLE);

                    Timer timer = new Timer();

                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            CollectionThread thread = new CollectionThread();
                            thread.start();
                            progressBar.setProgress(5 + (int)(Math.random() * ((10 - 5) + 1)));

                            if(!prediction.isEmpty()){
                                timer.cancel();
                                progressBar.setVisibility(View.INVISIBLE);
                                //outputView.setText("");
                                Intent intent = new Intent(Fertilizer.this, Result.class);
                                intent.putExtra("prediction", prediction);
                                startActivity(intent);
                            }
                        }
                    };
                    timer.schedule(timerTask,100,100);
//                    CollectionThread thread = new CollectionThread();
//                    thread.start();
//                    Intent intent = new Intent(Fertilizer.this, Result.class);
//                    intent.putExtra("prediction", prediction);
//                    startActivity(intent);
                    //System.out.println("Button is Click");
                }
            }
        });

        buttonCollect = findViewById(R.id.buttonCollect);
        buttonCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("NPK");

                myRef.child("n").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());

                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            n =String.valueOf(task.getResult().getValue());
                            nEditText.setText(String.valueOf(task.getResult().getValue()));

                        }
                    }
                });

                myRef.child("p").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                            //textView.setText("Error");
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            p =String.valueOf(task.getResult().getValue());
                            pEditText.setText(String.valueOf(task.getResult().getValue()));

                        }
                    }
                });

                myRef.child("k").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                            //textView.setText("Error");
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            k =String.valueOf(task.getResult().getValue());
                            kEditText.setText(String.valueOf(task.getResult().getValue()));


                        }
                    }
                });


                myRef = database.getReference("DATE");
                myRef.child("dt").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                            //textView.setText("Error");
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            timeAndDate =String.valueOf(task.getResult().getValue());
                            outputView.setText("Data Collected Successfully.\n"+"Collection Date and Time : "+timeAndDate);

                        }
                    }
                });

                n = nEditText.getText().toString();
                p = pEditText.getText().toString();
                k = kEditText.getText().toString();

               prediction ="";
               isSet = true;
            }
        });

    }


    private class CollectionThread extends Thread {
        @Override
        public void run() {
            collectionFunction();
        }
    }
    void collectionFunction(){

        try {
            // Create URL object
            URL url = new URL("http://ynnusunny.pythonanywhere.com/fertilizer/");

            // Create HttpURLConnection object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("POST");

            // Set request headers
            connection.setRequestProperty("Content-Type", "application/json");

            // Set request body
            String npkValue = n+","+p+","+k;
            String requestBody = "{\"npk_value\":\"" + npkValue + "\"}";
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            // Read response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                bufferedReader.close();
                inputStream.close();

                // Parse JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                prediction = jsonResponse.getString("result");
            } else {
                // Handle error
            }

            // Disconnect HttpURLConnection object
            connection.disconnect();

        } catch (Exception e) {
            prediction = e.toString();
            e.printStackTrace();
        }
    }
}