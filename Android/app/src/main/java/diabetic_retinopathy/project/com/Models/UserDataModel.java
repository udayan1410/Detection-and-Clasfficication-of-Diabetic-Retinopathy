
package diabetic_retinopathy.project.com.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDataModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("LeftEye")
    @Expose
    private String leftEye;
    @SerializedName("RightEye")
    @Expose
    private String rightEye;
    @SerializedName("LeftEyeResult")
    @Expose
    private String leftEyeResult;
    @SerializedName("RightEyeResult")
    @Expose
    private String rightEyeResult;
    @SerializedName("Date")
    @Expose
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLeftEye() {
        return leftEye;
    }

    public void setLeftEye(String leftEye) {
        this.leftEye = leftEye;
    }

    public String getRightEye() {
        return rightEye;
    }

    public void setRightEye(String rightEye) {
        this.rightEye = rightEye;
    }

    public String getLeftEyeResult() {
        return leftEyeResult;
    }

    public void setLeftEyeResult(String leftEyeResult) {
        this.leftEyeResult = leftEyeResult;
    }

    public String getRightEyeResult() {
        return rightEyeResult;
    }

    public void setRightEyeResult(String rightEyeResult) {
        this.rightEyeResult = rightEyeResult;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
