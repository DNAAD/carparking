# coding:utf-8
import cv2
import numpy as np
import subprocess
#from MyCvUtils import MyCvUtils

#datapath = "D:/imgData/video/"

import threading
import sys
sys.path.insert(0,'..')
import comunication.grpc_chanel as grpc_chanel
import paho.mqtt.client as mqtt #import the client1
import time


#grpc_chanel.init()
#grpc_chanel.send("2","9999")

#Timer（定时器）是Thread的派生类，
#用于在指定时间后调用一个方法。
def func():
    global Motion
    global display
    print ('STOP screem timer comming!')
    #out_bytes = subprocess.check_output(['vcgencmd','display_power','0'])
    display = False #print 'hello timer!'
    Motion = 0




def upMotion():
    print ('hello timer!')
    global Motion
    global timer
    global display
    Motion += 1
    print(Motion)

    if(Motion%100==99):
        timer.cancel()
        timer = threading.Timer(600, func)
        timer.start()
        
    if(Motion > 2):
        
        if display == False:
            out_bytes = subprocess.check_output(['vcgencmd','display_power','1'])
            display = True
            ut_text = out_bytes.decode('utf-8')
            print(ut_text)





Motion = 0


timer = threading.Timer(15, func)
timer.start()

def on_message(client, userdata, message):
    global timer
    print("message received " ,str(message.payload.decode("utf-8")))
    print("message topic=",message.topic)
    print("message qos=",message.qos)
    print("message retain flag=",message.retain)
    command = str(message.payload.decode("utf-8"))

    if command == 'on':
        print("打开屏幕")
        timer.cancel()
        timer = threading.Timer(15, func)
        timer.start()


    if command == 'off':
        print("关闭屏幕")






broker_address="localhost"
#broker_address="iot.eclipse.org" #use external broker
client = mqtt.Client("Pcommand") #create new instance
client.connect(broker_address, 110, 60)   #connect to broker
client.on_message=on_message

client.loop_start() #start the loop
print("Subscribing to topic","house/bulbs/commad")
client.subscribe("house/bulbs/commad")


time.sleep(1000) # wait
client.loop_stop()




upMotion()






