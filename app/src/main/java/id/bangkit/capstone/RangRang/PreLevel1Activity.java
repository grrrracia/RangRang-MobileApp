package id.bangkit.capstone.RangRang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import java.io.File;

import id.bangkit.capstone.RangRang.APIHelper.APIInterface;
import id.bangkit.capstone.RangRang.APIHelper.RetrofitClient;
import id.bangkit.capstone.RangRang.APIHelper.UtilsApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreLevel1Activity extends AppCompatActivity {

    static final int REQUEST_VIDEO_CAPTURE = 1;

    Button btnStartLevel1;

    Context mContext;

    APIInterface mApiService;
    Call<ResponseBody> uploadFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_level_1);

        btnStartLevel1 = findViewById(R.id.btnStartLevel1);
        btnStartLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mApiService = UtilsApi.getAPIService();
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
            System.out.println(videoUri.getPath());
//            videoView.setVideoURI(videoUri);

            File file = new File(videoUri.getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("photo", file.getName(), requestBody);
            System.out.println(fileToUpload);

            Call<ResponseBody> call = mApiService.fileUpload(fileToUpload);
            System.out.println(call);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ResponseBody jsonResponse = response.body();
                    System.out.println("++++++++++++Sent POST++++++++++++");
                    System.out.println(jsonResponse);

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.getLocalizedMessage();
                    System.out.println("FAILED");
                }
            });

            Intent startLevel1 = new Intent(PreLevel1Activity.this, findColorActivity.class);
            startActivity(startLevel1);
        }
    }
}