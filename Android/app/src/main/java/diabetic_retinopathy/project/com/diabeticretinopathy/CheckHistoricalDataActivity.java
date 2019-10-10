package diabetic_retinopathy.project.com.diabeticretinopathy;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import diabetic_retinopathy.project.com.Models.StatusModel;
import diabetic_retinopathy.project.com.Network.NetworkClient;
import diabetic_retinopathy.project.com.constant.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CheckHistoricalDataActivity extends Activity {
    ImageView backButton,sendEmail;
    TextView prevCheckTime,prevCheckDate,userName,leftEyeRes,rightEyeRes;
    String[] dateTimeArray,predictionResult;
    String leftEyeResult,rightEyeResult;
    String  hostIP, Port,finalURL;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_historical_data);

        init();
        setDataToView();

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id",Utils.getSharedPreferences(CheckHistoricalDataActivity.this,"user_data","id"));

                NetworkClient.sendDataToServer sendDataToServer = retrofit.create(NetworkClient.sendDataToServer.class);
                Call<StatusModel> call =  sendDataToServer.sendEmail(jsonObject);
                call.enqueue(new Callback<StatusModel>() {
                    @Override
                    public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                        sendEmail.setClickable(true);
                        Toast.makeText(CheckHistoricalDataActivity.this, "Email Sent Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<StatusModel> call, Throwable t) {
                        Toast.makeText(CheckHistoricalDataActivity.this, "Error Cannot Send Email", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }


    public void init()
    {
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userName = findViewById(R.id.userfirstnamehistory);
        prevCheckTime = findViewById(R.id.previousCheckTime);
        prevCheckDate = findViewById(R.id.previousCheckDate);
        leftEyeRes = findViewById(R.id.leftEyeResult);
        rightEyeRes = findViewById(R.id.rightEyeResult);

        dateTimeArray = Utils.splitDate(Utils.getSharedPreferences(this, "user_data", "Date"));

        leftEyeResult = Utils.getSharedPreferences(this,"user_data","LeftEyeResult");
        rightEyeResult = Utils.getSharedPreferences(this,"user_data","RightEyeResult");

        sendEmail = findViewById(R.id.sendEmailButton);
        Picasso.with(this).load(R.mipmap.gradient).into(sendEmail);

        dateTimeArray = Utils.splitDate(Utils.getSharedPreferences(this,"user_data","Date"));
        predictionResult = Utils.getResultInString(leftEyeResult,rightEyeResult);

        hostIP = Utils.getSharedPreferences(this, "ipdata", "ipaddress");
        Port = Utils.getSharedPreferences(this, "ipdata", "port");

        finalURL = "http://"+hostIP+":"+Port;
        retrofit = NetworkClient.getRetrofitClient(this,finalURL);
    }

    public void setDataToView()
    {
            userName.setText( Utils.getSharedPreferences(this,"user_data","Username"));
            prevCheckTime.setText(dateTimeArray[0]);
            prevCheckDate.setText(dateTimeArray[1]);
            leftEyeRes.setText(predictionResult[0]);
            rightEyeRes.setText(predictionResult[1]);
    }
}
