package id.bangkit.capstone.RangRang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Level2Activity extends AppCompatActivity {
    Button btnCheck;
    TextView tvObjectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);

        tvObjectName = findViewById(R.id.tvObjectToFind);
        String objectToFind = "";
        tvObjectName.setText(objectToFind);

        btnCheck = findViewById(R.id.btnLevel2Check);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start Camera
            }
        });
    }
}