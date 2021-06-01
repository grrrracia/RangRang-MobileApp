package id.bangkit.capstone.RangRang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExplainRulesLevel2Activity extends AppCompatActivity {
    Button btnStartLevel2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_rules_level_2);

        btnStartLevel2 = findViewById(R.id.btnStartLevel2);
        btnStartLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mulai Level 2
            }
        });
    }
}