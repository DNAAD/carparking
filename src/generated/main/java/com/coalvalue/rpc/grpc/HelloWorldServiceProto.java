// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: test.proto

package com.coalvalue.rpc.grpc;

public final class HelloWorldServiceProto {
  private HelloWorldServiceProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_HelloRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_HelloRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_HelloReply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_HelloReply_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_RouteSummary_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_RouteSummary_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_Point_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_Point_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_Rectangle_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_Rectangle_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_Feature_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_Feature_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_FeatureDatabase_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_FeatureDatabase_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_RouteNote_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_RouteNote_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_Alert_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_Alert_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_Mode_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_Mode_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_Temp_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_Temp_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_Topic_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_Topic_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_grpc_Notification_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_grpc_Notification_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\ntest.proto\022\004grpc\")\n\014HelloRequest\022\014\n\004na" +
      "me\030\001 \001(\t\022\013\n\003sex\030\002 \001(\t\"\035\n\nHelloReply\022\017\n\007m" +
      "essage\030\001 \001(\t\"a\n\014RouteSummary\022\023\n\013point_co" +
      "unt\030\001 \001(\005\022\024\n\014feture_count\030\002 \001(\005\022\020\n\010dista" +
      "nce\030\003 \001(\005\022\024\n\014elapsed_time\030\004 \001(\005\",\n\005Point" +
      "\022\020\n\010latitude\030\001 \001(\005\022\021\n\tlongitude\030\002 \001(\005\"=\n" +
      "\tRectangle\022\027\n\002lo\030\001 \001(\0132\013.grpc.Point\022\027\n\002h" +
      "i\030\002 \001(\0132\013.grpc.Point\"6\n\007Feature\022\014\n\004name\030" +
      "\001 \001(\t\022\035\n\010location\030\002 \001(\0132\013.grpc.Point\"1\n\017" +
      "FeatureDatabase\022\036\n\007feature\030\001 \003(\0132\r.grpc." +
      "Feature\";\n\tRouteNote\022\035\n\010location\030\001 \001(\0132\013" +
      ".grpc.Point\022\017\n\007message\030\002 \001(\t\"\030\n\005Alert\022\017\n" +
      "\007message\030\001 \001(\t\"\027\n\004Mode\022\017\n\007newMode\030\001 \001(\t\"" +
      "\027\n\004Temp\022\017\n\007newTemp\030\001 \001(\r\":\n\005Topic\022\022\n\ncli" +
      "entName\030\001 \001(\t\022\035\n\004type\030\002 \001(\0162\017.grpc.topic" +
      "Type\"\233\001\n\014Notification\022\022\n\nclientName\030\001 \001(" +
      "\t\022\022\n\nserverName\030\002 \001(\t\022\035\n\004type\030\003 \001(\0162\017.gr" +
      "pc.topicType\022\026\n\001a\030\006 \001(\0132\013.grpc.Alert\022\025\n\001" +
      "m\030\004 \001(\0132\n.grpc.Mode\022\025\n\001t\030\005 \001(\0132\n.grpc.Te" +
      "mp**\n\ttopicType\022\t\n\005ALERT\020\000\022\010\n\004MODE\020\001\022\010\n\004" +
      "TEMP\020\0022\315\002\n\007Greeter\0222\n\010SayHello\022\022.grpc.He" +
      "lloRequest\032\020.grpc.HelloReply\"\000\0229\n\013Record" +
      "Route\022\022.grpc.HelloRequest\032\022.grpc.RouteSu" +
      "mmary\"\000(\001\0228\n\021RecordRoute_Point\022\013.grpc.Po" +
      "int\032\022.grpc.RouteSummary\"\000(\001\0222\n\014ListFeatu" +
      "res\022\017.grpc.Rectangle\032\r.grpc.Feature\"\0000\001\022" +
      "3\n\tRouteChat\022\017.grpc.RouteNote\032\017.grpc.Rou" +
      "teNote\"\000(\0010\001\0220\n\tSubscribe\022\013.grpc.Topic\032\022" +
      ".grpc.Notification(\0010\001B2\n\026com.coalvalue." +
      "rpc.grpcB\026HelloWorldServiceProtoP\001b\006prot" +
      "o3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_grpc_HelloRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_grpc_HelloRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_HelloRequest_descriptor,
        new java.lang.String[] { "Name", "Sex", });
    internal_static_grpc_HelloReply_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_grpc_HelloReply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_HelloReply_descriptor,
        new java.lang.String[] { "Message", });
    internal_static_grpc_RouteSummary_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_grpc_RouteSummary_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_RouteSummary_descriptor,
        new java.lang.String[] { "PointCount", "FetureCount", "Distance", "ElapsedTime", });
    internal_static_grpc_Point_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_grpc_Point_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_Point_descriptor,
        new java.lang.String[] { "Latitude", "Longitude", });
    internal_static_grpc_Rectangle_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_grpc_Rectangle_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_Rectangle_descriptor,
        new java.lang.String[] { "Lo", "Hi", });
    internal_static_grpc_Feature_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_grpc_Feature_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_Feature_descriptor,
        new java.lang.String[] { "Name", "Location", });
    internal_static_grpc_FeatureDatabase_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_grpc_FeatureDatabase_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_FeatureDatabase_descriptor,
        new java.lang.String[] { "Feature", });
    internal_static_grpc_RouteNote_descriptor =
      getDescriptor().getMessageTypes().get(7);
    internal_static_grpc_RouteNote_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_RouteNote_descriptor,
        new java.lang.String[] { "Location", "Message", });
    internal_static_grpc_Alert_descriptor =
      getDescriptor().getMessageTypes().get(8);
    internal_static_grpc_Alert_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_Alert_descriptor,
        new java.lang.String[] { "Message", });
    internal_static_grpc_Mode_descriptor =
      getDescriptor().getMessageTypes().get(9);
    internal_static_grpc_Mode_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_Mode_descriptor,
        new java.lang.String[] { "NewMode", });
    internal_static_grpc_Temp_descriptor =
      getDescriptor().getMessageTypes().get(10);
    internal_static_grpc_Temp_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_Temp_descriptor,
        new java.lang.String[] { "NewTemp", });
    internal_static_grpc_Topic_descriptor =
      getDescriptor().getMessageTypes().get(11);
    internal_static_grpc_Topic_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_Topic_descriptor,
        new java.lang.String[] { "ClientName", "Type", });
    internal_static_grpc_Notification_descriptor =
      getDescriptor().getMessageTypes().get(12);
    internal_static_grpc_Notification_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_grpc_Notification_descriptor,
        new java.lang.String[] { "ClientName", "ServerName", "Type", "A", "M", "T", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}