/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package in.yayd.era.food.ordering.project.kafka.order.avro.model;
@org.apache.avro.specific.AvroGenerated
public enum PaymentOrderStatus implements org.apache.avro.generic.GenericEnumSymbol<PaymentOrderStatus> {
  PENDING, CANCELLED  ;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"PaymentOrderStatus\",\"namespace\":\"in.yayd.era.food.ordering.project.kafka.order.avro.model\",\"symbols\":[\"PENDING\",\"CANCELLED\"]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
}
