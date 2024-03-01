package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvTimer;
    private Button btnStart, btnReset, btnStop;

    private boolean isRunning = false;
    private long startTime = 0L;
    private long elapsedTime = 0L;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tvTimer);
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);
        btnStop = findViewById(R.id.btnStop);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    startTime = SystemClock.elapsedRealtime() - elapsedTime;
                    handler.postDelayed(updateTimer, 0);
                    isRunning = true;
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    handler.removeCallbacks(updateTimer);
                    isRunning = false;
                    elapsedTime = SystemClock.elapsedRealtime() - startTime;
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(updateTimer);
                isRunning = false;
                elapsedTime = 0L;
                tvTimer.setText("00:00:00");
            }
        });
    }

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            long updatedTime = SystemClock.elapsedRealtime() - startTime;
            int seconds = (int) (updatedTime / 1000);
            int minutes = seconds / 60;
            seconds %= 60;
            int hours = minutes / 60;
            minutes %= 60;

            String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            tvTimer.setText(timeString);

            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateTimer);
    }
}
