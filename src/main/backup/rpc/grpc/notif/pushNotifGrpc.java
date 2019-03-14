package com.coalvalue.rpc.grpc.notif;

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
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.10.0)",
    comments = "Source: pushnotif.proto")
public final class pushNotifGrpc {

  private pushNotifGrpc() {}

  public static final String SERVICE_NAME = "grpc.pushNotif";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getRegisterMethod()} instead.
  public static final io.grpc.MethodDescriptor<RegistrationRequest,
      RegistrationResponse> METHOD_REGISTER = getRegisterMethodHelper();

  private static volatile io.grpc.MethodDescriptor<RegistrationRequest,
      RegistrationResponse> getRegisterMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<RegistrationRequest,
      RegistrationResponse> getRegisterMethod() {
    return getRegisterMethodHelper();
  }

  private static io.grpc.MethodDescriptor<RegistrationRequest,
      RegistrationResponse> getRegisterMethodHelper() {
    io.grpc.MethodDescriptor<RegistrationRequest, RegistrationResponse> getRegisterMethod;
    if ((getRegisterMethod = pushNotifGrpc.getRegisterMethod) == null) {
      synchronized (pushNotifGrpc.class) {
        if ((getRegisterMethod = pushNotifGrpc.getRegisterMethod) == null) {
          pushNotifGrpc.getRegisterMethod = getRegisterMethod = 
              io.grpc.MethodDescriptor.<RegistrationRequest, RegistrationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "grpc.pushNotif", "Register"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  RegistrationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  RegistrationResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new pushNotifMethodDescriptorSupplier("Register"))
                  .build();
          }
        }
     }
     return getRegisterMethod;
  }
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @Deprecated // Use {@link #getAlertMethod()} instead.
  public static final io.grpc.MethodDescriptor<Topic,
      Notification> METHOD_ALERT = getAlertMethodHelper();

  private static volatile io.grpc.MethodDescriptor<Topic,
      Notification> getAlertMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<Topic,
      Notification> getAlertMethod() {
    return getAlertMethodHelper();
  }

  private static io.grpc.MethodDescriptor<Topic,
      Notification> getAlertMethodHelper() {
    io.grpc.MethodDescriptor<Topic, Notification> getAlertMethod;
    if ((getAlertMethod = pushNotifGrpc.getAlertMethod) == null) {
      synchronized (pushNotifGrpc.class) {
        if ((getAlertMethod = pushNotifGrpc.getAlertMethod) == null) {
          pushNotifGrpc.getAlertMethod = getAlertMethod = 
              io.grpc.MethodDescriptor.<Topic, Notification>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "grpc.pushNotif", "Alert"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Topic.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Notification.getDefaultInstance()))
                  .setSchemaDescriptor(new pushNotifMethodDescriptorSupplier("Alert"))
                  .build();
          }
        }
     }
     return getAlertMethod;
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
    if ((getSubscribeMethod = pushNotifGrpc.getSubscribeMethod) == null) {
      synchronized (pushNotifGrpc.class) {
        if ((getSubscribeMethod = pushNotifGrpc.getSubscribeMethod) == null) {
          pushNotifGrpc.getSubscribeMethod = getSubscribeMethod = 
              io.grpc.MethodDescriptor.<Topic, Notification>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "grpc.pushNotif", "Subscribe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Topic.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Notification.getDefaultInstance()))
                  .setSchemaDescriptor(new pushNotifMethodDescriptorSupplier("Subscribe"))
                  .build();
          }
        }
     }
     return getSubscribeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static pushNotifStub newStub(io.grpc.Channel channel) {
    return new pushNotifStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static pushNotifBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new pushNotifBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static pushNotifFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new pushNotifFutureStub(channel);
  }

  /**
   */
  public static abstract class pushNotifImplBase implements io.grpc.BindableService {

    /**
     */
    public void register(RegistrationRequest request,
        io.grpc.stub.StreamObserver<RegistrationResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRegisterMethodHelper(), responseObserver);
    }

    /**
     */
    public void alert(Topic request,
        io.grpc.stub.StreamObserver<Notification> responseObserver) {
      asyncUnimplementedUnaryCall(getAlertMethodHelper(), responseObserver);
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
            getRegisterMethodHelper(),
            asyncUnaryCall(
              new MethodHandlers<
                RegistrationRequest,
                RegistrationResponse>(
                  this, METHODID_REGISTER)))
          .addMethod(
            getAlertMethodHelper(),
            asyncServerStreamingCall(
              new MethodHandlers<
                Topic,
                Notification>(
                  this, METHODID_ALERT)))
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
   */
  public static final class pushNotifStub extends io.grpc.stub.AbstractStub<pushNotifStub> {
    private pushNotifStub(io.grpc.Channel channel) {
      super(channel);
    }

    private pushNotifStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected pushNotifStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new pushNotifStub(channel, callOptions);
    }

    /**
     */
    public void register(RegistrationRequest request,
        io.grpc.stub.StreamObserver<RegistrationResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRegisterMethodHelper(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void alert(Topic request,
        io.grpc.stub.StreamObserver<Notification> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getAlertMethodHelper(), getCallOptions()), request, responseObserver);
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
   */
  public static final class pushNotifBlockingStub extends io.grpc.stub.AbstractStub<pushNotifBlockingStub> {
    private pushNotifBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private pushNotifBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected pushNotifBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new pushNotifBlockingStub(channel, callOptions);
    }

    /**
     */
    public RegistrationResponse register(RegistrationRequest request) {
      return blockingUnaryCall(
          getChannel(), getRegisterMethodHelper(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<Notification> alert(
        Topic request) {
      return blockingServerStreamingCall(
          getChannel(), getAlertMethodHelper(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class pushNotifFutureStub extends io.grpc.stub.AbstractStub<pushNotifFutureStub> {
    private pushNotifFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private pushNotifFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected pushNotifFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new pushNotifFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<RegistrationResponse> register(
        RegistrationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRegisterMethodHelper(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER = 0;
  private static final int METHODID_ALERT = 1;
  private static final int METHODID_SUBSCRIBE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final pushNotifImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(pushNotifImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER:
          serviceImpl.register((RegistrationRequest) request,
              (io.grpc.stub.StreamObserver<RegistrationResponse>) responseObserver);
          break;
        case METHODID_ALERT:
          serviceImpl.alert((Topic) request,
              (io.grpc.stub.StreamObserver<Notification>) responseObserver);
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
        case METHODID_SUBSCRIBE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.subscribe(
              (io.grpc.stub.StreamObserver<Notification>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class pushNotifBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    pushNotifBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Pushnotif.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("pushNotif");
    }
  }

  private static final class pushNotifFileDescriptorSupplier
      extends pushNotifBaseDescriptorSupplier {
    pushNotifFileDescriptorSupplier() {}
  }

  private static final class pushNotifMethodDescriptorSupplier
      extends pushNotifBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    pushNotifMethodDescriptorSupplier(String methodName) {
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
      synchronized (pushNotifGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new pushNotifFileDescriptorSupplier())
              .addMethod(getRegisterMethodHelper())
              .addMethod(getAlertMethodHelper())
              .addMethod(getSubscribeMethodHelper())
              .build();
        }
      }
    }
    return result;
  }
}
