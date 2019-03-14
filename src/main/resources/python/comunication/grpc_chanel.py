import grpc
import comunication.test_pb2_grpc as test_pb2_grpc
import comunication.test_pb2 as test_pb2
_HOST = 'localhost'
_PORT = '50051'
client = 0

def init():
    global client
    conn = grpc.insecure_channel(_HOST + ':' + _PORT)
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
def send(type,data):
  global client
  response = client.SayHello(test_pb2.HelloRequest(name=type,sex=data))
  print("received: " + response.message)
 
