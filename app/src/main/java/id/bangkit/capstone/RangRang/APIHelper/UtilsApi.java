package id.bangkit.capstone.RangRang.APIHelper;

public class UtilsApi {

    public static final String BASE_URL_API = "http://34.101.140.95/";
    public static APIInterface getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(APIInterface.class);
    }
}
