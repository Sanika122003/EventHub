package com.eventhub.bookingservice.service;

import com.eventhub.bookingservice.dto.request.CreateBookingRequest;
import com.eventhub.bookingservice.dto.response.BookingResponse;
import com.eventhub.bookingservice.dto.response.EventResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {

    void createBooking(CreateBookingRequest request);

    Page<BookingResponse> getAllBookings(Pageable pageable);

    BookingResponse getBookingById(Long id);

    void cancelBooking(Long id);

    List<BookingResponse> getBookingsByUser(Long userId);

    List<BookingResponse> getBookingsByEvent(Long eventId);
    EventResponse getEventDetails(Long eventId);
}
