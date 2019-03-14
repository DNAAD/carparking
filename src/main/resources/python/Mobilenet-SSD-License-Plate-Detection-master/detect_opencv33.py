#coding=utf-8
from cv2 import dnn
import cv2
#import e2e

inWidth = 720
inHeight = 1024
WHRatio = inWidth / float(inHeight)
inScaleFactor = 0.007843
meanVal = 127.5

classNames = ('background',
              'plate')
net = dnn.readNetFromCaffe("MobileNetSSD_test.prototxt","lpr.caffemodel")

import time

def detect(cpp):
    frame_origin = cv2.imread(cpp)
    cv2.imshow("detection_or", frame_origin)
    frame=cv2.resize(frame_origin,(1024,720),interpolation=cv2.INTER_CUBIC)
    cv2.imshow("detection_res", frame)
    blob = dnn.blobFromImage(frame, inScaleFactor, (inWidth, inHeight), meanVal)
    net.setInput(blob)
    t0 = time.time()
    detections = net.forward()
    print (time.time() - t0)

    cols = frame.shape[1]
    rows = frame.shape[0]

##    if cols / float(rows) > WHRatio:
##        cropSize = (int(rows * WHRatio), rows)
##    else:
##        cropSize = (cols, int(cols / WHRatio))
##
##    y1 = (rows - cropSize[1]) // 2
##    y2 = y1 + cropSize[1]
##    x1 = (cols - cropSize[0]) // 2
##    x2 = x1 + cropSize[0]
##    frame = frame[y1:y2, x1:x2]
##
##    cols = frame.shape[1]
##    rows = frame.shape[0]

    for i in range(detections.shape[2]):
        confidence = detections[0, 0, i, 2]
        if confidence > 0.0:
            class_id = int(detections[0, 0, i, 1])

            xLeftBottom = int(detections[0, 0, i, 3] * cols)
            yLeftBottom = int(detections[0, 0, i, 4] * rows)
            xRightTop = int(detections[0, 0, i, 5] * cols)
            yRightTop = int(detections[0, 0, i, 6] * rows)

            cv2.rectangle(frame, (xLeftBottom, yLeftBottom), (xRightTop, yRightTop),
                          (0, 255, 255))

            image_sub = frame[yLeftBottom:yRightTop,xLeftBottom:xRightTop]
            print (confidence)
            print (yLeftBottom,yRightTop, xLeftBottom,xRightTop)
            # e2e.recognizeOne(image_sub)

            label = classNames[class_id] + ": " + str(confidence)
            labelSize, baseLine = cv2.getTextSize(label, cv2.FONT_HERSHEY_SIMPLEX, 0.5, 1)

            #cv2.rectangle(frame, (xLeftBottom, yLeftBottom - labelSize[1]),
            #               (xLeftBottom + labelSize[0], yLeftBottom + baseLine),
            #               (255, 255, 255), 2,cv2.FILLED)
            cv2.putText(frame, label, (xLeftBottom, yLeftBottom),
                        cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 0, 0))
        cv2.imshow("detection", frame)
    return frame
import os



        # break


        # Main 
if __name__ == '__main__':
 
  # Read image
  im = detect('images/8.jpg')

 

