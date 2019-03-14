from __future__ import print_function

import pyzbar.pyzbar as pyzbar
import comunication
import test_pb2_grpc
import test_pb2
_HOST = 'localhost'
_PORT = '50051'
client = 0

def init():
    global client
    conn = comunication.insecure_channel(_HOST + ':' + _PORT)
    client = test_pb2_grpc.GreeterStub(channel=conn)





def decode(im) : 
  # Find barcodes and QR codes
  decodedObjects = pyzbar.decode(im)
 
  # Print results
  for obj in decodedObjects:
    print('Type : ', obj.type)
    print('Data : ', obj.data,'\n')
    run(obj.data)
     
  return decodedObjects
 
 
# Display barcode and QR code location  
def send(data):
  global client
  response = client.SayHello(test_pb2.HelloRequest(name="qrcode",sex=data))
  print("received: " + response.message)
 
