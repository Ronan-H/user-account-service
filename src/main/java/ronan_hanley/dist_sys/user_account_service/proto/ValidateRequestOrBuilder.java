// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: password-service.proto

package ronan_hanley.dist_sys.user_account_service.proto;

public interface ValidateRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:ValidateRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string password = 1;</code>
   */
  String getPassword();
  /**
   * <code>string password = 1;</code>
   */
  com.google.protobuf.ByteString
      getPasswordBytes();

  /**
   * <code>.HashPair hashPair = 2;</code>
   */
  boolean hasHashPair();
  /**
   * <code>.HashPair hashPair = 2;</code>
   */
  HashPair getHashPair();
  /**
   * <code>.HashPair hashPair = 2;</code>
   */
  HashPairOrBuilder getHashPairOrBuilder();
}
