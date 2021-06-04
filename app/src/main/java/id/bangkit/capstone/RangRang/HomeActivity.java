package id.bangkit.capstone.RangRang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    TextView tvHelloText;
    ImageView btnLevel1, btnLevel2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();

        Intent fromWelcome = getIntent();
        String myName = fromWelcome.getStringExtra("GivenName");
        String welcomeText = myName+" !";

        tvHelloText = findViewById(R.id.tvHaloNama);
        tvHelloText.setText(welcomeText);

        btnLevel1 = findViewById(R.id.openLevel1);
        btnLevel2 = findViewById(R.id.openLevel2);

        btnLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRulesLvl1 = new Intent(HomeActivity.this, PreLevel1Activity.class);
                startActivity(goToRulesLvl1);
            }
        });

        btnLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRulesLvl2 = new Intent(HomeActivity.this, PreLevel2Activity.class);
                startActivity(goToRulesLvl2);
            }
        });
    }
}