package com.coalvalue.rpc.grpc;


import com.coalvalue.notification.liveEvent.BarDecodedCommandE;
import com.coalvalue.notification.liveEvent.EVENTS;
import com.coalvalue.notification.liveEvent.HardwareEventInvoker;
import com.coalvalue.notification.liveEvent.ObjectedDecodedCommandE;
import com.coalvalue.python.ObjectDetectionDeepLearningPythonService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@Component
public class HelloWorldServer {


    @Autowired
    HardwareEventInvoker hrdwrEvntInvokerObj ;


    @Autowired
    ObjectDetectionDeepLearningPythonService objectDetectionDeepLearningPythonService ;

    private int port = 50051;
    private Server server;

    public void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build()
                .start();

        System.out.println("service start...");

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                HelloWorldServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    // block 一直到退出程序 
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {

        final HelloWorldServer server = new HelloWorldServer();
        server.start();
        server.blockUntilShutdown();
    }


    // 实现 定义一个实现服务接口的类 
    private class GreeterImpl extends GreeterGrpc.GreeterImplBase {


        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            System.out.println("service:"+req.getName());
            if(req.getName().equals("qrcode")){
                Map map = new HashMap();
                map.put("data",req.getSex());
                System.out.println("qrcode:"+req.getSex());

                hrdwrEvntInvokerObj.handleEvent(EVENTS.USB_ATTACHED,new BarDecodedCommandE(map));
            }


            if(req.getName().equals("1")){
                Map map = new HashMap();
                map.put("data",req.getSex());

                System.out.println("service:"+req.getSex());
                ObjectedDecodedCommandE objectedDecodedCommandE = new ObjectedDecodedCommandE(map);
                objectedDecodedCommandE.setType(req.getName());
                objectedDecodedCommandE.setData(req.getSex());
                hrdwrEvntInvokerObj.handleEvent(EVENTS.CAMERA_OBJECT_DETECTED,objectedDecodedCommandE);
            }


            HelloReply reply = HelloReply.newBuilder().setMessage(("Hello: " + req.getName())).build();


            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }






    }



    public void getFeature(Point request, StreamObserver<Feature> responseObserver) {
        System.out.println("getFeature得到的请求参数: " + request.toString());
//        responseObserver.onError(); 代表请求出错
        responseObserver.onNext(checkFeature(request));//包装返回信息
        responseObserver.onCompleted();//结束一次请求
    }


    List<Feature> istfeatures = new ArrayList<>();
    //找到复核的feature
    private Feature checkFeature(Point location) {
        for (Feature feature : istfeatures) {
            if (feature.getLocation().getLatitude() == location.getLatitude()
                    && feature.getLocation().getLongitude() == location.getLongitude()) {
                return feature;
            }
        }
        // No feature was found, return an unnamed feature.
        return Feature.newBuilder().setName("").setLocation(location).build();
    }


} 