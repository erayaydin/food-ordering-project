package in.yayd.era.food.ordering.project.kafka.producer.exception;

public class KafkaProducerException extends RuntimeException {
    public KafkaProducerException(String message) {
        super(message);
    }
}
