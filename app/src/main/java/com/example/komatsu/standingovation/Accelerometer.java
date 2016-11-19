package com.example.komatsu.standingovation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.List;

/**
 * Created by komatsu on 2016/11/19.
 */

public final class Accelerometer implements SensorEventListener {

    public interface AccelerometerListener {
        void onAccelerationChanged(float x, float y, float z);
    }
    private AccelerometerListener mListener;

    private static Accelerometer instance;
    private SensorManager manager;

    public static Accelerometer getInstance(Context context, AccelerometerListener listener) {
        if(instance == null) instance = new Accelerometer(context, listener);
        return instance;
    }

    private Accelerometer(Context context, AccelerometerListener listener) {
        mListener = listener;
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public void register() {
        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(sensors.size() <= 0) return;
        Sensor sensor = sensors.get(0);
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void unregister() {
        manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;
        float x = event.values[SensorManager.DATA_X];
        float y = event.values[SensorManager.DATA_Y];
        float z = event.values[SensorManager.DATA_Z];
        mListener.onAccelerationChanged(x, y, z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
