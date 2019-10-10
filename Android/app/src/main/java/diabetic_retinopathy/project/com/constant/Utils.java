package diabetic_retinopathy.project.com.constant;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.Inflater;

import diabetic_retinopathy.project.com.Models.UserDataModel;
import diabetic_retinopathy.project.com.diabeticretinopathy.R;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class Utils {


    public static Dialog getDialogBox(Context context, int layout,String text)
    {
        PulsatorLayout radar;
        Dialog dialog;
        ImageView backbtn;

        dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.setContentView(layout);
        dialog.getWindow().setAttributes(lp);

        radar = dialog.findViewById(R.id.radar_loading);
        if(radar.isStarted())
        {
            radar.stop();
            radar.start();
        }
        else
            radar.start();

        TextView loadingStatus = dialog.findViewById(R.id.loadingStatus);
        loadingStatus.setText(text);

        backbtn = dialog.findViewById(R.id.backButton);
        backbtn.setVisibility(View.GONE);

        return dialog;
    }

    public static void saveToSharedPreferences(Context context,String sharedPref,String sharedPrefKey,String sharedPrefValue)
    {
        SharedPreferences preferences = context.getSharedPreferences(sharedPref,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(sharedPrefKey,sharedPrefValue);
        editor.commit();

    }

    public static String getSharedPreferences(Context context , String sharedPref , String sharedPrefKey)
    {
        SharedPreferences preferences = context.getSharedPreferences(sharedPref,Context.MODE_PRIVATE);
        return preferences.getString(sharedPrefKey,"").toString();

    }


    public static String getCurrentDate()
    {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm-dd/MMM/yyyy");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static void SaveUserDataToSP(Context context,UserDataModel model,String sharedPref){

        saveToSharedPreferences(context,sharedPref,"LeftEye",model.getLeftEye());
        saveToSharedPreferences(context,sharedPref,"RightEye",model.getRightEye());
        saveToSharedPreferences(context,sharedPref,"LeftEyeResult",model.getLeftEyeResult());
        saveToSharedPreferences(context,sharedPref,"RightEyeResult",model.getRightEyeResult());
        saveToSharedPreferences(context,sharedPref,"Date",model.getDate());
    }

    public static String[] splitDate(String DateTime)
    {
        String arr[] = DateTime.split("-");
        return arr;
    }

    public static String[] getResultInString(String leftRes, String rightRes) {
        String finalRes[] = new String[2];

        switch (leftRes) {

            case "0":
                finalRes[0] = "No DR";
                break;

            case "1":
                finalRes[0] = "Mild DR";
                break;

            case "2":
                finalRes[0] = "Medium DR";
                break;

            case "3":
                finalRes[0] = "Severe DR";
                break;
        }

        switch (rightRes) {

            case "0":
                finalRes[1] = "No DR";
                break;

            case "1":
                finalRes[1] = "Mild DR";
                break;

            case "2":
                finalRes[1] = "Medium DR";
                break;

            case "3":
                finalRes[1] = "Severe DR";
                break;
        }

        return finalRes;
    }

}
