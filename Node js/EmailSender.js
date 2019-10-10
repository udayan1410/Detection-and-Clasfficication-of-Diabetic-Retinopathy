var nodemailer = require('nodemailer')


var transporter = nodemailer.createTransport({
 service: 'gmail',
 auth: {
        user: 'diabeticretinopathycheckup@gmail.com',
        pass: 'diabeticretinopathy'
    }
});

var data = {
  from: 'diabeticretinopathycheckup@gmail.com',
};



var sendRegistrationEmail = function(email){

  console.log("user email =  "+email);
  data.to = email;
  data.subject = "Registeration Successful"
  data.html = "Thank You for Registering with Diabetic Retinopathy Android Application.<p>We look forward to giving you good service and checkup to you eyes</p><p>Thanks are Regards,</p><b>Diabetic Retinopathy Checkup Team</b>"
  transporter.sendMail(data, function (err, info) {
   if(err)
     console.log(err)
   else
     console.log(info);
});
}

var sendPredictionEmail = function(email,LeftEyeResult,RightEyeResult){
  console.log("user email =  "+email+LeftEyeResult+RightEyeResult);


  var strleft = getStringLabel(LeftEyeResult)
  var strright = getStringLabel(RightEyeResult)

  data.to= email
  data.subject = "Diabetic Retinopathy Checkup Results"
  data.html = "Thank You for using our service of Diabetic Retinopathy Checkup.<p>Following are you results : </p><p><b>Left Eye Result = "+strleft+"</b></p><p><b>Right Eye Result = "+strright+"</b></p><p>Thanks and Regards,</p><p>Diabetic Retinopathy Checkup Team</p>"

  transporter.sendMail(data, function (err, info) {
   if(err)
     console.log(err)
   else
     console.log(info);
});
}

var sendForgotPasswordMail = function(email,password,callback){
  data.to = email
  data.subject = " Diabetic Retinopathy Forgot Password"
  data.html="Thank You for using our service of Diabetic Retinopathy Checkup.<p>Following is your password :<b>"+password+"</b></p><p>Thanks and Regards,</p><p>Diabetic Retinopathy Checkup Team</p>"
  transporter.sendMail(data, function (err, info) {
   if(err)
     console.log(err)
   else
     callback(null,info)
  });

}


var getStringLabel = function(EyeResult){

  switch (EyeResult) {
    case "0":
                return "No DR";
                break;

            case "1":
                return "Mild DR";
                break;

            case "2":
                return "Medium DR";
                break;

            case "3":
                return "Severe DR";
                break;
  }
}



module.exports ={
  es_register : sendRegistrationEmail,
  es_predict : sendPredictionEmail,
  es_forgotpassword:sendForgotPasswordMail
}
