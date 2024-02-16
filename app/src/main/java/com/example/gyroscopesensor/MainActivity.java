package com.example.gyroscopesensor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Sensor mAccelerometer;
    Sensor mMagnetic;

    SensorManager mSensorManager;

    TextView xy;
    TextView xz;
    TextView zy;

    public static double vx;
    public static double vy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xy = findViewById(R.id.xy);
        xz = findViewById(R.id.xz);
        zy = findViewById(R.id.zy);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        setContentView(new Render(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mMagnetic, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSensorManager.unregisterListener(this);
    }

    float[] accel = new float[3];
    float[] magnet = new float[3];

    float[] rotationMatrix = new float[16];
    float[] orientation = new float[3];

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accel = event.values.clone();
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magnet = event.values.clone();
        }
        SensorManager.getRotationMatrix(rotationMatrix, null, accel, magnet);
        SensorManager.getOrientation(rotationMatrix, orientation);

        xy.setText(String.valueOf(Math.round(Math.toDegrees(orientation[0]))));
        xz.setText(String.valueOf(Math.round(Math.toDegrees(orientation[1]))));
        zy.setText(String.valueOf(Math.round(Math.toDegrees(orientation[2]))));

        vx = orientation[2];
        vy = - orientation[1];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}