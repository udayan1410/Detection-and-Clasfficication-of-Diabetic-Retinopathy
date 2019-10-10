package diabetic_retinopathy.project.com.diabeticretinopathy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import diabetic_retinopathy.project.com.constant.Utils;

public class IP_Port_Activity extends Activity {

    Button Submit;
    EditText IP,Port;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip__port_);

        init();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IP_Port_Activity.this,LoginActivity.class);
                i.putExtra("IP",IP.getText().toString());
                i.putExtra("Port",Port.getText().toString());
                i.putExtra("Address",Port.getText().toString());

                Utils.saveToSharedPreferences(IP_Port_Activity.this,"ipdata","ipaddress",IP.getText().toString());
                Utils.saveToSharedPreferences(IP_Port_Activity.this,"ipdata","port",Port.getText().toString());

                startActivity(i);
                finish();
            }
        });

    }


    public void init()
    {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        Submit = findViewById(R.id.submitData);
        IP = findViewById(R.id.ipAddress);
        Port = findViewById(R.id.portNumber);

        IP.setText(Utils.getSharedPreferences(IP_Port_Activity.this,"ipdata","ipaddress"));
        Port.setText(Utils.getSharedPreferences(IP_Port_Activity.this,"ipdata","port"));

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
