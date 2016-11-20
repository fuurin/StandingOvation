package com.example.komatsu.standingovation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

// ポジショントラッキングとかいうのやりたいかも
// http://qiita.com/binzume/items/3bbca6ab8a7926fc7255

public class MainActivity extends AppCompatActivity implements Accelerometer.AccelerometerListener {

    private TextView text;
    private ToggleButton measureButton;

    private Accelerometer mAccelerometer;
    private AccelerometerLogger mLogger;

    @Override
    protected void onResume() {
        super.onResume();
        // if(mAccelerometer != null) mAccelerometer.register();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViews();
        attachEvents();
        mAccelerometer = Accelerometer.getInstance(this, this);
        mLogger = new AccelerometerLogger(this, mAccelerometer, "StandingUp");
    }

    private void findViews() {
        measureButton = (ToggleButton) findViewById(R.id.measure_button);
        text = (TextView) findViewById(R.id.text);
    }

    private void attachEvents() {
        measureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(measureButton.isChecked()) mLogger.start();
                else mLogger.stop();
            }
        });
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
        String result = String.format("加速度センサー\nx:%s\ny:%s\nz:%s", x, y, z);
        text.setText(result);
        mLogger.addLog(x, y, z);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // if(mAccelerometer != null) mAccelerometer.unregister();
    }
}
