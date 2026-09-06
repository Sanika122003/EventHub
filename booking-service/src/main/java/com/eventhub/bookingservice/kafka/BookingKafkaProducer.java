package com.eventhub.bookingservice.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookingKafkaProducer {

    private static final String TOPIC = "booking-created";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public BookingKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookingCreatedEvent(String message) {

        kafkaTemplate.send(TOPIC, message);

        System.out.println("Booking event published to Kafka: " + message);
    }
}
