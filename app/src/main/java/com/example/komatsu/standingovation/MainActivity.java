package com.example.komatsu.standingovation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements Accelerometer.AccelerometerListener {

    private TextView text;
    private ToggleButton measure;

    private Accelerometer mAccelerometer;

    @Override
    protected void onResume() {
        super.onResume();
        if(mAccelerometer != null) mAccelerometer.register();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViews();
        mAccelerometer = Accelerometer.getInstance(this, this);
    }

    private void findViews() {
        text = (TextView) findViewById(R.id.text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAccelerationChanged(float x, float y, float z) {
        double vector = Math.sqrt(x*x + y*y + z*z);
        String result = String.format("加速度センサー\nx:%s\ny:%s\nz:%s\nベクトル:%s", x, y, z, vector);
        text.setText(result);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAccelerometer != null) mAccelerometer.unregister();
    }
}
