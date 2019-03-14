package com.coalvalue.rpc.grpc.notif;




import com.coalvalue.rpc.grpc.*;
import com.coalvalue.rpc.grpc.Notification;
import com.coalvalue.rpc.grpc.Topic;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.max;
import static java.lang.Math.min;


@Component
public class NotifiyServer {


    private int port = 50051;
    private Server server;

    public void start() throws IOException {

    /*    ManagedChannelBuilder.forAddress(host, port)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();*/
        // handshakeTimeout()
        server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build()
                .start();

        System.out.println("service start...");

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {

                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                NotifiyServer.this.stop();
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
    static Map<String, List<SynchronousQueue>> maps = new HashMap<>();

    public static void main(String[] args) throws IOException, InterruptedException {

        final NotifiyServer server = new NotifiyServer();

        server.start();

        int i = 0;
        while (true){
            //Topic topic = Topic.newBuilder().setClientName("ddd").setType(topicType.TEMP).build();

            List<SynchronousQueue> synchronousQueues = maps.get("alexa");
            if(synchronousQueues!= null){
                for(SynchronousQueue synchronousQueue: synchronousQueues){
                    Notification notification = Notification.newBuilder().setServerName("server"+ i++ ).build();
                    synchronousQueue.put(notification);
                }

            }

/*            try {
               // Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            //    requestObserver.onNext(topic);
        }


        //server.blockUntilShutdown();
    }


    // 实现 定义一个实现服务接口的类 
    private class GreeterImpl extends GreeterGrpc.GreeterImplBase {




        @Override
        public StreamObserver<Point> recordRoutePoint(StreamObserver<RouteSummary> responseObserver) {
            return new StreamObserver<Point>() {
                int pointCount;
                int featureCount;
                int distance;
                Point previous;
                long startTime = System.nanoTime();

                //客户端每写入一个Point,服务端就会调用该方法
                @Override
                public void onNext(Point point) {
                    System.out.println("recordRoute得到的请求参数: " + point.toString());
                    pointCount++;
 /*                   if (RouteGuideUtil.exists(checkFeature(point))) {
                        featureCount++;
                    }
                    if (previous != null) {
                        distance += calcDistance(previous, point);
                    }*/
                    previous = point;
                }

                @Override
                public void onError(Throwable throwable) {
                  //  throwable.printStackTrace();
                    System.out.println("Encountered error in routeChat");
                   // System.err.println("Ete");
                }

                //客户端写入结束时调用
                @Override
                public void onCompleted() {
                    long seconds = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime);
                    pointCount = 1;
                    featureCount = 1;
                    distance = 1;
                    RouteSummary routeSummary = RouteSummary.newBuilder().setPointCount(pointCount)
                            .setFetureCount(featureCount)
                            .setDistance(distance)
                            .setElapsedTime((int) seconds).build();
                    responseObserver.onNext(routeSummary);

                    responseObserver.onCompleted();

                }
            };

        }











        @Override
        public void listFeatures(Rectangle request, StreamObserver<Feature> responseObserver) {



            int left = min(request.getLo().getLongitude(), request.getHi().getLongitude());
            int right = max(request.getLo().getLongitude(), request.getHi().getLongitude());
            int top = max(request.getLo().getLatitude(), request.getHi().getLatitude());
            int bottom = min(request.getLo().getLatitude(), request.getHi().getLatitude());

            Point location = Point.newBuilder()
                    .setLatitude(45)
                    .setLongitude(67)
                    .build();


            Feature feature_o = Feature.newBuilder().setLocation(location).build();
            List<Feature> features = new ArrayList<>();
            features.add(feature_o);

/*            for (Feature feature : features) {
                //如果不存在则继续
                if (!RouteGuideUtil.exists(feature)) {
                    continue;
                }

                int lat = feature.getLocation().getLatitude();
                int lon = feature.getLocation().getLongitude();
                if (lon >= left && lon <= right && lat >= bottom && lat <= top) {
                    //找到符合的就写入
                    responseObserver.onNext(feature);
                }
            }*/

            for (Feature feature : features) {
                responseObserver.onNext(feature);
            }



            responseObserver.onCompleted();
            //最后标识完成

        }




        @Override
        public StreamObserver<RouteNote> routeChat(StreamObserver<RouteNote> responseObserver) {
            return new StreamObserver<RouteNote>() {
                @Override
                public void onNext(RouteNote note) {

                    System.out.println("服务器 收到了，routeChat Encountered error in routeChat");

                    //List<RouteNote> notes = getOrCreateNotes(note.getLocation());

 /*                   for (RouteNote prevNote : notes.toArray(new RouteNote[0])) {
                        responseObserver.onNext(prevNote);
                    }*/
                   // notes.add(note);




                  //  RouteNote[] notes = {, newNote("Second message Response", 0, 1), newNote("Third message Response", 1, 0), newNote("Fourth message Response", 1, 1)};


                        responseObserver.onNext(newNote("First message  服务器 回复信息 ", 0, 0));

                }
                @Override
                public void onError(Throwable t) {
                    t.printStackTrace();
                    System.err.println("Encountered error in routeChat");
                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }
























        @Override
        public  io.grpc.stub.StreamObserver<Topic> subscribe(StreamObserver<Notification> responseObserver) {
            return new StreamObserver<Topic>() {
                @Override
                public void onNext(Topic note) {



                    System.out.println("服务器Topic countered error in routeChat"+note.getClientName());
                  List<SynchronousQueue> synchronousQueues = maps.get(note.getClientName());
                    SynchronousQueue<Notification> queue = null;
                    if(synchronousQueues== null){
                        synchronousQueues = new ArrayList<>();
                        synchronousQueues.add( new SynchronousQueue<>());

                        maps.put(note.getClientName(),synchronousQueues);

                    }
                        queue = synchronousQueues.get(0);



                    synchronousQueues.add(queue);
                   // Notification notification = queue.take();
                    Notification notification = Notification.newBuilder().setServerName("server" ).build();

                    System.out.println("服发送一个命令啊啊Chat" + note.getClientName() + notification.getServerName());
                    responseObserver.onNext(notification);
                    //List<RouteNote> notes = getOrCreateNotes(note.getLocation());

 /*                   for (RouteNote prevNote : notes.toArray(new RouteNote[0])) {
                        responseObserver.onNext(prevNote);
                    }*/
                    // notes.add(note);


/*

                    int i = 0;
                    while(i<10){
                        i++;
                        try {

                            Notification notification = queue.take();

                            System.out.println("服发送一个命令啊啊Chat" + note.getClientName() + notification.getServerName());
                            responseObserver.onNext(notification);


                            System.out.println("现场号："+ Thread.currentThread().getId());


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
*/

                    //  RouteNote[] notes = {, newNote("Second message Response", 0, 1), newNote("Third message Response", 1, 0), newNote("Fourth message Response", 1, 1)};




                }
                @Override
                public void onError(Throwable t) {

                    t.printStackTrace();
                    System.err.println("Encountered error in routeChat"); //短线后，又这个阿
                }

                @Override
                public void onCompleted() {
                    responseObserver.onCompleted();
                }
            };
        }




    }


    RouteNote newNote(String content,Integer one, Integer two){
        RouteNote routeNote = RouteNote.newBuilder().setMessage(content).build();
        return routeNote;
    }






} 