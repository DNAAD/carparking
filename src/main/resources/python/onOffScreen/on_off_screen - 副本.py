# coding:utf-8
import cv2
import numpy as np
import subprocess
#from MyCvUtils import MyCvUtils

#datapath = "D:/imgData/video/"



import threading


#Timer（定时器）是Thread的派生类，
#用于在指定时间后调用一个方法。
def func():
    global Motion
    global display
    print ('STOP screem timer comming!')
    out_bytes = subprocess.check_output(['vcgencmd','display_power','0'])
    display = False #print 'hello timer!'
    Motion = 0


timer = threading.Timer(20, func)
timer.start()

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


bs = cv2.createBackgroundSubtractorKNN(detectShadows=True)
#camera = cv2.VideoCapture("http://192.168.100.13:8081/")
camera = cv2.VideoCapture(0)


ret, frame = camera.read()

display =False;
while True:
    ret, frame = camera.read()
    # 计算前景掩码，包含 前景的白色值 以及 阴影的灰色值
    fgmask = bs.apply(frame)
    # 前景区域二值化，将非白色（0-244）的非前景区域（包含背景以及阴影）均设为0，前景的白色（244-255）设置为255
    th = cv2.threshold(fgmask.copy(), 244, 255, cv2.THRESH_BINARY)[1]
    # 前景区域形态学处理
    th = cv2.erode(th, cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (3, 3)), iterations=2)
    dilated = cv2.dilate(th, cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (8, 3)), iterations=2)
    # 绘制前景图像的轮廓矩形
    image, contours, hier = cv2.findContours(dilated, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    for c in contours:
        #       对轮廓设置最小区域，对检测结果降噪
        if cv2.contourArea(c) > 1000:
 
            upMotion()

            (x, y, w, h) = cv2.boundingRect(c)
            cv2.rectangle(frame, (x, y), (x + w, y + h), (255, 255, 0), 2)

    #cv2.imshow("mog", fgmask)
    #cv2.imshow("thresh", th)
    #cv2.imshow("diff", frame & cv2.cvtColor(fgmask, cv2.COLOR_GRAY2BGR))
    cv2.imshow("detection", frame)

    if (cv2.waitKey(30) & 0xFF) == 27:
        break
    if (cv2.waitKey(30) & 0xFF) == ord('q'):
        break

camera.release()
cv2.destroyAllWindows()


