package diabetic_retinopathy.project.com.diabeticretinopathy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import diabetic_retinopathy.project.com.constant.Utils;

public class ChooseLeftImage extends Activity implements View.OnClickListener {

    File imageFile;
    Button chooseFile,next;
    ImageView chosenImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_left_image);

        init();


    }


    public void init() {

        chooseFile = findViewById(R.id.select_image);
        next = findViewById(R.id.next);
        chosenImage = findViewById(R.id.chosenImage);
        chooseFile.setOnClickListener(this);
        next.setOnClickListener(this);
        next.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.select_image:
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setAction(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(galleryIntent,"Select Picture"), 1);
                break;

            case R.id.next:
                Utils.saveToSharedPreferences(ChooseLeftImage.this,"temp_data","LeftEye",imageFile.toString());
                Intent i = new Intent(ChooseLeftImage.this,ChooseRightImage.class);
                startActivity(i);
                break;


            case R.id.backButton:
                finish();
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String imagepath = getPath(selectedImage);
            imageFile = new File(imagepath);
            next.setVisibility(View.VISIBLE);
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
        chosenImage.setImageBitmap(bitmap);
        return cursor.getString(column_index);
    }
}


