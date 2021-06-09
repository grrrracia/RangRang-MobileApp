package id.bangkit.capstone.RangRang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    TextView tvHelloText;
    ImageView btnLevel1, btnLevel2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 101);

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

    private void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(HomeActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(HomeActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(HomeActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}