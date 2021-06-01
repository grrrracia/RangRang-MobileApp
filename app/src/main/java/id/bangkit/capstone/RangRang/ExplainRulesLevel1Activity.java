package id.bangkit.capstone.RangRang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExplainRulesLevel1Activity extends AppCompatActivity {
    Button btnStartLevel1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain_rules_level_1);

        btnStartLevel1 = findViewById(R.id.btnStartLevel1);
        btnStartLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start Level 1
                //Start Room Scan
//                **RoomScan Code**
//                After room scan, then start level 1 intent
                Intent startLevel1 = new Intent(ExplainRulesLevel1Activity.this, Level1Activity.class);
                startActivity(startLevel1);
            }
        });
    }
}