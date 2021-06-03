package id.bangkit.capstone.RangRang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class WelcomeAskForNameActivity extends AppCompatActivity {
    ImageView btnMasuk;
    EditText etNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_ask_for_name);

        getSupportActionBar().hide();

        btnMasuk = findViewById(R.id.btnMasuk);
        etNama = findViewById(R.id.etNama);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etNama.getText().toString();
                Intent goToHome = new Intent(WelcomeAskForNameActivity.this, HomeActivity.class);
                goToHome.putExtra("GivenName", name);
                startActivity(goToHome);
            }
        });
    }
}