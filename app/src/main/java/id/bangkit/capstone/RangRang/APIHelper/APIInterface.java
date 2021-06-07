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
    Call<ResponseBody> videoObjectUpload(
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("od-api/picture/")
    Call<ResponseBody> photoObjectUpload(
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("cd-api/video/")
    Call<ResponseBody> videoColorUpload(
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("cd-api/picture/")
    Call<ResponseBody> photoColorUpload(
            @Part MultipartBody.Part file
    );
}
