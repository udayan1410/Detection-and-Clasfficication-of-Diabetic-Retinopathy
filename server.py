import os
import io
from keras.models import load_model
from numpy import *
from PIL import Image
import urllib.request
from socket import *
import numpy as np
import time
import boto3
import json
from multiprocessing.dummy import Pool as ThreadPool
from botocore.client import Config
import mysql.connector
from operator import itemgetter

path_predict = "C:\\Users\\Udayan R Birajdar\\Desktop\\Assignments\\College Assignments\\BE_sem1\\project\\Training_images\\one"
downloaded_image_path = "C:\\Users\\Udayan R Birajdar\\Desktop\\Assignments\\College Assignments\\BE_sem1\\project\\Training_images\\downloaded_image\\"
constFactor = 226 / 2
prediction_path = "C:\\Users\\Udayan R Birajdar\\Desktop\\Assignments\\College Assignments\\BE_sem1\\project\\Training_images\\predict\\"

weights = []
def getOutputFromWeights(temp,listing,weights):
    weights = sorted(weights, key=itemgetter(1))
    iterator = 0
    output = []
    for item in listing:
        output.append([item, temp[iterator]])
        iterator += 1;

    for item in output:
        for imagename in weights:
            if imagename[0] == item[0]:
                imagename[2] = item[1]

    count4 = 0
    count3 = 0
    count2 = 0
    count1 = 0

    for item in weights:
        if (item[1] == 4) and (item[2] == 1):
            count4 += 1
        if (item[1] == 3) and (item[2] == 1):
            count3 += 1
        if (item[1] == 2) and (item[2] == 1):
            count2 += 1
        if (item[1] == 1) and (item[2] == 1):
            count1 += 1

    if(count4>=4) or (count3>=4):
        return 3
    if(count3>=3) or (count2>=4):
        return 2
    if(count2>=3) or (count1>=4):
        return 1
    else:
        return 0



def CNN_predict():
    listing = os.listdir(prediction_path)
    immatrix = array([array(Image.open(prediction_path+ "\\" + im2)).flatten() for im2 in listing], 'f')
    X = immatrix

    X_train = X

    X_train = X_train.reshape(X_train.shape[0], 227, 227, 3)

    X_train = X_train.astype('float32')

    X_train /= 255

    this_dir = os.path.dirname(os.path.realpath("C:\\Users\\Udayan R Birajdar\\PycharmProjects\\CNN\\venv\\Lib"))
    file_path = os.path.join(this_dir, 'model_udayan.hdf5')
    model = load_model(file_path)

    temp = model.predict_classes(X_train[0:])
    return getOutputFromWeights(temp,listing,weights)


def cropFourthQuadrant(width,height,img,file,eye):
    if width%2!=0 or height%2!=0:
        if width%2==0 :
            height = height-1
        else:
            width = width-1

    widthHalf = (width/2)
    heightHalf = (height/2)

    i=0
    while i<4:
        cropped = img.crop((widthHalf,heightHalf,widthHalf+227,heightHalf+227))
        cropped2 = img.crop((widthHalf,heightHalf+227,widthHalf+227,heightHalf+(2*227)))
        cropped3 = img.crop((widthHalf,heightHalf+(2*227),widthHalf+227, heightHalf+(3*227)))
        widthHalf = widthHalf+int(height/10)
        cropped.save(prediction_path+"fourth_quadrant1("+str(i)+")"+file)
        cropped2.save(prediction_path + "fourth_quadrant2(" + str(i) + ")" + file)
        cropped3.save(prediction_path + "fourth_quadrant(3" + str(i) + ")" + file)
        if (eye == "right"):
            if (i == 0):
                weights.append(["fourth_quadrant1(" + str(i) + ")" +file, 4, 0])
                weights.append(["fourth_quadrant2(" + str(i) + ")" + file, 3, 0])
                weights.append(["fourth_quadrant3(" + str(i) + ")" + file, 2, 0])
            if (i == 1):
                weights.append(["fourth_quadrant1(" + str(i) + ")" +file, 3, 0])
                weights.append(["fourth_quadrant2(" + str(i) + ")" + file, 2, 0])
                weights.append(["fourth_quadrant3(" + str(i) + ")" + file, 1, 0])
            if (i == 2):
                weights.append(["fourth_quadrant1(" + str(i) + ")" +file, 3, 0])
                weights.append(["fourth_quadrant2(" + str(i) + ")" + file, 2, 0])
                weights.append(["fourth_quadrant(3" + str(i) + ")" + file, 1, 0])
            if (i == 3):
                weights.append(["fourth_quadrant1(" + str(i) + ")" +file, 4, 0])
                weights.append(["fourth_quadrant2(" + str(i) + ")" + file, 2, 0])
                weights.append(["fourth_quadrant3(" + str(i) + ")" + file, 1, 0])

        if (eye == "left"):
            if (i == 0):
                weights.append(["fourth_quadrant1(" + str(i) + ")" +file, 4, 0])
                weights.append(["fourth_quadrant2(" + str(i) + ")" + file, 3, 0])
                weights.append(["fourth_quadrant3(" + str(i) + ")" + file, 2, 0])
            if (i == 1):
                weights.append(["fourth_quadrant1(" + str(i) + ")" +file, 3, 0])
                weights.append(["fourth_quadrant2(" + str(i) + ")" + file, 2, 0])
                weights.append(["fourth_quadrant3(" + str(i) + ")" + file, 1, 0])
            if (i == 2):
                weights.append(["fourth_quadrant1(" + str(i) + ")" +file, 2, 0])
                weights.append(["fourth_quadrant2(" + str(i) + ")" + file, 1, 0])
                weights.append(["fourth_quadrant3(" + str(i) + ")" + file, 1, 0])
            if (i == 3):
                weights.append(["fourth_quadrant1(" + str(i) + ")" +file, 1, 0])
                weights.append(["fourth_quadrant2(" + str(i) + ")" + file, 1, 0])
                weights.append(["fourth_quadrant3(" + str(i) + ")" + file, 1, 0])

        i=i+1

