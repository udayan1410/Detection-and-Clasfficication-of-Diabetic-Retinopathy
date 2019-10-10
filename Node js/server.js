var fs = require('fs')
var express = require('express')
var multer = require('multer')
var mysql = require('./mysql_db')
var bodyParser = require('body-parser')

var db_connection = mysql.db_connection;
var insertUserData = mysql.db_insert;
var getUserData = mysql.db_getUserData;
var registerUser = mysql.db_registerUser;
var login = mysql.db_login;
var sendEmailOnId = mysql.db_sendEmailOnId;
var sendForgotPasswordEmail = mysql.db_sendForgotPasswordEmail;


console.log(db_connection);
var app = express();
app.set('view engine','ejs')
app.use('/assets',express.static('assets'))
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }))

var meta_data = {name:"",extension:""}
var imageSavePath = 'C:/Users/Udayan R Birajdar/Desktop/Assignments/College Assignments/BE_sem1/project/Training_images/downloaded_image/'
var storage = multer.diskStorage({
  destination : function(req,file,cb){
      cb(null,imageSavePath)
  },
  filename : function(req,file,cb){
      var originalFileName = file.originalname;
      var ex = originalFileName.split(".")
      meta_data.name= ex[0]
      meta_data.extension = ex[ex.length-1]
      cb(null,ex[0]+"."+ex[ex.length-1])
  }
})
var uploadImage = multer({storage:storage})

app.post('/uploadImage',uploadImage.any(),function(req,res){
  console.log(meta_data);
  if(meta_data.extension == "png" || meta_data.extension == "jpg" || meta_data.extension == "jpeg")
  {
    console.log("Upload Success");
    var status = {status:"OK"}
    res.send(JSON.stringify(status))
    }
  else {
    console.log("Upload Fail");
    fs.unlink(imageSavePath+meta_data.name+"."+meta_data.extension,function(err,d){})
    res.send("Please upload images only")
  }

})

app.post('/imageData',function(req,res){
  var receivedData = req.body
  insertUserData(receivedData)
  res.send(JSON.stringify({status:"Success"}))
})

app.post('/register',function(req,res){
  var userDetails = req.body;
  console.log(userDetails);
  registerUser(userDetails,function(err,status){
  res.send(JSON.stringify(status))
  })
})

app.post('/login',function(req,res){
  var loginDetails = req.body;
  console.log(loginDetails);
  login(loginDetails,function(err,id){
    res.send(JSON.stringify({id:id}))
  })
})

app.post('/getUserDataFromID',function(req,res){
  var userId = req.body.id;
  getUserData(userId,function(err,userData){
    res.send(JSON.stringify(userData))
  })
})

app.post('/sendEmail',function(req,res){
  sendEmailOnId(req.body.id);
  res.send(JSON.stringify({status:"ok"}))
})

app.post('/forgotPassword',function(req,res){
  console.log(req.body);
  sendForgotPasswordEmail(req.body.emailID,function(val){
    if(val == 0){
      res.send(JSON.stringify({status:"Please Check Email ID"}))}
    else {
      res.send(JSON.stringify({status:"Success"}))
    }
  })

})


app.listen(8001,'192.168.0.104')
