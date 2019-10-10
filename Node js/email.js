var nodemailer = require('nodemailer')

var transporter = nodemailer.createTransport({
 service: 'gmail',
 auth: {
        user: 'diabeticretinopathycheckup@gmail.com',
        pass: 'diabeticretinopathy'
    }
});


const mailOptions = {
  from: 'diabeticretinopathycheckup@gmail.com', // sender address
  to: 'vechalekarr@gmail.com', // list of receivers
  subject: 'Registration Success', // Subject line
  html: "Thank You for Registering with Diabetic Retinopathy Android Application.<p>We look forward to giving you good service and checkup to you eyes</p><p>Thanks are Regards,</p><b>Diabetic Retinopathy Checkup Team</b>"// plain text body
};

transporter.sendMail(mailOptions, function (err, info) {
   if(err)
     console.log(err)
   else
     console.log(info);
});