// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: test.proto

package com.coalvalue.rpc.grpc;

/**
 * Protobuf type {@code grpc.Notification}
 */
public  final class Notification extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:grpc.Notification)
    NotificationOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Notification.newBuilder() to construct.
  private Notification(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Notification() {
    clientName_ = "";
    serverName_ = "";
    type_ = 0;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Notification(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            clientName_ = s;
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            serverName_ = s;
            break;
          }
          case 24: {
            int rawValue = input.readEnum();

            type_ = rawValue;
            break;
          }
          case 34: {
            com.coalvalue.rpc.grpc.Mode.Builder subBuilder = null;
            if (m_ != null) {
              subBuilder = m_.toBuilder();
            }
            m_ = input.readMessage(com.coalvalue.rpc.grpc.Mode.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(m_);
              m_ = subBuilder.buildPartial();
            }

            break;
          }
          case 42: {
            com.coalvalue.rpc.grpc.Temp.Builder subBuilder = null;
            if (t_ != null) {
              subBuilder = t_.toBuilder();
            }
            t_ = input.readMessage(com.coalvalue.rpc.grpc.Temp.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(t_);
              t_ = subBuilder.buildPartial();
            }

            break;
          }
          case 50: {
            com.coalvalue.rpc.grpc.Alert.Builder subBuilder = null;
            if (a_ != null) {
              subBuilder = a_.toBuilder();
            }
            a_ = input.readMessage(com.coalvalue.rpc.grpc.Alert.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(a_);
              a_ = subBuilder.buildPartial();
            }

            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.coalvalue.rpc.grpc.HelloWorldServiceProto.internal_static_grpc_Notification_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.coalvalue.rpc.grpc.HelloWorldServiceProto.internal_static_grpc_Notification_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.coalvalue.rpc.grpc.Notification.class, com.coalvalue.rpc.grpc.Notification.Builder.class);
  }

  public static final int CLIENTNAME_FIELD_NUMBER = 1;
  private volatile java.lang.Object clientName_;
  /**
   * <code>string clientName = 1;</code>
   */
  public java.lang.String getClientName() {
    java.lang.Object ref = clientName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      clientName_ = s;
      return s;
    }
  }
  /**
   * <code>string clientName = 1;</code>
   */
  public com.google.protobuf.ByteString
      getClientNameBytes() {
    java.lang.Object ref = clientName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      clientName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int SERVERNAME_FIELD_NUMBER = 2;
  private volatile java.lang.Object serverName_;
  /**
   * <code>string serverName = 2;</code>
   */
  public java.lang.String getServerName() {
    java.lang.Object ref = serverName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      serverName_ = s;
      return s;
    }
  }
  /**
   * <code>string serverName = 2;</code>
   */
  public com.google.protobuf.ByteString
      getServerNameBytes() {
    java.lang.Object ref = serverName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      serverName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TYPE_FIELD_NUMBER = 3;
  private int type_;
  /**
   * <code>.grpc.topicType type = 3;</code>
   */
  public int getTypeValue() {
    return type_;
  }
  /**
   * <code>.grpc.topicType type = 3;</code>
   */
  public com.coalvalue.rpc.grpc.topicType getType() {
    com.coalvalue.rpc.grpc.topicType result = com.coalvalue.rpc.grpc.topicType.valueOf(type_);
    return result == null ? com.coalvalue.rpc.grpc.topicType.UNRECOGNIZED : result;
  }

  public static final int A_FIELD_NUMBER = 6;
  private com.coalvalue.rpc.grpc.Alert a_;
  /**
   * <code>.grpc.Alert a = 6;</code>
   */
  public boolean hasA() {
    return a_ != null;
  }
  /**
   * <code>.grpc.Alert a = 6;</code>
   */
  public com.coalvalue.rpc.grpc.Alert getA() {
    return a_ == null ? com.coalvalue.rpc.grpc.Alert.getDefaultInstance() : a_;
  }
  /**
   * <code>.grpc.Alert a = 6;</code>
   */
  public com.coalvalue.rpc.grpc.AlertOrBuilder getAOrBuilder() {
    return getA();
  }

  public static final int M_FIELD_NUMBER = 4;
  private com.coalvalue.rpc.grpc.Mode m_;
  /**
   * <code>.grpc.Mode m = 4;</code>
   */
  public boolean hasM() {
    return m_ != null;
  }
  /**
   * <code>.grpc.Mode m = 4;</code>
   */
  public com.coalvalue.rpc.grpc.Mode getM() {
    return m_ == null ? com.coalvalue.rpc.grpc.Mode.getDefaultInstance() : m_;
  }
  /**
   * <code>.grpc.Mode m = 4;</code>
   */
  public com.coalvalue.rpc.grpc.ModeOrBuilder getMOrBuilder() {
    return getM();
  }

  public static final int T_FIELD_NUMBER = 5;
  private com.coalvalue.rpc.grpc.Temp t_;
  /**
   * <code>.grpc.Temp t = 5;</code>
   */
  public boolean hasT() {
    return t_ != null;
  }
  /**
   * <code>.grpc.Temp t = 5;</code>
   */
  public com.coalvalue.rpc.grpc.Temp getT() {
    return t_ == null ? com.coalvalue.rpc.grpc.Temp.getDefaultInstance() : t_;
  }
  /**
   * <code>.grpc.Temp t = 5;</code>
   */
  public com.coalvalue.rpc.grpc.TempOrBuilder getTOrBuilder() {
    return getT();
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getClientNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, clientName_);
    }
    if (!getServerNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, serverName_);
    }
    if (type_ != com.coalvalue.rpc.grpc.topicType.ALERT.getNumber()) {
      output.writeEnum(3, type_);
    }
    if (m_ != null) {
      output.writeMessage(4, getM());
    }
    if (t_ != null) {
      output.writeMessage(5, getT());
    }
    if (a_ != null) {
      output.writeMessage(6, getA());
    }
    unknownFields.writeTo(output);
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getClientNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, clientName_);
    }
    if (!getServerNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, serverName_);
    }
    if (type_ != com.coalvalue.rpc.grpc.topicType.ALERT.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(3, type_);
    }
    if (m_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(4, getM());
    }
    if (t_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(5, getT());
    }
    if (a_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(6, getA());
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.coalvalue.rpc.grpc.Notification)) {
      return super.equals(obj);
    }
    com.coalvalue.rpc.grpc.Notification other = (com.coalvalue.rpc.grpc.Notification) obj;

    boolean result = true;
    result = result && getClientName()
        .equals(other.getClientName());
    result = result && getServerName()
        .equals(other.getServerName());
    result = result && type_ == other.type_;
    result = result && (hasA() == other.hasA());
    if (hasA()) {
      result = result && getA()
          .equals(other.getA());
    }
    result = result && (hasM() == other.hasM());
    if (hasM()) {
      result = result && getM()
          .equals(other.getM());
    }
    result = result && (hasT() == other.hasT());
    if (hasT()) {
      result = result && getT()
          .equals(other.getT());
    }
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + CLIENTNAME_FIELD_NUMBER;
    hash = (53 * hash) + getClientName().hashCode();
    hash = (37 * hash) + SERVERNAME_FIELD_NUMBER;
    hash = (53 * hash) + getServerName().hashCode();
    hash = (37 * hash) + TYPE_FIELD_NUMBER;
    hash = (53 * hash) + type_;
    if (hasA()) {
      hash = (37 * hash) + A_FIELD_NUMBER;
      hash = (53 * hash) + getA().hashCode();
    }
    if (hasM()) {
      hash = (37 * hash) + M_FIELD_NUMBER;
      hash = (53 * hash) + getM().hashCode();
    }
    if (hasT()) {
      hash = (37 * hash) + T_FIELD_NUMBER;
      hash = (53 * hash) + getT().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.coalvalue.rpc.grpc.Notification parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.coalvalue.rpc.grpc.Notification parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.coalvalue.rpc.grpc.Notification parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.coalvalue.rpc.grpc.Notification parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.coalvalue.rpc.grpc.Notification parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.coalvalue.rpc.grpc.Notification parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.coalvalue.rpc.grpc.Notification parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.coalvalue.rpc.grpc.Notification parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.coalvalue.rpc.grpc.Notification parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.coalvalue.rpc.grpc.Notification parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.coalvalue.rpc.grpc.Notification parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.coalvalue.rpc.grpc.Notification parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.coalvalue.rpc.grpc.Notification prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code grpc.Notification}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:grpc.Notification)
      com.coalvalue.rpc.grpc.NotificationOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.coalvalue.rpc.grpc.HelloWorldServiceProto.internal_static_grpc_Notification_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.coalvalue.rpc.grpc.HelloWorldServiceProto.internal_static_grpc_Notification_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.coalvalue.rpc.grpc.Notification.class, com.coalvalue.rpc.grpc.Notification.Builder.class);
    }

    // Construct using com.coalvalue.rpc.grpc.Notification.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    public Builder clear() {
      super.clear();
      clientName_ = "";

      serverName_ = "";

      type_ = 0;

      if (aBuilder_ == null) {
        a_ = null;
      } else {
        a_ = null;
        aBuilder_ = null;
      }
      if (mBuilder_ == null) {
        m_ = null;
      } else {
        m_ = null;
        mBuilder_ = null;
      }
      if (tBuilder_ == null) {
        t_ = null;
      } else {
        t_ = null;
        tBuilder_ = null;
      }
      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.coalvalue.rpc.grpc.HelloWorldServiceProto.internal_static_grpc_Notification_descriptor;
    }

    public com.coalvalue.rpc.grpc.Notification getDefaultInstanceForType() {
      return com.coalvalue.rpc.grpc.Notification.getDefaultInstance();
    }

    public com.coalvalue.rpc.grpc.Notification build() {
      com.coalvalue.rpc.grpc.Notification result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public com.coalvalue.rpc.grpc.Notification buildPartial() {
      com.coalvalue.rpc.grpc.Notification result = new com.coalvalue.rpc.grpc.Notification(this);
      result.clientName_ = clientName_;
      result.serverName_ = serverName_;
      result.type_ = type_;
      if (aBuilder_ == null) {
        result.a_ = a_;
      } else {
        result.a_ = aBuilder_.build();
      }
      if (mBuilder_ == null) {
        result.m_ = m_;
      } else {
        result.m_ = mBuilder_.build();
      }
      if (tBuilder_ == null) {
        result.t_ = t_;
      } else {
        result.t_ = tBuilder_.build();
      }
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.coalvalue.rpc.grpc.Notification) {
        return mergeFrom((com.coalvalue.rpc.grpc.Notification)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.coalvalue.rpc.grpc.Notification other) {
      if (other == com.coalvalue.rpc.grpc.Notification.getDefaultInstance()) return this;
      if (!other.getClientName().isEmpty()) {
        clientName_ = other.clientName_;
        onChanged();
      }
      if (!other.getServerName().isEmpty()) {
        serverName_ = other.serverName_;
        onChanged();
      }
      if (other.type_ != 0) {
        setTypeValue(other.getTypeValue());
      }
      if (other.hasA()) {
        mergeA(other.getA());
      }
      if (other.hasM()) {
        mergeM(other.getM());
      }
      if (other.hasT()) {
        mergeT(other.getT());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.coalvalue.rpc.grpc.Notification parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.coalvalue.rpc.grpc.Notification) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object clientName_ = "";
    /**
     * <code>string clientName = 1;</code>
     */
    public java.lang.String getClientName() {
      java.lang.Object ref = clientName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        clientName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string clientName = 1;</code>
     */
    public com.google.protobuf.ByteString
        getClientNameBytes() {
      java.lang.Object ref = clientName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        clientName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string clientName = 1;</code>
     */
    public Builder setClientName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      clientName_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string clientName = 1;</code>
     */
    public Builder clearClientName() {
      
      clientName_ = getDefaultInstance().getClientName();
      onChanged();
      return this;
    }
    /**
     * <code>string clientName = 1;</code>
     */
    public Builder setClientNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      clientName_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object serverName_ = "";
    /**
     * <code>string serverName = 2;</code>
     */
    public java.lang.String getServerName() {
      java.lang.Object ref = serverName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        serverName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string serverName = 2;</code>
     */
    public com.google.protobuf.ByteString
        getServerNameBytes() {
      java.lang.Object ref = serverName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        serverName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string serverName = 2;</code>
     */
    public Builder setServerName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      serverName_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string serverName = 2;</code>
     */
    public Builder clearServerName() {
      
      serverName_ = getDefaultInstance().getServerName();
      onChanged();
      return this;
    }
    /**
     * <code>string serverName = 2;</code>
     */
    public Builder setServerNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      serverName_ = value;
      onChanged();
      return this;
    }

    private int type_ = 0;
    /**
     * <code>.grpc.topicType type = 3;</code>
     */
    public int getTypeValue() {
      return type_;
    }
    /**
     * <code>.grpc.topicType type = 3;</code>
     */
    public Builder setTypeValue(int value) {
      type_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.grpc.topicType type = 3;</code>
     */
    public com.coalvalue.rpc.grpc.topicType getType() {
      com.coalvalue.rpc.grpc.topicType result = com.coalvalue.rpc.grpc.topicType.valueOf(type_);
      return result == null ? com.coalvalue.rpc.grpc.topicType.UNRECOGNIZED : result;
    }
    /**
     * <code>.grpc.topicType type = 3;</code>
     */
    public Builder setType(com.coalvalue.rpc.grpc.topicType value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      type_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.grpc.topicType type = 3;</code>
     */
    public Builder clearType() {
      
      type_ = 0;
      onChanged();
      return this;
    }

    private com.coalvalue.rpc.grpc.Alert a_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.coalvalue.rpc.grpc.Alert, com.coalvalue.rpc.grpc.Alert.Builder, com.coalvalue.rpc.grpc.AlertOrBuilder> aBuilder_;
    /**
     * <code>.grpc.Alert a = 6;</code>
     */
    public boolean hasA() {
      return aBuilder_ != null || a_ != null;
    }
    /**
     * <code>.grpc.Alert a = 6;</code>
     */
    public com.coalvalue.rpc.grpc.Alert getA() {
      if (aBuilder_ == null) {
        return a_ == null ? com.coalvalue.rpc.grpc.Alert.getDefaultInstance() : a_;
      } else {
        return aBuilder_.getMessage();
      }
    }
    /**
     * <code>.grpc.Alert a = 6;</code>
     */
    public Builder setA(com.coalvalue.rpc.grpc.Alert value) {
      if (aBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        a_ = value;
        onChanged();
      } else {
        aBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.grpc.Alert a = 6;</code>
     */
    public Builder setA(
        com.coalvalue.rpc.grpc.Alert.Builder builderForValue) {
      if (aBuilder_ == null) {
        a_ = builderForValue.build();
        onChanged();
      } else {
        aBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.grpc.Alert a = 6;</code>
     */
    public Builder mergeA(com.coalvalue.rpc.grpc.Alert value) {
      if (aBuilder_ == null) {
        if (a_ != null) {
          a_ =
            com.coalvalue.rpc.grpc.Alert.newBuilder(a_).mergeFrom(value).buildPartial();
        } else {
          a_ = value;
        }
        onChanged();
      } else {
        aBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.grpc.Alert a = 6;</code>
     */
    public Builder clearA() {
      if (aBuilder_ == null) {
        a_ = null;
        onChanged();
      } else {
        a_ = null;
        aBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.grpc.Alert a = 6;</code>
     */
    public com.coalvalue.rpc.grpc.Alert.Builder getABuilder() {
      
      onChanged();
      return getAFieldBuilder().getBuilder();
    }
    /**
     * <code>.grpc.Alert a = 6;</code>
     */
    public com.coalvalue.rpc.grpc.AlertOrBuilder getAOrBuilder() {
      if (aBuilder_ != null) {
        return aBuilder_.getMessageOrBuilder();
      } else {
        return a_ == null ?
            com.coalvalue.rpc.grpc.Alert.getDefaultInstance() : a_;
      }
    }
    /**
     * <code>.grpc.Alert a = 6;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.coalvalue.rpc.grpc.Alert, com.coalvalue.rpc.grpc.Alert.Builder, com.coalvalue.rpc.grpc.AlertOrBuilder> 
        getAFieldBuilder() {
      if (aBuilder_ == null) {
        aBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.coalvalue.rpc.grpc.Alert, com.coalvalue.rpc.grpc.Alert.Builder, com.coalvalue.rpc.grpc.AlertOrBuilder>(
                getA(),
                getParentForChildren(),
                isClean());
        a_ = null;
      }
      return aBuilder_;
    }

    private com.coalvalue.rpc.grpc.Mode m_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.coalvalue.rpc.grpc.Mode, com.coalvalue.rpc.grpc.Mode.Builder, com.coalvalue.rpc.grpc.ModeOrBuilder> mBuilder_;
    /**
     * <code>.grpc.Mode m = 4;</code>
     */
    public boolean hasM() {
      return mBuilder_ != null || m_ != null;
    }
    /**
     * <code>.grpc.Mode m = 4;</code>
     */
    public com.coalvalue.rpc.grpc.Mode getM() {
      if (mBuilder_ == null) {
        return m_ == null ? com.coalvalue.rpc.grpc.Mode.getDefaultInstance() : m_;
      } else {
        return mBuilder_.getMessage();
      }
    }
    /**
     * <code>.grpc.Mode m = 4;</code>
     */
    public Builder setM(com.coalvalue.rpc.grpc.Mode value) {
      if (mBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        m_ = value;
        onChanged();
      } else {
        mBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.grpc.Mode m = 4;</code>
     */
    public Builder setM(
        com.coalvalue.rpc.grpc.Mode.Builder builderForValue) {
      if (mBuilder_ == null) {
        m_ = builderForValue.build();
        onChanged();
      } else {
        mBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.grpc.Mode m = 4;</code>
     */
    public Builder mergeM(com.coalvalue.rpc.grpc.Mode value) {
      if (mBuilder_ == null) {
        if (m_ != null) {
          m_ =
            com.coalvalue.rpc.grpc.Mode.newBuilder(m_).mergeFrom(value).buildPartial();
        } else {
          m_ = value;
        }
        onChanged();
      } else {
        mBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.grpc.Mode m = 4;</code>
     */
    public Builder clearM() {
      if (mBuilder_ == null) {
        m_ = null;
        onChanged();
      } else {
        m_ = null;
        mBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.grpc.Mode m = 4;</code>
     */
    public com.coalvalue.rpc.grpc.Mode.Builder getMBuilder() {
      
      onChanged();
      return getMFieldBuilder().getBuilder();
    }
    /**
     * <code>.grpc.Mode m = 4;</code>
     */
    public com.coalvalue.rpc.grpc.ModeOrBuilder getMOrBuilder() {
      if (mBuilder_ != null) {
        return mBuilder_.getMessageOrBuilder();
      } else {
        return m_ == null ?
            com.coalvalue.rpc.grpc.Mode.getDefaultInstance() : m_;
      }
    }
    /**
     * <code>.grpc.Mode m = 4;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.coalvalue.rpc.grpc.Mode, com.coalvalue.rpc.grpc.Mode.Builder, com.coalvalue.rpc.grpc.ModeOrBuilder> 
        getMFieldBuilder() {
      if (mBuilder_ == null) {
        mBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.coalvalue.rpc.grpc.Mode, com.coalvalue.rpc.grpc.Mode.Builder, com.coalvalue.rpc.grpc.ModeOrBuilder>(
                getM(),
                getParentForChildren(),
                isClean());
        m_ = null;
      }
      return mBuilder_;
    }

    private com.coalvalue.rpc.grpc.Temp t_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.coalvalue.rpc.grpc.Temp, com.coalvalue.rpc.grpc.Temp.Builder, com.coalvalue.rpc.grpc.TempOrBuilder> tBuilder_;
    /**
     * <code>.grpc.Temp t = 5;</code>
     */
    public boolean hasT() {
      return tBuilder_ != null || t_ != null;
    }
    /**
     * <code>.grpc.Temp t = 5;</code>
     */
    public com.coalvalue.rpc.grpc.Temp getT() {
      if (tBuilder_ == null) {
        return t_ == null ? com.coalvalue.rpc.grpc.Temp.getDefaultInstance() : t_;
      } else {
        return tBuilder_.getMessage();
      }
    }
    /**
     * <code>.grpc.Temp t = 5;</code>
     */
    public Builder setT(com.coalvalue.rpc.grpc.Temp value) {
      if (tBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        t_ = value;
        onChanged();
      } else {
        tBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.grpc.Temp t = 5;</code>
     */
    public Builder setT(
        com.coalvalue.rpc.grpc.Temp.Builder builderForValue) {
      if (tBuilder_ == null) {
        t_ = builderForValue.build();
        onChanged();
      } else {
        tBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.grpc.Temp t = 5;</code>
     */
    public Builder mergeT(com.coalvalue.rpc.grpc.Temp value) {
      if (tBuilder_ == null) {
        if (t_ != null) {
          t_ =
            com.coalvalue.rpc.grpc.Temp.newBuilder(t_).mergeFrom(value).buildPartial();
        } else {
          t_ = value;
        }
        onChanged();
      } else {
        tBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.grpc.Temp t = 5;</code>
     */
    public Builder clearT() {
      if (tBuilder_ == null) {
        t_ = null;
        onChanged();
      } else {
        t_ = null;
        tBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.grpc.Temp t = 5;</code>
     */
    public com.coalvalue.rpc.grpc.Temp.Builder getTBuilder() {
      
      onChanged();
      return getTFieldBuilder().getBuilder();
    }
    /**
     * <code>.grpc.Temp t = 5;</code>
     */
    public com.coalvalue.rpc.grpc.TempOrBuilder getTOrBuilder() {
      if (tBuilder_ != null) {
        return tBuilder_.getMessageOrBuilder();
      } else {
        return t_ == null ?
            com.coalvalue.rpc.grpc.Temp.getDefaultInstance() : t_;
      }
    }
    /**
     * <code>.grpc.Temp t = 5;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.coalvalue.rpc.grpc.Temp, com.coalvalue.rpc.grpc.Temp.Builder, com.coalvalue.rpc.grpc.TempOrBuilder> 
        getTFieldBuilder() {
      if (tBuilder_ == null) {
        tBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.coalvalue.rpc.grpc.Temp, com.coalvalue.rpc.grpc.Temp.Builder, com.coalvalue.rpc.grpc.TempOrBuilder>(
                getT(),
                getParentForChildren(),
                isClean());
        t_ = null;
      }
      return tBuilder_;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:grpc.Notification)
  }

  // @@protoc_insertion_point(class_scope:grpc.Notification)
  private static final com.coalvalue.rpc.grpc.Notification DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.coalvalue.rpc.grpc.Notification();
  }

  public static com.coalvalue.rpc.grpc.Notification getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Notification>
      PARSER = new com.google.protobuf.AbstractParser<Notification>() {
    public Notification parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Notification(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Notification> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Notification> getParserForType() {
    return PARSER;
  }

  public com.coalvalue.rpc.grpc.Notification getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