def cropThirdQuadrant(width,height,img,file,eye):
    if width%2!=0 or height%2!=0:
        if width%2==0 :
            height = height-1
        else:
            width = width-1

    widthHalf = (width/2)
    heightHalf = (height/2)

    i=0
    while i<4:
        cropped = img.crop((widthHalf-227,heightHalf,widthHalf,heightHalf+227))
        cropped2 = img.crop((widthHalf-227,heightHalf+227,widthHalf,heightHalf+(2*227)))
        cropped3 = img.crop((widthHalf-227,heightHalf+(2*227),widthHalf,heightHalf+(3*227)))
        widthHalf = widthHalf-int(height/10)
        cropped.save(prediction_path+"third_quadrant1("+str(i)+")"+file)
        cropped2.save(prediction_path + "third_quadrant2(" + str(i) + ")" + file)
        cropped3.save(prediction_path + "third_quadrant(3" + str(i) + ")" + file)

        if (eye == "right"):
            if (i == 0):
                weights.append(["third_quadrant1(" + str(i) + ")" + file, 4, 0])
                weights.append(["third_quadrant2(" + str(i) + ")" + file, 3, 0])
                weights.append(["third_quadrant3(" + str(i) + ")" + file, 2, 0])
            if (i == 1):
                weights.append(["third_quadrant1(" + str(i) + ")" + file, 3, 0])
                weights.append(["third_quadrant2(" + str(i) + ")" + file, 2, 0])
                weights.append(["third_quadrant3(" + str(i) + ")" + file, 1, 0])
            if (i == 2):
                weights.append(["third_quadrant1(" + str(i) + ")" + file, 2, 0])
                weights.append(["third_quadrant2(" + str(i) + ")" + file, 1, 0])
                weights.append(["third_quadrant3(" + str(i) + ")" + file, 1, 0])
            if (i == 3):
                weights.append(["third_quadrant1(" + str(i) + ")" + file, 1, 0])
                weights.append(["third_quadrant2(" + str(i) + ")" + file, 1, 0])
                weights.append(["third_quadrant3(" + str(i) + ")" + file, 1, 0])

        if (eye == "left"):
            if (i == 0):
                weights.append(["third_quadrant1(" + str(i) + ")" + file, 4, 0])
                weights.append(["third_quadrant2(" + str(i) + ")" + file, 3, 0])
                weights.append(["third_quadrant3(" + str(i) + ")" + file, 2, 0])
            if (i == 1):
                weights.append(["third_quadrant1(" + str(i) + ")" + file, 3, 0])
                weights.append(["third_quadrant2(" + str(i) + ")" + file, 2, 0])
                weights.append(["third_quadrant3(" + str(i) + ")" + file, 1, 0])
            if (i == 2):
                weights.append(["third_quadrant1(" + str(i) + ")" + file, 3, 0])
                weights.append(["third_quadrant2(" + str(i) + ")" + file, 2, 0])
                weights.append(["third_quadrant3(" + str(i) + ")" + file, 1, 0])
            if (i == 3):
                weights.append(["third_quadrant1(" + str(i) + ")" + file, 4, 0])
                weights.append(["third_quadrant2(" + str(i) + ")" + file, 2, 0])
                weights.append(["third_quadrant3(" + str(i) + ")" + file, 1, 0])

        i=i+1

