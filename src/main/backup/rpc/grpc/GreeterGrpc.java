package com.coalvalue.rpc.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 *https://www.jianshu.com/p/39c9eedba2c2
 *����˽ӿ���
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.10.0)",
    comments = "Source: test.proto")
public final class GreeterGrpc {

  private GreeterGrpc() {}

  public static final String SERVICE_NAME = "grpc.Greeter";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getSayHelloMethod()} instead.
  public static final io.grpc.MethodDescriptor<HelloRequest,
      HelloReply> METHOD_SAY_HELLO = getSayHelloMethodHelper();

  private static volatile io.grpc.MethodDescriptor<HelloRequest,
      HelloReply> getSayHelloMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<HelloRequest,
      HelloReply> getSayHelloMethod() {
    return getSayHelloMethodHelper();
  }

  private static io.grpc.MethodDescriptor<HelloRequest,
      HelloReply> getSayHelloMethodHelper() {
    io.grpc.MethodDescriptor<HelloRequest, HelloReply> getSayHelloMethod;
    if ((getSayHelloMethod = GreeterGrpc.getSayHelloMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getSayHelloMethod = GreeterGrpc.getSayHelloMethod) == null) {
          GreeterGrpc.getSayHelloMethod = getSayHelloMethod = 
              io.grpc.MethodDescriptor.<HelloRequest, HelloReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "grpc.Greeter", "SayHello"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  HelloReply.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("SayHello"))
                  .build();
          }
        }
     }
     return getSayHelloMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getRecordRouteMethod()} instead.
  public static final io.grpc.MethodDescriptor<HelloRequest,
      RouteSummary> METHOD_RECORD_ROUTE = getRecordRouteMethodHelper();

  private static volatile io.grpc.MethodDescriptor<HelloRequest,
      RouteSummary> getRecordRouteMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<HelloRequest,
      RouteSummary> getRecordRouteMethod() {
    return getRecordRouteMethodHelper();
  }

  private static io.grpc.MethodDescriptor<HelloRequest,
      RouteSummary> getRecordRouteMethodHelper() {
    io.grpc.MethodDescriptor<HelloRequest, RouteSummary> getRecordRouteMethod;
    if ((getRecordRouteMethod = GreeterGrpc.getRecordRouteMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getRecordRouteMethod = GreeterGrpc.getRecordRouteMethod) == null) {
          GreeterGrpc.getRecordRouteMethod = getRecordRouteMethod = 
              io.grpc.MethodDescriptor.<HelloRequest, RouteSummary>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "grpc.Greeter", "RecordRoute"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  RouteSummary.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("RecordRoute"))
                  .build();
          }
        }
     }
     return getRecordRouteMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getRecordRoutePointMethod()} instead.
  public static final io.grpc.MethodDescriptor<Point,
      RouteSummary> METHOD_RECORD_ROUTE_POINT = getRecordRoutePointMethodHelper();

  private static volatile io.grpc.MethodDescriptor<Point,
      RouteSummary> getRecordRoutePointMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<Point,
      RouteSummary> getRecordRoutePointMethod() {
    return getRecordRoutePointMethodHelper();
  }

  private static io.grpc.MethodDescriptor<Point,
      RouteSummary> getRecordRoutePointMethodHelper() {
    io.grpc.MethodDescriptor<Point, RouteSummary> getRecordRoutePointMethod;
    if ((getRecordRoutePointMethod = GreeterGrpc.getRecordRoutePointMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getRecordRoutePointMethod = GreeterGrpc.getRecordRoutePointMethod) == null) {
          GreeterGrpc.getRecordRoutePointMethod = getRecordRoutePointMethod = 
              io.grpc.MethodDescriptor.<Point, RouteSummary>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "grpc.Greeter", "RecordRoute_Point"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Point.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  RouteSummary.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("RecordRoute_Point"))
                  .build();
          }
        }
     }
     return getRecordRoutePointMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getListFeaturesMethod()} instead.
  public static final io.grpc.MethodDescriptor<Rectangle,
      Feature> METHOD_LIST_FEATURES = getListFeaturesMethodHelper();

  private static volatile io.grpc.MethodDescriptor<Rectangle,
      Feature> getListFeaturesMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<Rectangle,
      Feature> getListFeaturesMethod() {
    return getListFeaturesMethodHelper();
  }

  private static io.grpc.MethodDescriptor<Rectangle,
      Feature> getListFeaturesMethodHelper() {
    io.grpc.MethodDescriptor<Rectangle, Feature> getListFeaturesMethod;
    if ((getListFeaturesMethod = GreeterGrpc.getListFeaturesMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getListFeaturesMethod = GreeterGrpc.getListFeaturesMethod) == null) {
          GreeterGrpc.getListFeaturesMethod = getListFeaturesMethod = 
              io.grpc.MethodDescriptor.<Rectangle, Feature>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "grpc.Greeter", "ListFeatures"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Rectangle.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Feature.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("ListFeatures"))
                  .build();
          }
        }
     }
     return getListFeaturesMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getRouteChatMethod()} instead.
  public static final io.grpc.MethodDescriptor<RouteNote,
      RouteNote> METHOD_ROUTE_CHAT = getRouteChatMethodHelper();

  private static volatile io.grpc.MethodDescriptor<RouteNote,
      RouteNote> getRouteChatMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<RouteNote,
      RouteNote> getRouteChatMethod() {
    return getRouteChatMethodHelper();
  }

  private static io.grpc.MethodDescriptor<RouteNote,
      RouteNote> getRouteChatMethodHelper() {
    io.grpc.MethodDescriptor<RouteNote, RouteNote> getRouteChatMethod;
    if ((getRouteChatMethod = GreeterGrpc.getRouteChatMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getRouteChatMethod = GreeterGrpc.getRouteChatMethod) == null) {
          GreeterGrpc.getRouteChatMethod = getRouteChatMethod = 
              io.grpc.MethodDescriptor.<RouteNote, RouteNote>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "grpc.Greeter", "RouteChat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  RouteNote.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  RouteNote.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("RouteChat"))
                  .build();
          }
        }
     }
     return getRouteChatMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getSubscribeMethod()} instead.
  public static final io.grpc.MethodDescriptor<Topic,
      Notification> METHOD_SUBSCRIBE = getSubscribeMethodHelper();

  private static volatile io.grpc.MethodDescriptor<Topic,
      Notification> getSubscribeMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<Topic,
      Notification> getSubscribeMethod() {
    return getSubscribeMethodHelper();
  }

  private static io.grpc.MethodDescriptor<Topic,
      Notification> getSubscribeMethodHelper() {
    io.grpc.MethodDescriptor<Topic, Notification> getSubscribeMethod;
    if ((getSubscribeMethod = GreeterGrpc.getSubscribeMethod) == null) {
      synchronized (GreeterGrpc.class) {
        if ((getSubscribeMethod = GreeterGrpc.getSubscribeMethod) == null) {
          GreeterGrpc.getSubscribeMethod = getSubscribeMethod = 
              io.grpc.MethodDescriptor.<Topic, Notification>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "grpc.Greeter", "Subscribe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Topic.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Notification.getDefaultInstance()))
                  .setSchemaDescriptor(new GreeterMethodDescriptorSupplier("Subscribe"))
                  .build();
          }
        }
     }
     return getSubscribeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static GreeterStub newStub(io.grpc.Channel channel) {
    return new GreeterStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static GreeterBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new GreeterBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static GreeterFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new GreeterFutureStub(channel);
  }

  /**
   * <pre>
   *https://www.jianshu.com/p/39c9eedba2c2
   *����˽ӿ���
   * </pre>
   */
  public static abstract class GreeterImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *����˽ӿڷ���
     * </pre>
     */
    public void sayHello(HelloRequest request,
        io.grpc.stub.StreamObserver<HelloReply> responseObserver) {
      asyncUnimplementedUnaryCall(getSayHelloMethodHelper(), responseObserver);
    }

    /**
     * <pre>
     *һ�� �ͻ�����ʽ RPC �� �ͻ���д��һ����Ϣ���в����䷢�͵���������ͬ��Ҳ��ʹ������һ���ͻ������д����Ϣ��
     *��ȴ��������ɶ�ȡ���������Ӧ��ͨ���� ���� ����ǰָ�� stream �ؼ�����ָ��һ���ͻ��˵���������
     * </pre>
     */
    public io.grpc.stub.StreamObserver<HelloRequest> recordRoute(
        io.grpc.stub.StreamObserver<RouteSummary> responseObserver) {
      return asyncUnimplementedStreamingCall(getRecordRouteMethodHelper(), responseObserver);
    }

    /**
     * <pre>
     *��¼�õ�
     *һ�� �ͻ�����ʽ RPC �� �ͻ���д��һ����Ϣ���в����䷢�͵���������ͬ��Ҳ��ʹ������һ���ͻ������д����Ϣ��
     *��ȴ��������ɶ�ȡ���������Ӧ��ͨ���� ���� ����ǰָ�� stream �ؼ�����ָ��һ���ͻ��˵���������
     * </pre>
     */
    public io.grpc.stub.StreamObserver<Point> recordRoutePoint(
        io.grpc.stub.StreamObserver<RouteSummary> responseObserver) {
      return asyncUnimplementedStreamingCall(getRecordRoutePointMethodHelper(), responseObserver);
    }

    /**
     * <pre>
     *��ȡһ�������ڵĵ�
     *һ�� ����������ʽ RPC �� �ͻ��˷������󵽷��������õ�һ����ȥ��ȡ���ص���Ϣ���С� �ͻ��˶�ȡ���ص�����
     *ֱ������û���κ���Ϣ���������п��Կ�����ͨ���� ��Ӧ ����ǰ���� stream �ؼ��֣�����ָ��һ���������˵���������
     * </pre>
     */
    public void listFeatures(Rectangle request,
        io.grpc.stub.StreamObserver<Feature> responseObserver) {
      asyncUnimplementedUnaryCall(getListFeaturesMethodHelper(), responseObserver);
    }

    /**
     * <pre>
     *·�ɽ���
     *һ�� ˫����ʽ RPC ��˫��ʹ�ö�д��ȥ����һ����Ϣ���С�������������������˿ͻ��˺ͷ�����
     *����������ϲ����˳���д�����磬 ������������д����Ӧǰ�ȴ�������еĿͻ�����Ϣ�����߿��Խ��� �Ķ�ȡ��д����Ϣ��
     *���������д����ϡ�ÿ�����е���Ϣ˳��Ԥ���������ͨ�����������Ӧǰ�� stream �ؼ���ȥ�ƶ����������͡�
     * </pre>
     */
    public io.grpc.stub.StreamObserver<RouteNote> routeChat(
        io.grpc.stub.StreamObserver<RouteNote> responseObserver) {
      return asyncUnimplementedStreamingCall(getRouteChatMethodHelper(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<Topic> subscribe(
        io.grpc.stub.StreamObserver<Notification> responseObserver) {
      return asyncUnimplementedStreamingCall(getSubscribeMethodHelper(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSayHelloMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                HelloRequest,
                HelloReply>(
                  this, METHODID_SAY_HELLO)))
          .addMethod(
            getRecordRouteMethodHelper(),
            asyncClientStreamingCall(
              new MethodHandlers<
                HelloRequest,
                RouteSummary>(
                  this, METHODID_RECORD_ROUTE)))
          .addMethod(
            getRecordRoutePointMethodHelper(),
            asyncClientStreamingCall(
              new MethodHandlers<
                Point,
                RouteSummary>(
                  this, METHODID_RECORD_ROUTE_POINT)))
          .addMethod(
            getListFeaturesMethodHelper(),
            asyncServerStreamingCall(
              new MethodHandlers<
                Rectangle,
                Feature>(
                  this, METHODID_LIST_FEATURES)))
          .addMethod(
            getRouteChatMethodHelper(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                RouteNote,
                RouteNote>(
                  this, METHODID_ROUTE_CHAT)))
          .addMethod(
            getSubscribeMethodHelper(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                Topic,
                Notification>(
                  this, METHODID_SUBSCRIBE)))
          .build();
    }
  }

  /**
   * <pre>
   *https://www.jianshu.com/p/39c9eedba2c2
   *����˽ӿ���
   * </pre>
   */
  public static final class GreeterStub extends io.grpc.stub.AbstractStub<GreeterStub> {
    private GreeterStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GreeterStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected GreeterStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GreeterStub(channel, callOptions);
    }

    /**
     * <pre>
     *����˽ӿڷ���
     * </pre>
     */
    public void sayHello(HelloRequest request,
        io.grpc.stub.StreamObserver<HelloReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSayHelloMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *һ�� �ͻ�����ʽ RPC �� �ͻ���д��һ����Ϣ���в����䷢�͵���������ͬ��Ҳ��ʹ������һ���ͻ������д����Ϣ��
     *��ȴ��������ɶ�ȡ���������Ӧ��ͨ���� ���� ����ǰָ�� stream �ؼ�����ָ��һ���ͻ��˵���������
     * </pre>
     */
    public io.grpc.stub.StreamObserver<HelloRequest> recordRoute(
        io.grpc.stub.StreamObserver<RouteSummary> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getRecordRouteMethodHelper(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     *��¼�õ�
     *һ�� �ͻ�����ʽ RPC �� �ͻ���д��һ����Ϣ���в����䷢�͵���������ͬ��Ҳ��ʹ������һ���ͻ������д����Ϣ��
     *��ȴ��������ɶ�ȡ���������Ӧ��ͨ���� ���� ����ǰָ�� stream �ؼ�����ָ��һ���ͻ��˵���������
     * </pre>
     */
    public io.grpc.stub.StreamObserver<Point> recordRoutePoint(
        io.grpc.stub.StreamObserver<RouteSummary> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getRecordRoutePointMethodHelper(), getCallOptions()), responseObserver);
    }

    /**
     * <pre>
     *��ȡһ�������ڵĵ�
     *һ�� ����������ʽ RPC �� �ͻ��˷������󵽷��������õ�һ����ȥ��ȡ���ص���Ϣ���С� �ͻ��˶�ȡ���ص�����
     *ֱ������û���κ���Ϣ���������п��Կ�����ͨ���� ��Ӧ ����ǰ���� stream �ؼ��֣�����ָ��һ���������˵���������
     * </pre>
     */
    public void listFeatures(Rectangle request,
        io.grpc.stub.StreamObserver<Feature> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getListFeaturesMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *·�ɽ���
     *һ�� ˫����ʽ RPC ��˫��ʹ�ö�д��ȥ����һ����Ϣ���С�������������������˿ͻ��˺ͷ�����
     *����������ϲ����˳���д�����磬 ������������д����Ӧǰ�ȴ�������еĿͻ�����Ϣ�����߿��Խ��� �Ķ�ȡ��д����Ϣ��
     *���������д����ϡ�ÿ�����е���Ϣ˳��Ԥ���������ͨ�����������Ӧǰ�� stream �ؼ���ȥ�ƶ����������͡�
     * </pre>
     */
    public io.grpc.stub.StreamObserver<RouteNote> routeChat(
        io.grpc.stub.StreamObserver<RouteNote> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getRouteChatMethodHelper(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<Topic> subscribe(
        io.grpc.stub.StreamObserver<Notification> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getSubscribeMethodHelper(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   *https://www.jianshu.com/p/39c9eedba2c2
   *����˽ӿ���
   * </pre>
   */
  public static final class GreeterBlockingStub extends io.grpc.stub.AbstractStub<GreeterBlockingStub> {
    private GreeterBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GreeterBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected GreeterBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GreeterBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *����˽ӿڷ���
     * </pre>
     */
    public HelloReply sayHello(HelloRequest request) {
      return blockingUnaryCall(
          getChannel(), getSayHelloMethodHelper(), getCallOptions(), request);
    }

    /**
     * <pre>
     *��ȡһ�������ڵĵ�
     *һ�� ����������ʽ RPC �� �ͻ��˷������󵽷��������õ�һ����ȥ��ȡ���ص���Ϣ���С� �ͻ��˶�ȡ���ص�����
     *ֱ������û���κ���Ϣ���������п��Կ�����ͨ���� ��Ӧ ����ǰ���� stream �ؼ��֣�����ָ��һ���������˵���������
     * </pre>
     */
    public java.util.Iterator<Feature> listFeatures(
        Rectangle request) {
      return blockingServerStreamingCall(
          getChannel(), getListFeaturesMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   *https://www.jianshu.com/p/39c9eedba2c2
   *����˽ӿ���
   * </pre>
   */
  public static final class GreeterFutureStub extends io.grpc.stub.AbstractStub<GreeterFutureStub> {
    private GreeterFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private GreeterFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected GreeterFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new GreeterFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *����˽ӿڷ���
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<HelloReply> sayHello(
        HelloRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSayHelloMethodHelper(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SAY_HELLO = 0;
  private static final int METHODID_LIST_FEATURES = 1;
  private static final int METHODID_RECORD_ROUTE = 2;
  private static final int METHODID_RECORD_ROUTE_POINT = 3;
  private static final int METHODID_ROUTE_CHAT = 4;
  private static final int METHODID_SUBSCRIBE = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final GreeterImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(GreeterImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SAY_HELLO:
          serviceImpl.sayHello((HelloRequest) request,
              (io.grpc.stub.StreamObserver<HelloReply>) responseObserver);
          break;
        case METHODID_LIST_FEATURES:
          serviceImpl.listFeatures((Rectangle) request,
              (io.grpc.stub.StreamObserver<Feature>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RECORD_ROUTE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.recordRoute(
              (io.grpc.stub.StreamObserver<RouteSummary>) responseObserver);
        case METHODID_RECORD_ROUTE_POINT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.recordRoutePoint(
              (io.grpc.stub.StreamObserver<RouteSummary>) responseObserver);
        case METHODID_ROUTE_CHAT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.routeChat(
              (io.grpc.stub.StreamObserver<RouteNote>) responseObserver);
        case METHODID_SUBSCRIBE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.subscribe(
              (io.grpc.stub.StreamObserver<Notification>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class GreeterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    GreeterBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return HelloWorldServiceProto.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Greeter");
    }
  }

  private static final class GreeterFileDescriptorSupplier
      extends GreeterBaseDescriptorSupplier {
    GreeterFileDescriptorSupplier() {}
  }

  private static final class GreeterMethodDescriptorSupplier
      extends GreeterBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    GreeterMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (GreeterGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new GreeterFileDescriptorSupplier())
              .addMethod(getSayHelloMethodHelper())
              .addMethod(getRecordRouteMethodHelper())
              .addMethod(getRecordRoutePointMethodHelper())
              .addMethod(getListFeaturesMethodHelper())
              .addMethod(getRouteChatMethodHelper())
              .addMethod(getSubscribeMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
