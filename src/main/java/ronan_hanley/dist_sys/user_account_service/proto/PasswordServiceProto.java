// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: password-service.proto

package ronan_hanley.dist_sys.user_account_service.proto;

public final class PasswordServiceProto {
  private PasswordServiceProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_HashPair_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_HashPair_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_HashRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_HashRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_HashResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_HashResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ValidateRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ValidateRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ValidateResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ValidateResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\026password-service.proto\"&\n\010HashPair\022\014\n\004" +
      "hash\030\001 \001(\014\022\014\n\004salt\030\002 \001(\014\"/\n\013HashRequest\022" +
      "\016\n\006userId\030\001 \001(\005\022\020\n\010password\030\002 \001(\t\";\n\014Has" +
      "hResponse\022\016\n\006userId\030\001 \001(\005\022\033\n\010hashPair\030\002 " +
      "\001(\0132\t.HashPair\"@\n\017ValidateRequest\022\020\n\010pas" +
      "sword\030\001 \001(\t\022\033\n\010hashPair\030\002 \001(\0132\t.HashPair" +
      "\"!\n\020ValidateResponse\022\r\n\005valid\030\001 \001(\0102k\n\017P" +
      "asswordService\022%\n\004Hash\022\014.HashRequest\032\r.H" +
      "ashResponse\"\000\0221\n\010Validate\022\020.ValidateRequ" +
      "est\032\021.ValidateResponse\"\000BK\n1ronan_hanley" +
      ".dist_sys.user_account_service.protoB\024P" +
      "asswordServiceProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_HashPair_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_HashPair_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_HashPair_descriptor,
        new String[] { "Hash", "Salt", });
    internal_static_HashRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_HashRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_HashRequest_descriptor,
        new String[] { "UserId", "Passwords", });
    internal_static_HashResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_HashResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_HashResponse_descriptor,
        new String[] { "UserId", "HashPair", });
    internal_static_ValidateRequest_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_ValidateRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ValidateRequest_descriptor,
        new String[] { "Passwords", "HashPair", });
    internal_static_ValidateResponse_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_ValidateResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ValidateResponse_descriptor,
        new String[] { "Valid", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
