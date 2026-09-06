package com.eventhub.eventservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BookingKafkaConsumer {

    @KafkaListener(
            topics = "booking-created",
            groupId = "event-service-group"
    )
    public void consumeBookingCreatedEvent(String message) {

        System.out.println("======================================");
        System.out.println("Booking event received by Event Service");
        System.out.println("Message: " + message);
        System.out.println("======================================");
    }
}
