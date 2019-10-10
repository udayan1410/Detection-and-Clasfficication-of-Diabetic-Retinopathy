package diabetic_retinopathy.project.com.diabeticretinopathy;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import diabetic_retinopathy.project.com.Models.StatusModel;
import diabetic_retinopathy.project.com.Network.NetworkClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistrationActivity extends Activity implements View.OnClickListener {

    MaterialEditText firstName,lastName,password,email;
    ImageView reguserimage;
    Button Register;
    Retrofit retrofit;
    String hostIP,Port,finalURL,fn,ln,pwd,emailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();
    }

    public void init()
    {


        firstName = findViewById(R.id.regfirstName);
        lastName = findViewById(R.id.reglastName);
        password= findViewById(R.id.regPassword);
        email = findViewById(R.id.regEmail);
        reguserimage = findViewById(R.id.registerUser);


        Picasso.with(RegistrationActivity.this).load(R.mipmap.registeruser).into(reguserimage);


        Register = findViewById(R.id.registerSubmit);
        Register.setOnClickListener(this);

        finalURL = "http://"+hostIP+":"+Port;
        retrofit = NetworkClient.getRetrofitClient(RegistrationActivity.this,finalURL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.registerSubmit:
                boolean validate;
                validate = validateUserData();
                if(validate){
                    NetworkClient.sendDataToServer sendDataToServer = retrofit.create(NetworkClient.sendDataToServer.class);

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("FirstName",fn);
                    jsonObject.addProperty("LastName",ln);
                    jsonObject.addProperty("Password",pwd);
                    jsonObject.addProperty("EmailID",emailid);

                    Call<StatusModel> call = sendDataToServer.registerUSer(jsonObject);
                    call.enqueue(new Callback<StatusModel>() {
                        @Override
                        public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                            StatusModel model = response.body();
                            if(model.getStatus().equalsIgnoreCase("Registration Successful"))
                            {
                                Toast.makeText(RegistrationActivity.this, model.getStatus(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(RegistrationActivity.this, "User Already Present", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<StatusModel> call, Throwable t) {

                        }
                    });

                }
                else{
                Toast.makeText(this, "Please Enter Proper Details", Toast.LENGTH_SHORT).show();
                email.setErrorColor(R.color.red);
                }
                    break;
        }
    }


    public boolean validateUserData()
    {
        boolean validation=false;

         fn = firstName.getText().toString().toLowerCase();
         ln = lastName.getText().toString().toLowerCase();
         pwd = password.getText().toString();
         emailid = email.getText().toString();


        if(!fn.equalsIgnoreCase(""))
        {
            if(!ln.equalsIgnoreCase(""))
            {
                if(!pwd.equalsIgnoreCase(""))
                {
                    if(emailid.indexOf("@")>0 && emailid.indexOf(".")>0)
                        validation=true;
                }
            }
        }


        return validation;
    }
}
