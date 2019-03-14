__author__ = 'silence'

#E:\mei\backup\storage\carparking\src\main\resources\proto>python -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. ./test.proto
import grpc
import test_pb2_grpc,test_pb2
import threading


import ctypes

import time
import inspect

import queue

q = queue.Queue()

def gen():
    while 1:
        i = input("\nEnter a number or 'q' to quit: \n")
        if i == "q":
            break
        try:
            num = int(i)
        except ValueError:
            continue
        yield test_pb2_grpc.Request(num=num)
        time.sleep(0.1)


# def run():
#     channel = grpc.insecure_channel('localhost:50051')
#     stub = test_pb2_grpc.GreeterStub(channel)
#     it = stub.Subscribe(gen())
#     try:
#         for r in it:
#             print(f"Prime factor = {r.result}")
#         except grpc._channel._Rendezvous as err:
#         print(err)

def generate_route():
    for _ in range(0, 1):
        random_feature = test_pb2.Topic(clientName="alexa", type=1)
        print("Visiting point %s" % random_feature.clientName)
        yield random_feature

def consume_route(responses):
    for response in responses:
        print(response)







def generate_route_queue_thread(q):
    while True:
        random_feature = test_pb2.Topic(clientName="alexa", type=1)
        print("Visiting point_______________________ %s" % random_feature.clientName)
        time.sleep(2)
        q.put(random_feature)

def generate_route_queue(q):
    while True:
        time.sleep(2)
        random_feature = test_pb2.Topic(clientName="alexa", type=1)

        yield random_feature # q.get()
        print("Queue get ")


def generate_route():
    for _ in range(0, 1):
        random_feature = test_pb2.Topic(clientName="alexa", type=1)
        print("Visiting point %s" % random_feature.clientName)
        yield random_feature


def _async_raise(tid, exctype):
    """raises the exception, performs cleanup if needed"""
    tid = ctypes.c_long(tid)
    if not inspect.isclass(exctype):
        exctype = type(exctype)
    res = ctypes.pythonapi.PyThreadState_SetAsyncExc(tid, ctypes.py_object(exctype))
    if res == 0:
        raise ValueError("invalid thread id")
    elif res != 1:
        # """if it returns a number greater than one, you're in trouble,
        # and you should call it again with exc=NULL to revert the effect"""
        ctypes.pythonapi.PyThreadState_SetAsyncExc(tid, None)
        raise SystemError("PyThreadState_SetAsyncExc failed")


def stop_thread(thread):
    _async_raise(thread.ident, SystemExit)


if __name__ == '__main__':

    print(generate_route())

    channel = grpc.insecure_channel('localhost:50051')
    my_channel_ready_future = grpc.channel_ready_future(channel)
#<do some other stuff>
    try:
       my_channel_ready_future.result(timeout=10) #秒
    except grpc.FutureTimeoutError:
        # <handle channel-did-not-connect>成功
         print('失败')
    else:
        print('成功')# <make use of connected channel>
        stub = test_pb2_grpc.GreeterStub(channel)

        t_quque = threading.Thread(target=generate_route_queue_thread,args=(q,))
        t_quque.start()
        responses = stub.Subscribe(generate_route_queue(q))
    #consume_route(responses)
        #t = threading.Thread(target=consume_route, args = (responses,))
        #t.start()
        print('eeeeeeeeee')

        #stop_thread(t)
        t_quque.join()
        #t.join()

