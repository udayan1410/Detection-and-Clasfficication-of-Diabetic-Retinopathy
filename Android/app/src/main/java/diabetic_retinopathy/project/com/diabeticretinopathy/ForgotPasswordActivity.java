package diabetic_retinopathy.project.com.diabeticretinopathy;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;

import diabetic_retinopathy.project.com.Models.StatusModel;
import diabetic_retinopathy.project.com.Network.NetworkClient;
import diabetic_retinopathy.project.com.constant.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForgotPasswordActivity extends Activity implements View.OnClickListener {

    MaterialEditText emailID;
    Button submitEmail;
    Retrofit retrofit;
    String hostIP,Port,finalURL;
    Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mDialog = Utils.getDialogBox(this,R.layout.loader_layout,"Sending Email...");

        submitEmail = findViewById(R.id.submitEmail);
        emailID = findViewById(R.id.emailID);

        submitEmail.setOnClickListener(this);

        hostIP = Utils.getSharedPreferences(this, "ipdata", "ipaddress");
        Port = Utils.getSharedPreferences(this, "ipdata", "port");

        finalURL = "http://" + hostIP + ":" + Port;

        retrofit = NetworkClient.getRetrofitClient(this, finalURL);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.submitEmail:
                mDialog.show();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("emailID", emailID.getText().toString());

                NetworkClient.sendDataToServer sendDataToServer = retrofit.create(NetworkClient.sendDataToServer.class);

                Call<StatusModel> call = sendDataToServer.forgotPassword(jsonObject);
                call.enqueue(new Callback<StatusModel>() {
                    @Override
                    public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                        StatusModel statusModel = response.body();
                        mDialog.dismiss();
                        if(statusModel.getStatus().equalsIgnoreCase("Success"))
                            Toast.makeText(ForgotPasswordActivity.this, "Email Sent Successfully", Toast.LENGTH_SHORT).show();


                        else
                            Toast.makeText(ForgotPasswordActivity.this, "Error "+statusModel.getStatus(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<StatusModel> call, Throwable t) {
                        mDialog.dismiss();
                        Toast.makeText(ForgotPasswordActivity.this, "Error Check Internet", Toast.LENGTH_SHORT).show();
                    }
                });

                break;



        }
    }
}
