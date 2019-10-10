package diabetic_retinopathy.project.com.diabeticretinopathy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import diabetic_retinopathy.project.com.Models.UserDataModel;
import diabetic_retinopathy.project.com.Network.NetworkClient;
import diabetic_retinopathy.project.com.constant.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends Activity implements View.OnClickListener {


    MaterialEditText FirstName, Password;
    Button Login;
    TextView Register,ForgotPassword;
    Retrofit retrofit;
    String hostIP, Port, finalURL;
    ImageView mainIcon, userIcon, pwdIcon;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }


    public void init() {
        FirstName = findViewById(R.id.firstName);
        Password = findViewById(R.id.password);
        Login = findViewById(R.id.login);
        Register = findViewById(R.id.register);
        mainIcon = findViewById(R.id.eye_image);
        userIcon = findViewById(R.id.userIcon);
        pwdIcon = findViewById(R.id.passwordIcon);
        ForgotPassword = findViewById(R.id.forgotPasword);

        Picasso.with(LoginActivity.this).load(R.mipmap.eye_loader).into(mainIcon);
        Picasso.with(LoginActivity.this).load(R.mipmap.user).into(userIcon);
        Picasso.with(LoginActivity.this).load(R.mipmap.password).into(pwdIcon);

        Login.setOnClickListener(this);
        Register.setOnClickListener(this);
        ForgotPassword.setOnClickListener(this);

        hostIP = Utils.getSharedPreferences(this, "ipdata", "ipaddress");
        Port = Utils.getSharedPreferences(this, "ipdata", "port");

        finalURL = "http://" + hostIP + ":" + Port;

        retrofit = NetworkClient.getRetrofitClient(LoginActivity.this, finalURL);

        FirstName.setText(Utils.getSharedPreferences(this, "user_data", "Username"));
        Password.setText(Utils.getSharedPreferences(this, "user_data", "Password"));

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        loginAutomatically();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                String firstName = FirstName.getText().toString();
                String password = Password.getText().toString();

                firstName = new String(firstName).toLowerCase();

                Utils.saveToSharedPreferences(LoginActivity.this, "user_data", "Username", firstName.toUpperCase());
                Utils.saveToSharedPreferences(LoginActivity.this, "user_data", "Password", password);

                NetworkClient.sendDataToServer sendDataToServer = retrofit.create(NetworkClient.sendDataToServer.class);


                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("FirstName", firstName);
                jsonObject.addProperty("Password", password);

                //Call<UserDataModel> call2 = NetworkClient.getIntefaceimplementer().loginUser()

                Call<UserDataModel> call = sendDataToServer.loginUser(jsonObject);
                call.enqueue(new Callback<UserDataModel>() {
                    @Override
                    public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                        UserDataModel model = response.body();

                        if (model.getId() == -1)
                            Toast.makeText(LoginActivity.this, "Login Failed ! Wrong Username or Password", Toast.LENGTH_SHORT).show();

                        else {
                            Utils.saveToSharedPreferences(LoginActivity.this, "user_data", "id", model.getId().toString());
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(i);
                            finish();
                        }

                    }

                    @Override
                    public void onFailure(Call<UserDataModel> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Cant Connect To server", Toast.LENGTH_SHORT).show();
                    }
                });
                break;


            case R.id.register:
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
                break;


            case R.id.forgotPasword:
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
                break;
        }
    }

    public void loginAutomatically() {

        String fn = FirstName.getText().toString();
        String pwd = Password.getText().toString();
        if (fn.equalsIgnoreCase("loggedout") && pwd.equalsIgnoreCase("loggedout")) {
            FirstName.setText("");
            Password.setText("");
        } else {
            NetworkClient.sendDataToServer sendDataToServer = retrofit.create(NetworkClient.sendDataToServer.class);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("FirstName", fn);
            jsonObject.addProperty("Password", pwd);

            Call<UserDataModel> call = sendDataToServer.loginUser(jsonObject);
            call.enqueue(new Callback<UserDataModel>() {
                @Override
                public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                    UserDataModel model = response.body();

                    if (model.getId() == -1)
                        Toast.makeText(LoginActivity.this, "Login Failed ! Wrong Username or Password", Toast.LENGTH_SHORT).show();

                    else {
                        Utils.saveToSharedPreferences(LoginActivity.this, "user_data", "id", model.getId().toString());
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        finish();
                    }

                }

                @Override
                public void onFailure(Call<UserDataModel> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Cant Connect To server", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        if (count == 0) {
            Toast.makeText(this, "Press Back Again to Exit", Toast.LENGTH_SHORT).show();
            count += 1;
        } else if (count == 1) {
            super.onBackPressed();
        }

    }
}
