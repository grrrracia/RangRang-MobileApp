package id.bangkit.capstone.RangRang.APIHelper;

public class UtilsApi {

//    public static final String BASE_URL_API = "http://34.101.165.63/";
    public static final String BASE_URL_API = "http://35.222.141.247/";
    public static APIInterface getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(APIInterface.class);
    }
}
