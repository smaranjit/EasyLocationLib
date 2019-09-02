package com.smaranjit.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.smaranjit.easylocationlib.EasyLocation;
import com.smaranjit.easylocationlib.EasyLocationSettings;

import java.util.List;

public class MainActivity extends AppCompatActivity implements EasyLocation.OnLocationEventListener {

    EditText mLatitude, mLongitude, mAccurecy;
    Button mStartButton, mStopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EasyLocationSettings.withActivity(this)
                .setInterval(2000)
                .setFastestInterval(1000)
                .setPriority(EasyLocationSettings.PRIORITY_HIGH_ACCURACY)
                .setSmallestDisplacement(0)
                .build();
        final EasyLocation el = EasyLocation.with(this).start();
        mLatitude = findViewById(R.id.editTextLatitude);
        mLongitude = findViewById(R.id.editTextLongitude);
        mAccurecy = findViewById(R.id.editTextAccurecy);

        mStartButton = findViewById(R.id.buttonStartListening);
        mStopButton = findViewById(R.id.buttonStopListening);


        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addListener();
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeListener();
            }
        });
    }

    void addListener() {
        EasyLocation.addListener(this);
    }

    void removeListener() {
        EasyLocation.removeListener(this);
    }

    @Override
    public void onLocationEvent(Location location) {
        System.out.println(location);
        mLatitude.setText(Double.toString(location.getLatitude()));
        mLongitude.setText(Double.toString(location.getLongitude()));
        mAccurecy.setText(Double.toString(location.getAccuracy()));
    }
}
