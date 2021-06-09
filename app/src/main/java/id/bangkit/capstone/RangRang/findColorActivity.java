package id.bangkit.capstone.RangRang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import id.bangkit.capstone.RangRang.APIHelper.APIInterface;
import id.bangkit.capstone.RangRang.APIHelper.UtilsApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class findColorActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Button btnCheck, btnGoHome;
    TextView tvFindThisColor;

    Context mContext;

    APIInterface mApiService;
    Call<ResponseBody> uploadFile;
    JSONObject jsonValues;

    ArrayList<String> arrayColors = new ArrayList<String>();
    ArrayList<String> receivedArrayColors = new ArrayList<String>();

    String currentColor = "";
    Boolean check;
    int counter = 0;

    private static final int pic_id = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_color);

        btnCheck = findViewById(R.id.btnCheckColor);
        btnGoHome = findViewById(R.id.btnGoHome);
        btnGoHome.setVisibility(View.GONE);
        tvFindThisColor = findViewById(R.id.tvFindThisColor);
        mContext = this;

        receivedArrayColors = (ArrayList<String>) getIntent().getSerializableExtra("DetectedColors");

        currentColor = receivedArrayColors.get(counter);
        System.out.println(currentColor);
        tvFindThisColor.setText(currentColor.toUpperCase());

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApiService = UtilsApi.getAPIService();
                takePicture();
            }
        });

    }

    private void takePicture() {
        MediaController controller = new MediaController(this);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Uri imageUri;
            imageUri = getImageUri(mContext, imageBitmap);
            System.out.println(imageUri.getPath());
            String imageRealPath = getRealPathFromURI(imageUri);
            System.out.println(imageRealPath);


            File file = new File(imageRealPath);
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            System.out.println(fileToUpload);

            Call<ResponseBody> call = mApiService.photoColorUpload(fileToUpload);


            System.out.println(call);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ResponseBody jsonResponse = response.body();
                    System.out.println("++++++SENT POST++++++++");
                    try {
                        String jsonString = response.body().string();
                        jsonValues = new JSONObject(jsonString);
                        JSONArray arr_temp = jsonValues.getJSONArray("color");//Change to Color Later
                        for (int i = 0; i < arr_temp.length(); i++)
                            arrayColors.add(arr_temp.getString(i));
                        System.out.println("arraylistny");
                        for (int i = 0; i < arrayColors.size(); i++)
                            System.out.println(arrayColors.get(i));

                        if (arrayColors.contains(currentColor)) {
                            System.out.println("_+_+_+_+_+_+_+_+_+_+__CORRECT _++_+_+_+_+_+_+_+_+");
                            System.out.println(counter);
                            counter = counter + 1;
                            arrayColors.clear();

                            if (counter == receivedArrayColors.size()) {
                                tvFindThisColor.setText("GAME OVER");
                                btnCheck.setVisibility(View.GONE);
                                btnGoHome.setVisibility(View.VISIBLE);
                                btnGoHome.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent goHome = new Intent(findColorActivity.this, HomeActivity.class);
                                        startActivity(goHome);
                                    }
                                });

                            }else {
                                currentColor = receivedArrayColors.get(counter);
                                System.out.println(currentColor);
                                tvFindThisColor.setText(currentColor.toUpperCase());
                            }

                        } else {
                            Toast.makeText(getApplicationContext(),"WRONG PLEASE TRY AGAIN",Toast.LENGTH_SHORT).show();
                            arrayColors.clear();
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

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "temporary", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(mContext, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public static void saveBitmap(String path, String bitName, Bitmap mBitmap) {

        File f = new File(Environment.getExternalStorageDirectory()
                .toString() + "/" + bitName + ".png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}