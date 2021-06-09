package id.bangkit.capstone.RangRang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.loader.content.CursorLoader;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import id.bangkit.capstone.RangRang.APIHelper.APIInterface;
import id.bangkit.capstone.RangRang.APIHelper.UtilsApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreLevel2Activity extends AppCompatActivity {

    static final int REQUEST_VIDEO_CAPTURE = 1;

    ConstraintLayout clPreLevel2;
    LinearLayout llPrelevel2;

    Button btnStartLevel2;

    TextView tvPrompt;

    Context mContext;

    APIInterface mApiService;
    Call<ResponseBody> uploadFile;
    JSONObject jsonValues;

    ArrayList<String> arrayObjects = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_level_2);
        mContext = this;
        btnStartLevel2 = findViewById(R.id.btnStartLevel2);
        clPreLevel2 = findViewById(R.id.CLPrelevel2);
        llPrelevel2 = findViewById(R.id.LLRulesLevel2);
        tvPrompt = findViewById(R.id.tvRulesLevel2);

        btnStartLevel2.setOnClickListener(new View.OnClickListener() {
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

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(mContext, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            String fullPath = getRealPathFromURI(videoUri);

            File file = new File(fullPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            System.out.println(fileToUpload);

            Call<ResponseBody> call = mApiService.videoObjectUpload(fileToUpload);
//            tvPrompt.setText("Please Wait While We Analyze the Video");
            clPreLevel2.setBackgroundResource(R.drawable.plswaitbg);
            llPrelevel2.setVisibility(View.GONE);

            btnStartLevel2.setVisibility(View.GONE);


            System.out.println(call);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ResponseBody jsonResponse = response.body();
                    System.out.println("++++++SENT POST++++++++");
                    try {
                        String jsonString = response.body().string();
                        jsonValues = new JSONObject(jsonString);
                        JSONArray arr_temp = jsonValues.getJSONArray("object");
                        for (int i = 0; i < arr_temp.length(); i++) arrayObjects.add(arr_temp.getString(i));
                        System.out.println("arraylistny");
                        for (int i = 0; i < arrayObjects.size(); i++) System.out.println(arrayObjects.get(i));

                        if (arrayObjects.size() < 2){
//                            tvPrompt.setText("Please Scan the Room Again");
                            Toast.makeText(getApplicationContext(),"Jumlah Object yang Ditemukan Kurang, Silahkan Rekam Ruangan Lagi",Toast.LENGTH_LONG).show();
                            clPreLevel2.setBackgroundResource(R.drawable.rulesbg);
                            btnStartLevel2.setVisibility(View.VISIBLE);
                        }else{
                            Intent startLevel2 = new Intent(PreLevel2Activity.this, findObjectActivity.class);
                            startLevel2.putExtra("DetectedObjects", arrayObjects);
                            startActivity(startLevel2);
                        }

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.getLocalizedMessage();
                    System.out.println(t.getLocalizedMessage());
                    System.out.println(t.getMessage());
                    System.out.println("FAILED");
                }
            });

        }
    }
}