def cropSecondQuadrant(width,height,img,file,eye):
    if width%2!=0 or height%2!=0:
        if width%2==0 :
            height = height-1
        else:
            width = width-1

    widthHalf = (width/2)
    heightHalf = (height/2)

    i=0
    while i<4:
        cropped = img.crop((widthHalf-227,heightHalf-227,widthHalf,heightHalf))
        cropped2 = img.crop((widthHalf-227,heightHalf-(2*227),widthHalf,heightHalf-227))
        cropped3 = img.crop((widthHalf-227,heightHalf-(3*227),widthHalf,heightHalf-(2*227)))
        widthHalf = widthHalf-int((height/10))
        cropped.save(prediction_path + "second_quadrant1(" + str(i) + ")" + file)
        cropped2.save(prediction_path + "second_quadrant2(" + str(i) + ")" + file)
        cropped3.save(prediction_path + "second_quadrant3(" + str(i) + ")" + file)

        if (eye == "right"):
            if (i == 0):
                weights.append(["second_quadrant1(" + str(i) + ")" + file, 4, 0])
                weights.append(["second_quadrant2(" + str(i) + ")" + file, 3, 0])
                weights.append(["second_quadrant3(" + str(i) + ")" + file, 2, 0])
            if (i == 1):
                weights.append(["second_quadrant1(" + str(i) + ")" + file, 3, 0])
                weights.append(["second_quadrant2(" + str(i) + ")" + file, 2, 0])
                weights.append(["second_quadrant3(" + str(i) + ")" + file, 1, 0])
            if (i == 2):
                weights.append(["second_quadrant1(" + str(i) + ")" + file, 2, 0])
                weights.append(["second_quadrant2(" + str(i) + ")" + file, 1, 0])
                weights.append(["second_quadrant3(" + str(i) + ")" + file, 1, 0])
            if (i == 3):
                weights.append(["second_quadrant1(" + str(i) + ")" + file, 1, 0])
                weights.append(["second_quadrant2(" + str(i) + ")" + file, 1, 0])
                weights.append(["second_quadrant3(" + str(i) + ")" + file, 1, 0])

        if (eye == "left"):
            if (i == 0):
                weights.append(["second_quadrant1(" + str(i) + ")" + file, 4, 0])
                weights.append(["second_quadrant2(" + str(i) + ")" + file, 3, 0])
                weights.append(["second_quadrant3(" + str(i) + ")" + file, 2, 0])
            if (i == 1):
                weights.append(["second_quadrant1(" + str(i) + ")" + file, 3, 0])
                weights.append(["second_quadrant2(" + str(i) + ")" + file, 2, 0])
                weights.append(["second_quadrant3(" + str(i) + ")" + file, 1, 0])
            if (i == 2):
                weights.append(["second_quadrant1(" + str(i) + ")" + file, 3, 0])
                weights.append(["second_quadrant2(" + str(i) + ")" + file, 2, 0])
                weights.append(["second_quadrant3(" + str(i) + ")" + file, 1, 0])
            if (i == 3):
                weights.append(["second_quadrant1(" + str(i) + ")" + file, 4, 0])
                weights.append(["second_quadrant2(" + str(i) + ")" + file, 2, 0])
                weights.append(["second_quadrant3(" + str(i) + ")" + file, 1, 0])

        i = i + 1


