package io.vertx.codegen.protobuf.annotations;

public enum FieldNumberStrategy {
  /**
   * Field numbers must be assigned explicitly (using {@link ProtobufField @ProtobufField})
   * for all properties of the data object. It is an error when two properties are assigned
   * the same field number.
   * <p>
   * When removing properties, it is recommended to put their field numbers and names into
   * the <em>reserved</em> set to prevent accidentally assigning the same field number
   * to a different property in the future.
   */
  MANUAL,
  /**
   * Field numbers are assigned automatically for all properties of the data object.
   * First, all properties of the data object that have an explicitly assigned field number
   * (using {@link ProtobufField @ProtobufField}) are collected and their field numbers
   * are remembered. Then, properties of the data object are iterated in declaration order
   * and for each of them that does not have an explicitly assigned field number, a field
   * number is assigned, starting from {@code 1} for the first property and increasing
   * sequentially, skipping the explicitly assigned field numbers.
   * <p>
   * With this strategy, it is crucial to <strong>never remove properties</strong> (they must
   * be either left in place or added to the <em>reserved</em> set), and <strong>always add
   * properties at the end</strong>, even if they logically belong elsewhere.
   */
  COMPACT,
  /**
   * Field numbers are assigned automatically for all properties of the data object.
   * The properties, taken in declaration order, are divided into segments where each
   * property with an explicitly assigned field number (using {@link ProtobufField @ProtobufField})
   * starts a new segment. The explicitly assigned field number that starts a segment
   * is called the <em>initial field number</em> of the segment. There may be an additional
   * segment at the very beginning, if the first property does not have an explicitly
   * assigned field number; its initial field number is {@code 1}. Field numbers are
   * assigned sequentially in each segment, where the first property of the segment
   * has the initial field number of the segment. It is an error if two segments
   * have overlapping field numbers.
   * <p>
   * With this strategy, it is crucial to <strong>never remove properties</strong> (they must
   * be either left in place or added to the <em>reserved</em> set), and <strong>always add
   * properties at the end of the segments</strong>, even if they logically belong elsewhere.
   */
  SEGMENTED,
}
