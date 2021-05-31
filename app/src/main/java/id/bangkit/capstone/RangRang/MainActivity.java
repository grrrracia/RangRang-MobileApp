package id.bangkit.capstone.RangRang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        Handler handler = new Handler();
        Intent intent = new Intent(MainActivity.this, WelcomeAskForName.class);
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };
        handler.postDelayed(r, 2000);

    }
}