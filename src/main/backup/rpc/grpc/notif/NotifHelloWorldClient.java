package com.coalvalue.rpc.grpc.notif;

import com.coalvalue.rpc.grpc.*;
import com.coalvalue.rpc.grpc.Notification;
import com.coalvalue.rpc.grpc.Topic;
import com.coalvalue.rpc.grpc.topicType;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class NotifHelloWorldClient {

    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;


    private final GreeterGrpc.GreeterStub asyncStub;
    public NotifHelloWorldClient(String host, int port){
        channel = ManagedChannelBuilder.forAddress(host,port) 
                .usePlaintext(true) 
                .build();

        blockingStub = GreeterGrpc.newBlockingStub(channel);

        asyncStub = GreeterGrpc.newStub(channel);
    }


    public void shutdown() throws InterruptedException { 
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS); 
    }

    public  void greet(String name){ 
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply   response = blockingStub.sayHello(request);
        System.out.println(response.getMessage());

    }

    public static void main(String[] args) throws InterruptedException { 
        NotifHelloWorldClient client = new NotifHelloWorldClient("127.0.0.1",50051);
/*        for(int i=0;i<5;i++){
            client.greet("world:"+i);
        }*/

/*
        Point location = Point.newBuilder()
                .setLatitude(45)
                .setLongitude(67)
                .build();


        Feature feature = Feature.newBuilder().setLocation(location).build();
        List<Feature> features = new ArrayList<>();
        features.add(feature);
        client.recordRoute(features,1);
*/



        //client.listFeatures(1,1,1,1);
        //client.routeChat();
        client.subscribe();

        client.channel.shutdownNow();

        //client.end();

    }

    public void recordRoute(List<Feature> features, int numPoints) throws InterruptedException {
        System.out.println("start recordRoute");
        final CountDownLatch finishLatch = new CountDownLatch(1);
        //建一个应答者接受返回数据
        StreamObserver<RouteSummary> responseObserver = new StreamObserver<RouteSummary>() {
            @Override
            public void onNext(RouteSummary summary) {
                System.out.println("recordRoute服务端返回 :" + summary);
            }
            @Override
            public void onError(Throwable t) {
                System.out.println("RecordRoute Failed");
                finishLatch.countDown();
            }
            @Override
            public void onCompleted() {
                System.out.println("RecordRoute finish");
                finishLatch.countDown();
            }
        };


        //客户端写入操作
        StreamObserver<Point> requestObserver = asyncStub.recordRoutePoint(responseObserver);

        Random random = new Random();
        try {
            for (int i = 0; i < numPoints; ++i) {
                int index = random.nextInt(features.size());
                Point point = features.get(index).getLocation();
                System.out.println("客户端写入point:" + point);
                requestObserver.onNext(point);

                Thread.sleep(random.nextInt(1000) + 500);
                if (finishLatch.getCount() == 0) {
                    return;
                }
            }

            Thread.sleep(4000);
            for (int i = 0; i < numPoints; ++i) {
                int index = random.nextInt(features.size());
                Point point = features.get(index).getLocation();
                System.out.println("客户端写入point:" + point);
                requestObserver.onNext(point);

                Thread.sleep(random.nextInt(1000) + 500);
                if (finishLatch.getCount() == 0) {
                    return;
                }
            }

            Thread.sleep(4000);
            for (int i = 0; i < numPoints; ++i) {
                int index = random.nextInt(features.size());
                Point point = features.get(index).getLocation();
                System.out.println("客户端写入point:" + point);
                requestObserver.onNext(point);

                Thread.sleep(random.nextInt(1000) + 500);
                if (finishLatch.getCount() == 0) {
                    return;
                }
            }
        } catch (RuntimeException e) {
            requestObserver.onError(e);
            throw e;
        }
        //标识已经写完
        requestObserver.onCompleted();
        // Receiving happens asynchronously
        if (!finishLatch.await(1, TimeUnit.MINUTES)) {
            System.out.println("recordRoute can not finish within 1 minutes");
        }
    }








    //2.服务端流式RPC
    public void listFeatures(int lowLat, int lowLon, int hiLat, int hiLon){
        System.out.println("start listFeatures");
        Rectangle request =
                Rectangle.newBuilder()
                        .setLo(Point.newBuilder().setLatitude(lowLat).setLongitude(lowLon).build())
                        .setHi(Point.newBuilder().setLatitude(hiLat).setLongitude(hiLon).build()).build();
        Iterator<Feature> features;
        try {
            features = blockingStub.listFeatures(request);
            for (int i = 1; features.hasNext(); i++) {
                Feature feature = features.next();
                System.out.println("getFeature服务端返回 :" + feature);
            }
        } catch (Exception e) {
            System.out.println("RPC failed " +e.getMessage());
        }
    }



    public CountDownLatch routeChat() {
        System.out.println("start routeChat");
        final CountDownLatch finishLatch = new CountDownLatch(1);
        //写入监听
        StreamObserver<RouteNote> requestObserver =       //写回监听
                asyncStub.routeChat(new StreamObserver<RouteNote>() {
                    //服务端每写回一个操作就调用
                    @Override
                    public void onNext(RouteNote note) {
                        System.out.println("服务端写回: " + note);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        System.out.println("RouteChat Failed:");
                        finishLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Finished RouteChat");
                        finishLatch.countDown();
                    }
                });

        try {
            RouteNote[] requests = {newNote("First message", 0, 0), newNote("Second message", 0, 1), newNote("Third message", 1, 0), newNote("Fourth message", 1, 1)};

            for (RouteNote request : requests) {
                System.out.println("客户端写入:" + request);
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                requestObserver.onNext(request);
            }

        } catch (RuntimeException e) {
            requestObserver.onError(e);
            throw e;
        }

        //标识写完
        //requestObserver.onCompleted();
        return finishLatch;
    }



    RouteNote newNote(String content,Integer one, Integer two){
        RouteNote routeNote = RouteNote.newBuilder().setMessage(content).build();
        return routeNote;
     }













    public CountDownLatch subscribe() {
        System.out.println("start routeChat");
        final CountDownLatch finishLatch = new CountDownLatch(1);
        //写入监听
        StreamObserver<Topic> requestObserver =       //写回监听
                asyncStub.subscribe(new StreamObserver<Notification>() {
                    //服务端每写回一个操作就调用
                    @Override
                    public void onNext(Notification note) {
                        System.out.println("服务端写回: " + note);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        System.out.println("RouteChat Failed:");
                        finishLatch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Finished RouteChat");
                        finishLatch.countDown();
                    }
                });

        try {

            Topic topic = Topic.newBuilder().setClientName("ddd").setType(topicType.TEMP).build();
            requestObserver.onNext(topic);
            while (true){
                //Topic topic = Topic.newBuilder().setClientName("ddd").setType(topicType.TEMP).build();

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            //
            }




        } catch (RuntimeException e) {
            requestObserver.onError(e);
            throw e;
        }

        //标识写完
        //requestObserver.onCompleted();
       // return finishLatch;
    }

}