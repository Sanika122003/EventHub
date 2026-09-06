package com.eventhub.bookingservice.client;

import com.eventhub.bookingservice.dto.response.EventResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "event-service",
        url = "http://host.docker.internal:8082"
)
public interface EventServiceClient {

    @GetMapping("/api/events/{id}")
    EventResponse getEventById(@PathVariable("id") Long id);
    @PutMapping("/api/events/{id}/seats")
    void updateAvailableSeats(
            @PathVariable("id") Long id,
            @RequestParam Integer seats);
}
