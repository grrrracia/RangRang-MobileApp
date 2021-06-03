package id.bangkit.capstone.RangRang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class PreLevel2Activity extends AppCompatActivity {

    static final int REQUEST_VIDEO_CAPTURE = 1;

//    String CAMERA_PERMISSION = Manifest.permission.CAMERA;

    Button btnStartLevel2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_level_2);

        btnStartLevel2 = findViewById(R.id.btnStartLevel2);
        btnStartLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanRoom();
            }
        });
    }

    private void scanRoom() {
        Intent recordVideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (recordVideo.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(recordVideo, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            System.out.println(videoUri);
//            videoView.setVideoURI(videoUri);
            Intent startLevel2 = new Intent(PreLevel2Activity.this, findObjectActivity.class);
            startActivity(startLevel2);
        }
    }
}