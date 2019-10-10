var nodemail = require('./EmailSender.js')
var mysql = require('mysql')
var fs = require('fs')
var sendRegistrationEmail = nodemail.es_register;
var sendPredictionEmail = nodemail.es_predict
var sendForgotPasswordMail = nodemail.es_forgotpassword;
var imageSavePath = 'C:/Users/Udayan R Birajdar/Desktop/Assignments/College Assignments/BE_sem1/project/Training_images/downloaded_image/'

var connection = mysql.createConnection({
  host:'localhost',
  user:'root',
  password:"",
  database:"dr_project"
})

var ConnectToSQL = function(){
connection.connect((err) => {
  if (err){
    return err;
  };
  return"Connected to DB";
})
}


var insertUserData = function(Data){
  getUserData(Data.id,function(err,userData){
    if(userData.LeftEye == ""){
      data = [[Data.id,Data.LeftEye,Data.RightEye,Data.LeftEyeResult,Data.RightEyeResult,Data.Date]]
      connection.query('INSERT into user_data(id,LeftEye,RightEye,LeftEyeResult,RightEyeResult,Date) values ?',[data],function(err,res){
         if (err) throw err;
         console.log('Inserted to user_data');
       })
    }
    else {
        fs.unlink(imageSavePath+userData.LeftEye,function(err,d){})
        fs.unlink(imageSavePath+userData.RightEye,function(err,d){})

        console.log('Updated user_data');
        var sql = "Update user_data SET LeftEye='"+Data.LeftEye+"',RightEye='"+Data.RightEye+"',LeftEyeResult='"+Data.LeftEyeResult+"',RightEyeResult='"+Data.RightEyeResult+"',Date='"+Data.Date+"' where id='"+Data.id+"'"
        connection.query(sql,function(err,result){
          if (err) throw err;
        })
      }
  })

 }

var getUserData = function(id,callback){
  var sql = "SELECT * from user_data where id='"+id+"'"
  connection.query(sql,function(err,rows){
    if (err) throw err;
    else{
      if(rows.length == 0)
        {
          var emptyJson = {
              id:id,
              LeftEye:"",
              RightEye:"",
              LeftEyeResult:"",
              RightEyeResult:"",
              Date:""
            }
              callback(null,emptyJson)
        }
      else
        callback(null,rows[0])
    }
  })
}

var registerUser = function(userDetails,callback){
      sql = "SELECT * from login_data where FirstName='"+userDetails.FirstName+"' and LastName='"+userDetails.LastName+"'"
      connection.query(sql,function(err,rows){
        if(err) throw err;
        if(rows.length == 0){
          data = [[userDetails.FirstName,userDetails.LastName,userDetails.Password,userDetails.EmailID]]
          connection.query('INSERT into login_data(FirstName,LastName,Password,EmailID) values ?',[data],function(err,res){
            if (err) throw err;
              console.log("Inserted to login_data table");
              sendRegistrationEmail(userDetails.EmailID)
              callback(null,{status:"Registration Successful"})
          })
        }
        else{
              console.log("User Present cant register");
              callback(null,{status:"User_Present"})
            }
      })

    }


var login = function(loginDetails,callback){
  var sql = "SELECT * from login_data where FirstName='"+loginDetails.FirstName+"' and Password='"+loginDetails.Password+"'"
  connection.query(sql,function(err,rows){
    if (err)
      callback(err,null)


    if(rows.length == 0)
      callback(null,"-1")
    else
      callback(null,rows[0].id)

  })
}

var sendEmailOnId = function(id){
    var sql = "SELECT EmailID from login_data where id='"+id+"'"
    connection.query(sql,function(err,rows){
      if (err) throw err
      var email  = rows[0].EmailID;
      sql = "SELECT LeftEyeResult,RightEyeResult FROM `user_data` WHERE id='"+id+"'"
      connection.query(sql,function(err,rows){
            sendPredictionEmail(email,rows[0].LeftEyeResult,rows[0].RightEyeResult)
      })

    })
}


var sendForgotPasswordEmail = function(email,callback){
  var sql = "SELECT Password from login_data where EmailID='"+email+"'"
  connection.query(sql,function(err,rows){

      if(rows.length <=0 )
        callback("0")

      else if(rows.length>0){
        console.log(rows[0]);
      sendForgotPasswordMail(email,rows[0].Password,function(err,response){
        if(err)
        console.log("error");
        else {
          callback("1")
        }
      })
    }
  })

}

 module.exports={
   db_connection : ConnectToSQL,
   db_insert : insertUserData,
   db_getUserData : getUserData,
   db_registerUser : registerUser,
   db_login: login,
   db_sendEmailOnId : sendEmailOnId,
   db_sendForgotPasswordEmail : sendForgotPasswordEmail
 }
