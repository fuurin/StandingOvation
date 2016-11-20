package com.example.komatsu.standingovation;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by komatsu on 2016/11/20.
 */

public class AccelerometerLogger {

    private String mLogFileName = "AccelerometerLogger";

    private Context mContext;
    private Accelerometer mAccelerometer;
    private List<List<String>> mResult;
    private boolean isLogging = false;

    public boolean isLogging() {
        return isLogging;
    }

    public AccelerometerLogger(Context context, Accelerometer accelerometer) {
        mContext = context;
        mAccelerometer = accelerometer;
    }

    public AccelerometerLogger(Context context, Accelerometer accelerometer, String logFileName) {
        mLogFileName = logFileName;
        mContext = context;
        mAccelerometer = accelerometer;
    }

    public void start() {
        isLogging = true;
        mAccelerometer.register();
        mResult = new ArrayList<>();
        mResult.add(Arrays.asList("x", "y", "z", "v"));
    }

    public void addLog(float x, float y, float z) {
        if(!isLogging) return;
        String strX = String.valueOf(x);
        String strY = String.valueOf(y);
        String strZ = String.valueOf(z);
        String strV = String.valueOf(Math.sqrt(x*x + y*y + z*z));
        List<String> row = Arrays.asList(strX, strY, strZ, strV);
        mResult.add(row);
    }

    public void stop() {
        isLogging = false;
        mAccelerometer.unregister();
        CSVWriter.write(mContext, mLogFileName + "#" + new Date().toString(), mResult);
    }
}
