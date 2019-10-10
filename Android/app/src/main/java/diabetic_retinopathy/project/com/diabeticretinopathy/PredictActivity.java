package diabetic_retinopathy.project.com.diabeticretinopathy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Network;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import diabetic_retinopathy.project.com.Models.StatusModel;
import diabetic_retinopathy.project.com.Models.UserDataModel;
import diabetic_retinopathy.project.com.Network.NetworkClient;
import diabetic_retinopathy.project.com.constant.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PredictActivity extends Activity {

    String id, hostIP, Port,finalURL;
    Socket socket;
    DataOutputStream dos;
    ReceivePythonData r1;
    byte[] bytes;
    Dialog mDialog;
    TextView mleftEyeResult, mrightEyeResult, musername;
    Button saveAndExit;
    int count = 0;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);

        init();
        sendDatatoPythonServer(id);

        saveAndExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndExit.setClickable(false);

                Intent i = new Intent(PredictActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);




            }
        });

    }


    public void init() {
        id = new String(Utils.getSharedPreferences(this, "user_data", "id"));
        hostIP = Utils.getSharedPreferences(this, "ipdata", "ipaddress");
        Port = Utils.getSharedPreferences(this, "ipdata", "port");
        mleftEyeResult = findViewById(R.id.leftEyeResult);
        mrightEyeResult = findViewById(R.id.rightEyeResult);
        saveAndExit = findViewById(R.id.saveAndExit);
        musername = findViewById(R.id.userName);

        finalURL = "http://"+hostIP+":"+Port;
        retrofit = NetworkClient.getRetrofitClient(this,finalURL);

        musername.setText(Utils.getSharedPreferences(PredictActivity.this,"user_data","Username"));
        mDialog = Utils.getDialogBox(this, R.layout.loader_layout, "Getting Server Response");
        mDialog.show();

    }


    public void sendDatatoPythonServer(final String userid) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    socket = new Socket(hostIP, (Integer.parseInt(Port) + 1));
                    dos = new DataOutputStream(socket.getOutputStream());
                    bytes = userid.getBytes();
                    dos.write(bytes);
                    dos.flush();
                    r1 = new ReceivePythonData();
                    r1.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    public class ReceivePythonData extends Thread {
        @Override
        public void run() {
            super.run();
            BufferedReader in;
            String line;

            try {

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                line = in.readLine();
                Log.d("Tag", "Received = " + line);

                String[] final_result = line.split("/");
                final String leftEyeResult = final_result[0];
                String rightEyeResult = final_result[1];

                Log.d("Tag", "Received2 = " + leftEyeResult + " " + rightEyeResult);
                mDialog.dismiss();

                final String predictionResult[] = Utils.getResultInString(leftEyeResult, rightEyeResult);

                Utils.saveToSharedPreferences(PredictActivity.this, "user_data", "LeftEyeResult", predictionResult[0]);
                Utils.saveToSharedPreferences(PredictActivity.this, "user_data", "RightEyeResult", predictionResult[1]);

                sendEmail();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mleftEyeResult.setText(predictionResult[0]);
                        mrightEyeResult.setText(predictionResult[1]);
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void sendEmail()
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",id);

        NetworkClient.sendDataToServer sendDataToServer = retrofit.create(NetworkClient.sendDataToServer.class);
        Call<StatusModel> call =  sendDataToServer.sendEmail(jsonObject);
        call.enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                Log.d("Tag","Email = "+new Gson().toJson(response.body()));
                Toast.makeText(PredictActivity.this, "Checkup Results sent via Email", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                Toast.makeText(PredictActivity.this, "Error Cannot Send Email", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (count == 0)
            Toast.makeText(this, "Press again to go back", Toast.LENGTH_SHORT).show();

        else {
            Intent i = new Intent(PredictActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        count = count + 1;
    }
}
