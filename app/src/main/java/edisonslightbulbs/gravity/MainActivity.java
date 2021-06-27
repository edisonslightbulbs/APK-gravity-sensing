package edisonslightbulbs.gravity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView m_gravityX;
    TextView m_gravityY;
    TextView m_gravityZ;

    private SensorManager m_sensorManager;
    private Sensor m_gravity;

    // round up to n number of decimal places
    DecimalFormat df = new DecimalFormat("#.######");

    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        df.setRoundingMode(RoundingMode.CEILING);

        m_sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (m_sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null){
            m_gravity = m_sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
            Utils.toast(this, "gravity sensor detected");
        } else{
            Utils.toast(this, "sorry, gravity sensor not detected");
            Log.e(TAG, "-- gravity sensor not found!");
        }

        m_gravityX = findViewById(R.id.accXTextView);
        m_gravityY = findViewById(R.id.accYTextView);
        m_gravityZ = findViewById(R.id.accZTextView);

        m_gravityX.setText("0.0");
        m_gravityY.setText("0.0");
        m_gravityZ.setText("0.0");
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        String xValue = df.format(event.values[0]);
        String yValue = df.format(event.values[1]);
        String zValue = df.format(event.values[2]);

        m_gravityX.setText(xValue);
        m_gravityY.setText(yValue);
        m_gravityZ.setText(zValue);
    }

    @Override
    protected void onResume() {
        super.onResume();
        m_sensorManager.registerListener(this, m_gravity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        m_sensorManager.unregisterListener(this);
    }
}