// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: dimitStoreConf.proto

package dimit.store.conf;

public interface DimitConfOrBuilder extends
    // @@protoc_insertion_point(interface_extends:DimitConf)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>uint32 v = 1;</code>
   */
  int getV();

  /**
   * <pre>
   * dimit id
   * </pre>
   *
   * <code>string id = 2;</code>
   */
  java.lang.String getId();
  /**
   * <pre>
   * dimit id
   * </pre>
   *
   * <code>string id = 2;</code>
   */
  com.google.protobuf.ByteString
      getIdBytes();

  /**
   * <code>string name = 3;</code>
   */
  java.lang.String getName();
  /**
   * <code>string name = 3;</code>
   */
  com.google.protobuf.ByteString
      getNameBytes();

  /**
   * <pre>
   * timestamp
   * </pre>
   *
   * <code>uint64 ct = 9;</code>
   */
  long getCt();

  /**
   * <pre>
   * modify timestamp
   * </pre>
   *
   * <code>uint64 mt = 10;</code>
   */
  long getMt();
}
