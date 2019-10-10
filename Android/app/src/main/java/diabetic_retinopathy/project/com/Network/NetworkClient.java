package diabetic_retinopathy.project.com.Network;
import android.content.Context;

import com.google.gson.JsonObject;

import diabetic_retinopathy.project.com.Models.StatusModel;
import diabetic_retinopathy.project.com.Models.UserDataModel;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class NetworkClient {

    //public static final String BASE_URL = "http://192.168.0.105:8000/";
    private static Retrofit retrofit;
    public static sendDataToServer server = null;

    public static Retrofit getRetrofitClient(Context context, String URL) {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }



    public interface sendDataToServer {
        @Multipart
        @POST("/uploadImage")
        Call<Object> uploadImage(@Part MultipartBody.Part file, @Part("name") RequestBody requestBody);

        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @POST("/imageData")
        Call<StatusModel> postRawJSON(@Body JsonObject locationPost);

        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @POST("/login")
        Call<UserDataModel> loginUser(@Body JsonObject locationPost);

        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @POST("/register")
        Call<StatusModel> registerUSer(@Body JsonObject locationPost);

        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @POST("/getUserDataFromID")
        Call<UserDataModel> getUserData(@Body JsonObject locationPost);

        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @POST("/sendEmail")
        Call<StatusModel> sendEmail(@Body JsonObject locationPost);

        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @POST("/forgotPassword")
        Call<StatusModel> forgotPassword(@Body JsonObject locationPost);


    }



}
