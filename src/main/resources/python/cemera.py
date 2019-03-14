# created by Huang Lu
# 27/08/2016 17:05:45 
# Department of EE, Tsinghua Univ.

import cv2
import numpy as np
# 
cap = cv2.VideoCapture("rtsp://admin:silence110@192.168.1.76:554/h264/ch1/main/av_stream")
while(1):
    # get a frame
    ret, frame = cap.read()
    # show a frame
    cv2.imshow("capture", frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break
cap.release()
cv2.destroyAllWindows() 
