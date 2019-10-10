package diabetic_retinopathy.project.com.diabeticretinopathy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import diabetic_retinopathy.project.com.Models.StatusModel;
import diabetic_retinopathy.project.com.Network.NetworkClient;
import diabetic_retinopathy.project.com.constant.Utils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChooseRightImage extends Activity implements View.OnClickListener{

    File rightEyeFile,leftEyeFile;
    Button chooseFile,predict;
    ImageView RightEyeImage;
    String leftEyeFileName,rightEyeFileName,leftEyeExtension,rightEyeExtension,finalURL,hostIP,Port;
    Dialog mDialog;
    Retrofit retrofit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_right_image);

        init();

    }


    public void init()
    {
        chooseFile = findViewById(R.id.select_right_image);

        predict = findViewById(R.id.predict);
        RightEyeImage = findViewById(R.id.chosenRightImage);
        chooseFile.setOnClickListener(this);
        predict.setOnClickListener(this);
        predict.setVisibility(View.INVISIBLE);

        mDialog = Utils.getDialogBox(this,R.layout.loader_layout,"Uploading Images");

        hostIP = Utils.getSharedPreferences(this,"ipdata","ipaddress");
        Port = Utils.getSharedPreferences(this,"ipdata","port");
        finalURL = "http://"+hostIP+":"+Port;

        retrofit = NetworkClient.getRetrofitClient(ChooseRightImage.this,finalURL);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.select_right_image:
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setAction(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(galleryIntent,"Select Picture"), 1);
                break;


            case R.id.predict:
                sendLeftEyeImage();
                mDialog.show();
                break;

            case R.id.backButton:
                finish();
                break;
        }
    }

    public void sendLeftEyeImage()
    {
        NetworkClient.sendDataToServer sendDataToServerAPI= retrofit.create(NetworkClient.sendDataToServer.class);
        leftEyeFile = new File(Utils.getSharedPreferences(ChooseRightImage.this,"temp_data","LeftEye")) ;

        leftEyeExtension = leftEyeFile.getName().substring(leftEyeFile.getName().lastIndexOf("."));
        leftEyeFileName = ""+System.currentTimeMillis();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),leftEyeFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("upload",leftEyeFileName+leftEyeExtension,requestBody);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");

        Call call = sendDataToServerAPI.uploadImage(part,description);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                sendRightEyeImage();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Tag","Fail");
            }
        });
    }

    public void sendRightEyeImage()
    {
        NetworkClient.sendDataToServer sendDataToServerAPI= retrofit.create(NetworkClient.sendDataToServer.class);

        rightEyeExtension = rightEyeFile.getName().substring(rightEyeFile.getName().lastIndexOf("."));
        rightEyeFileName = ""+System.currentTimeMillis();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),rightEyeFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("upload",rightEyeFileName+rightEyeExtension,requestBody);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");

        Call call = sendDataToServerAPI.uploadImage(part,description);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                sendJsonData();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Tag","Fail");
            }
        });

    }

    public void sendJsonData()
    {
        NetworkClient.sendDataToServer sendDataToServerAPI= retrofit.create(NetworkClient.sendDataToServer.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",Integer.parseInt(Utils.getSharedPreferences(this,"user_data","id")));
        jsonObject.addProperty("LeftEye",leftEyeFileName+leftEyeExtension);
        jsonObject.addProperty("RightEye",rightEyeFileName+rightEyeExtension);
        jsonObject.addProperty("LeftEyeResult","");
        jsonObject.addProperty("RightEyeResult","");
        jsonObject.addProperty("Date",Utils.getCurrentDate());

        Call<StatusModel> call = sendDataToServerAPI.postRawJSON(jsonObject);
        call.enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                StatusModel model = response.body();
                Log.d("Tag","Response = "+model.getStatus());
                if(model.getStatus().equalsIgnoreCase("Success")) {
                    mDialog.dismiss();
                    startActivity(new Intent(ChooseRightImage.this, PredictActivity.class));
                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String imagepath = getPath(selectedImage);
            rightEyeFile = new File(imagepath);
            predict.setVisibility(View.VISIBLE);
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        //cursor.close();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        RightEyeImage.setImageBitmap(bitmap);
        return cursor.getString(column_index);
    }




}


