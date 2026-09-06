package com.eventhub.bookingservice.controller;

import com.eventhub.bookingservice.dto.request.CreateBookingRequest;
import com.eventhub.bookingservice.dto.response.BookingResponse;
import com.eventhub.bookingservice.dto.response.EventResponse;
import com.eventhub.bookingservice.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<String> createBooking(
            @Valid @RequestBody CreateBookingRequest request) {

        bookingService.createBooking(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Booking created successfully.");
    }

    @GetMapping
    public ResponseEntity<Page<BookingResponse>> getAllBookings(
            Pageable pageable) {

        return ResponseEntity.ok(
                bookingService.getAllBookings(pageable)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.getBookingById(id)
        );
    }
    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelBooking(
            @PathVariable Long id) {

        bookingService.cancelBooking(id);

        return ResponseEntity.ok("Booking cancelled successfully.");
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                bookingService.getBookingsByUser(userId)
        );
    }
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByEvent(
            @PathVariable Long eventId) {

        return ResponseEntity.ok(
                bookingService.getBookingsByEvent(eventId)
        );
    }
    @GetMapping("/event-details/{eventId}")
    public ResponseEntity<EventResponse> getEventDetails(
            @PathVariable Long eventId) {

        return ResponseEntity.ok(
                bookingService.getEventDetails(eventId)
        );
    }
}