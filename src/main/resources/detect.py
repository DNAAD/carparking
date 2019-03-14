#pip install demjson
#pip install urllib2

import demjson
import sys
import json
import numpy as np

#import urllib2
# python 2.7
import urllib.request
#python 3
import json
import cv2
print (sys.argv[0])
url='http://localhost:10080/configuration/cameras'
#url=sys.argv[2]

print ('1:'+sys.argv[0])
print ('2:'+sys.argv[1])


#html = urllib2.urlopen('sys.argv[1]')
#html = urllib.request.urlopen(sys.argv[1])
html = urllib.request.urlopen(url)
read=html.read()
print(read)
hjson = json.loads(read)
print(hjson)

#print (sys.argv[2])
#print sys.argv[3]
#print sys.argv[4]
#print (demjson.decode(sys.argv[0]))
#data = json.loads(sys.argv[0])
#print (data.rstpUrl)
path = None
for j in hjson:
    print (j['rstpUrl']);
    path = (j['path']);
    cap = cv2.VideoCapture(j['rstpUrl'])
    cap.set(cv2.CAP_PROP_FRAME_WIDTH,300)
    cap.set(cv2.CAP_PROP_FRAME_HEIGHT,300)
    print (cap.isOpened())

print (path)
if(len(hjson) ==0):
    print ("find no camera")

#path = 'e:/a'
c=1
timeF = 60
while 1:
    if cap.isOpened():
        ret,frame = cap.read()

        if(ret):
            if(c%timeF == 0):
                print (path)
                cv2.imwrite(path+ '.jpg',frame)
            c = c + 1
        else:
            print("disconnected!")

    cv2.waitKey(1)





