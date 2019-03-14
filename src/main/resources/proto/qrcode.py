from __future__ import print_function

import numpy as np

import pyzbar.pyzbar as pyzbar

import cv2

import grpc_chanel
import test_pb2_grpc,test_pb2



def decode(im) : 
  # Find barcodes and QR codes
  decodedObjects = pyzbar.decode(im)
 
  # Print results
  for obj in decodedObjects:
    print('Type : ', obj.type)
    print('Data : ', obj.data,'\n')
    grpc_chanel.send(obj.data)
     
  return decodedObjects
 
 
# Display barcode and QR code location  
def display(im, decodedObjects):
 
  # Loop over all decoded objects
  for decodedObject in decodedObjects: 
    points = decodedObject.polygon
 
    # If the points do not form a quad, find convex hull
    if len(points) > 4 : 
      hull = cv2.convexHull(np.array([point for point in points], dtype=np.float32))
      hull = list(map(tuple, np.squeeze(hull)))
    else : 
      hull = points;
     
    # Number of points in the convex hull
    n = len(hull)
 
    # Draw the convext hull
    for j in range(0,n):
      cv2.line(im, hull[j], hull[ (j+1) % n], (255,0,0), 3)
 
  # Display results 
  cv2.imshow("Results", im);
  #cv2.waitKey(0);
 
   
# Main 
if __name__ == '__main__':
 
  # Read image
  #im = cv2.imread('qrcode.jpg')
  grpc_chanel.init()
  cap = cv2.VideoCapture("rtsp://admin:silence110@192.168.1.76:554/h264/ch1/main/av_stream")
  #cap = cv2.VideoCapture(0)

  #ret=cap.set(3,1280)
  #ret=cap.set(4,720)
  count = 1
  while(1):
    # get a frame
    count +=1
    ret, frame = cap.read()

    if count%40 ==1 :
      decodedObjects = decode(frame)
      display(frame, decodedObjects)
      print ("ddd")
    # show a frame
    cv2.imshow("capture", frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break
  cap.release()
  cv2.destroyAllWindows() 
