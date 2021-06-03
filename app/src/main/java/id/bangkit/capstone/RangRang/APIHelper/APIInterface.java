package id.bangkit.capstone.RangRang.APIHelper;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {

    @Multipart
    @POST("api/upload_file")
    Call<ResponseBody> fileUpload(
            @Part MultipartBody.Part photo
    );
}
