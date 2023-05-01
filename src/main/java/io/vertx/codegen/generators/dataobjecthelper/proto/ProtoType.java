package io.vertx.codegen.generators.dataobjecthelper.proto;

public enum ProtoType {
  STRING  ("string",  "String"),
  INT32   ("int32",   "Int32"),
  INT64   ("int64",   "Int64"),
  DOUBLE  ("double",  "Double"),
  FLOAT   ("float",   "Float"),
  BOOL    ("bool",    "Bool"),
  BYTES   ("bytes",   "Bytes");

  public final String value;
  public final String camelValue;

  ProtoType(String value, String camelValue) {
    this.value = value;
    this.camelValue = camelValue;
  }

  public String read() {
    return "read" + camelValue;
  }

  public String write() {
    return "write" + camelValue;
  }

  public String writeNoTag() {
    return "write" + camelValue + "NoTag";
  }

  public String computeSize() {
    return "compute" + camelValue + "Size";
  }

  public String computeSizeNoTag() {
    return "compute" + camelValue + "SizeNoTag";
  }
}
