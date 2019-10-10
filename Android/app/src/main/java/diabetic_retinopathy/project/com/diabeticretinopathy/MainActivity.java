package diabetic_retinopathy.project.com.diabeticretinopathy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.takusemba.cropme.CropView;
import com.takusemba.cropme.OnCropListener;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import diabetic_retinopathy.project.com.Models.UserDataModel;
import diabetic_retinopathy.project.com.Network.NetworkClient;
import diabetic_retinopathy.project.com.constant.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends Activity implements View.OnClickListener {

    TextView previousCheckDateText, previousCheckDate, userName,previousCheckTimeText,previousCheckTime;
    ImageView newPrediction, checkOld, signOut, backBtn;
    int count=0;
    Retrofit retrofit;
    String hostIP,Port,finalURL;
    Dialog mDialog;
    boolean prevres = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getUserDataFromID(Utils.getSharedPreferences(this,"user_data","id"));

    }

    public void init() {

        hostIP = Utils.getSharedPreferences(this,"ipdata","ipaddress");
        Port = Utils.getSharedPreferences(this,"ipdata","port");

        finalURL = "http://"+hostIP+":"+Port;
        retrofit = NetworkClient.getRetrofitClient(MainActivity.this,finalURL);

        mDialog = Utils.getDialogBox(MainActivity.this,R.layout.loader_layout,"Loading");
        mDialog.show();

        userName = findViewById(R.id.userfirstname);
        userName.setText(Utils.getSharedPreferences(this,"user_data","Username"));

        previousCheckDate = findViewById(R.id.previousCheckDate);
        previousCheckDateText = findViewById(R.id.previousCheckDateText);
        previousCheckTime = findViewById(R.id.previousCheckTime);
        previousCheckTimeText = findViewById(R.id.previousCheckTimeText);

        newPrediction = findViewById(R.id.newPrediction);
        checkOld = findViewById(R.id.previousResult);
        signOut = findViewById(R.id.signout);
        backBtn = findViewById(R.id.backButton);
        backBtn.setVisibility(View.INVISIBLE);

        newPrediction.setOnClickListener(this);
        signOut.setOnClickListener(this);

        checkOld.setOnClickListener(this);
        signOut.setOnClickListener(this);

        newPrediction.setImageResource(R.mipmap.gradient);
        checkOld.setImageResource(R.mipmap.gradient);
        signOut.setImageResource(R.mipmap.gradient);


    }

    public void getUserDataFromID(String id)
    {
        NetworkClient.sendDataToServer sendDataToServer = retrofit.create(NetworkClient.sendDataToServer.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",Integer.parseInt(id));

        Call<UserDataModel> call =  sendDataToServer.getUserData(jsonObject);
        call.enqueue(new Callback<UserDataModel>() {
            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                UserDataModel model = response.body();
                Utils.SaveUserDataToSP(MainActivity.this, model, "user_data");
                if(Utils.getSharedPreferences(MainActivity.this, "user_data", "Date").equalsIgnoreCase(""))
                    prevres=false;
                else
                    prevres=true;

                setDataFromModel();
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {

            }
        });
    }

    public void setDataFromModel()
    {
        if (prevres) {
            String[] datetimeArray = Utils.splitDate(Utils.getSharedPreferences(this, "user_data", "Date"));
            previousCheckTime.setText(datetimeArray[0]);
            previousCheckDate.setText(datetimeArray[1]);

        } else {

            previousCheckDateText.setText("No Previous Records Found !");
            previousCheckDate.setVisibility(View.GONE);
            previousCheckTime.setVisibility(View.GONE);
            previousCheckTimeText.setVisibility(View.GONE);
        }
        mDialog.dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newPrediction:
                startActivity(new Intent(MainActivity.this, ChooseLeftImage.class));
                break;

            case R.id.signout:
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
                Utils.saveToSharedPreferences(this,"user_data","Username","loggedout");
                Utils.saveToSharedPreferences(this,"user_data","Password","loggedout");
                Intent i = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.previousResult:
                if (prevres)
                    startActivity(new Intent(MainActivity.this,CheckHistoricalDataActivity.class));
                else
                    Toast.makeText(this, "No Previous Records Found !", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void onBackPressed() {

        if(count==0)
        {
            Toast.makeText(this, "Press Back Again to Exit", Toast.LENGTH_SHORT).show();
            count+=1;
        }

        else if(count==1){
           super.onBackPressed();
            // finish();
        }

    }
}
