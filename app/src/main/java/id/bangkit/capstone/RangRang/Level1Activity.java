package id.bangkit.capstone.RangRang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Level1Activity extends AppCompatActivity {
    Button btnCheck;
    TextView tvColorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        tvColorName = findViewById(R.id.tvColorToFind);
        String colorToFind = "";
        tvColorName.setText(colorToFind);

        btnCheck = findViewById(R.id.btnLevel1Check);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start Camera
            }
        });
    }
}