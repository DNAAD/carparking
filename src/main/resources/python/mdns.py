from zeroconf import ServiceBrowser, Zeroconf

import cv2
import requests
import paho.mqtt.client as mqtt
import threading

deviceId = 1;
mqtt_image = False
sessionId =""
# The callback for when the client receives a CONNACK response from the server.
def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))

    # Subscribing in on_connect() means that if we lose the connection and
    # reconnect then subscriptions will be renewed.
    client.subscribe("$SYS/#")
    client.subscribe("camera/1")



    # The callback for when a PUBLISH message is received from the server.
def on_message(client, userdata, msg):
    global mqtt_image
    global sessionId
    print("topic   ")
    mqtt_image =True
    sessionId = str(msg.payload, encoding = "utf-8");
    print ("收到 "+sessionId)

    print(msg.topic+" "+str(msg.payload))
class MyListener:




    def __init__(self, cap):
        self.cap = cap


    def post_image(self, url_,sessionId):
        url = 'http://'+url_+'/upload'
        # 要上传的文件
        files = {'file': ('1.jpg', open('e:/fangjian2.jpg', 'rb'))
                }     #显式的设置文件名
        # post携带的数据
        data = {'sessionId':sessionId,'deviceId':deviceId}
        r = requests.post(url, files=files, data = data)

        print (r)



    def get_image(self,url_):
        global mqtt_image
        global sessionId

        # ret, frame = cap.read()



        #cv2.imshow('frame', frame)
        while(True):
        # 获取一帧
            ret, frame = cap.read()
            # 将这帧转换为灰度图
            gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

            cv2.imshow('frame', gray)
            if mqtt_image:
                cv2.imwrite("e:/fangjian2.jpg", frame)
                self.post_image(url_,sessionId)
                mqtt_image = False;


            if cv2.waitKey(1) == ord('w'):
                cv2.imwrite("e:/fangjian2.jpg", frame)
                self.post_image(url_,sessionId)
            if cv2.waitKey(1) == ord('q'):
                break

        #break



    def remove_service(self, zeroconf, type, name):
        print("Service %s removed" % (name,))

    def add_service(self, zeroconf, type, name):
        info = zeroconf.get_service_info(type, name)
        print("Service %s added, service info: %s" % (name, info.server))
        #url = info.server
        self.get_image(info.server)


def printHello():
    global client
    print("start" )

    client.publish("health", "deviceId#1")
    timer = threading.Timer(5.8,printHello)
    timer.start()



client = mqtt.Client()
client.on_connect = on_connect
client.on_message = on_message

client.connect("localhost", 1883, 60)
threading.Timer(5, printHello).start()
#client.subscribe("house/bulbs/bulb1", qos=0)
print("Connected   ")
#client.loop_forever()
client.loop_start()

# Blocking call that processes network traffic, dispatches callbacks and
# handles reconnecting.
# Other loop*() functions are available that give a threaded interface and a
# manual interface.
#client.loop_forever()

cap = cv2.VideoCapture(0)
zeroconf = Zeroconf()
listener = MyListener(cap)
browser = ServiceBrowser(zeroconf, "_yulinmei._tcp.local.", listener)













try:

    input("Press enter to exit...\n\n")
finally:
    zeroconf.close()
    cap.release()
    cv2.destroyAllWindows()