def cropFirstQuadrant(width,height,img,file,eye):
    if width%2!=0 or height%2!=0:
        if width%2==0 :
            height = height-1
        else:
            width = width-1

    widthHalf = (width/2)
    heightHalf = (height/2)

    i=0
    while i<4:
        cropped = img.crop((widthHalf,heightHalf-227,widthHalf+227,heightHalf))
        cropped2 = img.crop((widthHalf,heightHalf-(227*2),widthHalf+227,heightHalf-227))
        cropped3 = img.crop((widthHalf,heightHalf-(227*3),widthHalf+227,heightHalf-(2*227)))
        widthHalf = widthHalf+int((height/10))
        cropped.save(prediction_path + "first_quadrant1(" + str(i) + ")" + file)
        cropped2.save(prediction_path + "first_quadrant2(" + str(i) + ")" + file)
        cropped3.save(prediction_path + "first_quadrant3(" + str(i) + ")" + file)

        if (eye == "right"):
            if (i == 0):
                weights.append(["first_quadrant1(" + str(i) + ")" + file, 4, 0])
                weights.append(["first_quadrant2(" + str(i) + ")" + file, 3, 0])
                weights.append(["first_quadrant3(" + str(i) + ")" + file, 2, 0])
            if (i == 1):
                weights.append(["first_quadrant1(" + str(i) + ")" + file, 3, 0])
                weights.append(["first_quadrant2(" + str(i) + ")" + file, 2, 0])
                weights.append(["first_quadrant3(" + str(i) + ")" + file, 1, 0])
            if (i == 2):
                weights.append(["first_quadrant1(" + str(i) + ")" + file, 3, 0])
                weights.append(["first_quadrant2(" + str(i) + ")" + file, 2, 0])
                weights.append(["first_quadrant3(" + str(i) + ")" + file, 1, 0])
            if (i == 3):
                weights.append(["first_quadrant1(" + str(i) + ")" + file, 4, 0])
                weights.append(["first_quadrant2(" + str(i) + ")" + file, 2, 0])
                weights.append(["first_quadrant3(" + str(i) + ")" + file, 1, 0])

        if (eye == "left"):
            if (i == 0):
                weights.append(["first_quadrant1(" + str(i) + ")" + file, 4, 0])
                weights.append(["first_quadrant2(" + str(i) + ")" + file, 3, 0])
                weights.append(["first_quadrant3(" + str(i) + ")" + file, 2, 0])
            if (i == 1):
                weights.append(["first_quadrant1(" + str(i) + ")" + file, 3, 0])
                weights.append(["first_quadrant2(" + str(i) + ")" + file, 2, 0])
                weights.append(["first_quadrant3(" + str(i) + ")" + file, 1, 0])
            if (i == 2):
                weights.append(["first_quadrant1(" + str(i) + ")" + file, 2, 0])
                weights.append(["first_quadrant2(" + str(i) + ")" + file, 1, 0])
                weights.append(["first_quadrant3(" + str(i) + ")" + file, 1, 0])
            if (i == 3):
                weights.append(["first_quadrant1(" + str(i) + ")" + file, 1, 0])
                weights.append(["first_quadrant2(" + str(i) + ")" + file, 1, 0])
                weights.append(["first_quadrant3(" + str(i) + ")" + file, 1, 0])

        i = i + 1



def deletecroppedFiles(path):
    filesToBeDelted = os.listdir(path)

    for item in filesToBeDelted:
        os.remove(path+item)

def cropobtainedImage(data,eye):

    img = Image.open(downloaded_image_path+data)
    width = img.size[0]
    height = img.size[1]

    for file in os.listdir(downloaded_image_path):
        if file.endswith(data):
            print("File = " + str(file))
            break

    cropFourthQuadrant(width, height,img,"fundus_eye"+data,eye)
    cropThirdQuadrant(width, height,img,"fundus_eye"+data,eye)
    cropFirstQuadrant(width, height,img,"fundus_eye"+data,eye)
    cropSecondQuadrant(width, height,img,"fundus_eye"+data,eye)



def updatedb(id,output,eye,cursor):
    if eye == "left":
        cursor.execute("UPDATE user_data set LeftEyeResult = '"+str(output)+"' where id='"+str(id)+"'")
        mydb.commit()
    else:
        cursor.execute("UPDATE user_data set RightEyeResult = '" +str(output)+ "' where id='" +str(id)+ "'")
        mydb.commit()

def getEyeNameFromDB(id,cursor):
    cursor.execute("SELECT LeftEye,RightEye from user_data where id='"+id+"'")
    result = cursor.fetchall()
    res = [result[0][0], result[0][1]]
    return res



if __name__ == '__main__':

    HOST = "192.168.0.104"
    PORT = 8001
    s = socket(AF_INET, SOCK_STREAM)

    print("socket Created")

    try:
        s.bind((HOST, PORT))
    except socket.error as error:
        print("failed")

    print("Socket Bind success")

    mydb = mysql.connector.connect(
        host="localhost",
        user="root",
        password="",
        database="dr_project"
    )
    cursor = mydb.cursor()


    while True:
        print("Waiting for connections")

        s.listen(100)

        conn, addr = s.accept()

        print("Connected by: " , addr)

        data = conn.recv(1024)

        # getAWSData(data)
        print("String data = "+data.decode("utf-8"))

        id = (data.decode("utf-8"))

       

        eyeName = getEyeNameFromDB(id,cursor)

        print(eyeName)

        #cropobtainedImage(data.decode("utf-8"))
        cropobtainedImage(eyeName[0],"left")

        a = CNN_predict()

        print("A = " + str(a))

        updatedb(id, a, "left", cursor)

        deletecroppedFiles(prediction_path)

        weights = []

        cropobtainedImage(eyeName[1],"right")

        b = CNN_predict()

        print("B = " + str(b))

        updatedb(id, b, "right", cursor)

        deletecroppedFiles(prediction_path)

        final_result = str(a)+"/"+str(b)

        conn.send(str(final_result).encode('utf8'))

        conn.close()
