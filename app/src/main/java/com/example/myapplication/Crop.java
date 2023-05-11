package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class Crop extends AppCompatActivity {

    private Button predict_button;
    private Button buttonCollect;
    private EditText nEditText;
    private EditText pEditText;
    private EditText kEditText;
    private EditText tEditText;
    private EditText hEditText;
    private EditText phEditText;
    private EditText rEditText;

    private String prediction;
    private String n,p,k,t,h,ph,r;
    boolean isSet=false;

    private ProgressBar progressBar;
    private TextView outputView;

    private String timeAndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        nEditText = findViewById(R.id.editTextN);
        pEditText = findViewById(R.id.editTextP);
        kEditText = findViewById(R.id.editTextK);
        tEditText = findViewById(R.id.editTextT);
        hEditText = findViewById(R.id.editTextH);
        phEditText = findViewById(R.id.editTextPH);
        rEditText = findViewById(R.id.editTextR);

        outputView = findViewById(R.id.textView);

        predict_button = findViewById(R.id.predict_button);


        progressBar = findViewById(R.id.progressBar);

        predict_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSet==true) {
//                    CollectionThread thread = new CollectionThread();
//                    thread.start();
//                    outputView.setText("Output : "+prediction);

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
                                //progressBar.setVisibility(View.GONE);
                                progressBar.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(Crop.this, Result.class);
                                intent.putExtra("prediction", prediction);
                                startActivity(intent);
                            }
                        }
                    };
                    timer.schedule(timerTask,100,100);




                }else{
                    outputView.setText("No Data is Provided");
                }
            }
        });




        buttonCollect = findViewById(R.id.collect_button);
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

                myRef = database.getReference("DHT");
                myRef.child("h").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                            //textView.setText("Error");
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            h =String.valueOf(task.getResult().getValue());
                            hEditText.setText(String.valueOf(task.getResult().getValue()));


                        }
                    }
                });
                myRef.child("t").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                            //textView.setText("Error");
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            t =String.valueOf(task.getResult().getValue());
                            tEditText.setText(String.valueOf(task.getResult().getValue()));


                        }
                    }
                });

                myRef = database.getReference("PH");
                myRef.child("ph").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                            //textView.setText("Error");
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            ph =String.valueOf(task.getResult().getValue());
                            phEditText.setText(String.valueOf(task.getResult().getValue()));


                        }
                    }
                });

                myRef = database.getReference("R");
                myRef.child("r").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                            //textView.setText("Error");
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            r =String.valueOf(task.getResult().getValue());
                            rEditText.setText(String.valueOf(task.getResult().getValue()));


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
                t = tEditText.getText().toString();
                h = hEditText.getText().toString();
                ph = phEditText.getText().toString();
                r = rEditText.getText().toString();
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
            URL url = new URL("http://ynnusunny.pythonanywhere.com/crop/");

            // Create HttpURLConnection object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("POST");

            // Set request headers
            connection.setRequestProperty("Content-Type", "application/json");

            // Set request body
            String values = n+","+p+","+k+","+t+","+h+","+ph+","+r;
            String requestBody = "{\"values\":\"" + values + "\"}";
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