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

public class PreLevel1Activity extends AppCompatActivity {

    static final int REQUEST_VIDEO_CAPTURE = 1;

    ConstraintLayout clPreLevel1;
    LinearLayout llPrelevel1;

    Button btnStartLevel1;

    Context mContext;

    TextView tvPrompt;

    APIInterface mApiService;
    Call<ResponseBody> uploadFile;
    JSONObject jsonValues;

    ArrayList<String> arrayColors = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_level_1);
        getSupportActionBar().hide();

        mContext = this;
        btnStartLevel1 = findViewById(R.id.btnStartLevel1);
        clPreLevel1 = findViewById(R.id.CLPrelevel1);
        llPrelevel1 = findViewById(R.id.LLRulesLevel1);
        tvPrompt = findViewById(R.id.tvRulesLevel1);

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

            Call<ResponseBody> call = mApiService.videoColorUpload(fileToUpload);
//            tvPrompt.setText("Please Wait While We Analyze The Room");
            clPreLevel1.setBackgroundResource(R.drawable.plswaitbg);
            llPrelevel1.setVisibility(View.GONE);

            btnStartLevel1.setVisibility(View.GONE);


            System.out.println(call);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ResponseBody jsonResponse = response.body();
                    System.out.println("++++++SENT POST++++++++");
                    try {
                        String jsonString = response.body().string();
                        jsonValues = new JSONObject(jsonString);
                        JSONArray arr_temp = jsonValues.getJSONArray("color");
                        for (int i = 0; i < arr_temp.length(); i++) arrayColors.add(arr_temp.getString(i));
                        System.out.println("arraylistny");
                        for (int i = 0; i < arrayColors.size(); i++) System.out.println(arrayColors.get(i));

                        if (arrayColors.size() < 2){
//                            tvPrompt.setText("Please Scan the Room Again");
//                            System.out.println("Lacking items to detect");
                            Toast.makeText(mContext,"Jumlah Object yang Ditemukan Kurang, Silahkan Rekam Ruangan Lagi",Toast.LENGTH_LONG).show();
                            clPreLevel1.setBackgroundResource(R.drawable.rulesbg);
                            llPrelevel1.setVisibility(View.VISIBLE);
                            btnStartLevel1.setVisibility(View.VISIBLE);
                        }else {
                            Intent startLevel1 = new Intent(PreLevel1Activity.this, findColorActivity.class);
                            startLevel1.putExtra("DetectedColors", arrayColors);
                            startActivity(startLevel1);
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