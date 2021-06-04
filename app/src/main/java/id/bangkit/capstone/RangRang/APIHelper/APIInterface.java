package id.bangkit.capstone.RangRang.APIHelper;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {

    @Multipart
    @POST("od-api/video/")
    Call<ResponseBody> videoUpload(
//            @Header("Content-Disposition") String contentDisposition,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("od-api/picture/")
    Call<ResponseBody> photoUpload(
//            @Header("Content-Disposition") String contentDisposition,
            @Part MultipartBody.Part file
//            @Part("file") RequestBody file
            );
}

//response = requests.post('http://35.222.141.247:8080/od-api/video/',
// headers={'Content-Disposition':'Content-Disposition: attachment; filename=test.mp4'},
// files={'file': finput})