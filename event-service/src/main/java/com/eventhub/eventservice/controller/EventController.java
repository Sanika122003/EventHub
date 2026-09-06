package com.eventhub.eventservice.controller;

import com.eventhub.eventservice.dto.request.AddEventRequest;
import com.eventhub.eventservice.dto.request.UpdateEventRequest;
import com.eventhub.eventservice.enums.Category;
import com.eventhub.eventservice.service.EventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.eventhub.eventservice.dto.response.EventResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
@Tag(
        name = "Event APIs",
        description = "Operations for managing events"
)
@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<String> addEvent(
            @Valid @RequestBody AddEventRequest request) {

        eventService.addEvent(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Event added successfully.");
    }
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {

        List<EventResponse> events = eventService.getAllEvents();

        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {

        EventResponse response = eventService.getEventById(id);

        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEventRequest request) {

        eventService.updateEvent(id, request);

        return ResponseEntity.ok("Event updated successfully.");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long id) {

        eventService.deleteEvent(id);

        return ResponseEntity.ok("Event deleted successfully.");
    }
    @GetMapping("/search")
    public ResponseEntity<List<EventResponse>> searchEvents(
            @RequestParam String keyword) {

        List<EventResponse> events = eventService.searchEvents(keyword);

        return ResponseEntity.ok(events);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<EventResponse>> getEventsByCategory(
            @PathVariable Category category) {

        List<EventResponse> events = eventService.getEventsByCategory(category);

        return ResponseEntity.ok(events);
    }
    @GetMapping("/upcoming")
    public ResponseEntity<List<EventResponse>> getUpcomingEvents() {

        List<EventResponse> events = eventService.getUpcomingEvents();

        return ResponseEntity.ok(events);
    }
    @GetMapping("/page")
    public ResponseEntity<Page<EventResponse>> getAllEvents(
            @PageableDefault(size = 5) Pageable pageable) {

        Page<EventResponse> events = eventService.getAllEvents(pageable);

        return ResponseEntity.ok(events);
    }
    @GetMapping("/filter")
    public ResponseEntity<Page<EventResponse>> filterEvents(

            @RequestParam(required = false) String keyword,

            @RequestParam(required = false) Category category,

            @PageableDefault(size = 5) Pageable pageable) {

        Page<EventResponse> events =
                eventService.filterEvents(keyword, category, pageable);

        return ResponseEntity.ok(events);
    }
    @PutMapping("/{id}/seats")
    public ResponseEntity<String> updateAvailableSeats(
            @PathVariable Long id,
            @RequestParam Integer seats) {

        eventService.updateAvailableSeats(id, seats);

        return ResponseEntity.ok("Available seats updated successfully.");
    }
}