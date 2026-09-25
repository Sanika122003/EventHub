package com.eventhub.bookingservice.service;

import com.eventhub.bookingservice.client.EventServiceClient;
import com.eventhub.bookingservice.dto.request.CreateBookingRequest;
import com.eventhub.bookingservice.dto.response.BookingResponse;
import com.eventhub.bookingservice.dto.response.EventResponse;
import com.eventhub.bookingservice.entity.Booking;
import com.eventhub.bookingservice.enums.BookingStatus;
import com.eventhub.bookingservice.exception.BookingNotFoundException;
import com.eventhub.bookingservice.kafka.BookingKafkaProducer;
import com.eventhub.bookingservice.repository.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger logger =
            LoggerFactory.getLogger(BookingServiceImpl.class);

    private final BookingRepository bookingRepository;
    private final EventServiceClient eventServiceClient;
    private final BookingKafkaProducer bookingKafkaProducer;

    public BookingServiceImpl(
            BookingRepository bookingRepository,
            EventServiceClient eventServiceClient,
            BookingKafkaProducer bookingKafkaProducer) {

        this.bookingRepository = bookingRepository;
        this.eventServiceClient = eventServiceClient;
        this.bookingKafkaProducer = bookingKafkaProducer;
    }

    @Override
    public void createBooking(CreateBookingRequest request) {

        logger.info("Creating booking for userId: {}, eventId: {}, tickets: {}",
                request.getUserId(),
                request.getEventId(),
                request.getNumberOfTickets());

        // 1. Get event details from Event Service
        EventResponse event = eventServiceClient.getEventById(request.getEventId());

        logger.info("Event fetched successfully: {}",
                event.getTitle());

        // 2. Check available seats
        if (event.getAvailableSeats() < request.getNumberOfTickets()) {

            logger.warn(
                    "Not enough seats available for eventId: {}. Requested: {}, Available: {}",
                    request.getEventId(),
                    request.getNumberOfTickets(),
                    event.getAvailableSeats()
            );

            throw new RuntimeException("Not enough seats available.");
        }
        // 3. Calculate remaining seats
        int remainingSeats =
                event.getAvailableSeats() - request.getNumberOfTickets();

        logger.info("Remaining seats for eventId: {}: {}",
                request.getEventId(),
                remainingSeats);

// 4. Update available seats in Event Service
        eventServiceClient.updateAvailableSeats(
                request.getEventId(),
                remainingSeats
        );

        // 3. Calculate total amount
        double totalAmount =
                event.getPrice() * request.getNumberOfTickets();

        logger.info("Total booking amount: {}", totalAmount);

        // 4. Create booking
        Booking booking = new Booking();

        booking.setBookingReference(UUID.randomUUID().toString());

        booking.setUserId(request.getUserId());

        booking.setEventId(request.getEventId());

        booking.setNumberOfTickets(request.getNumberOfTickets());

        booking.setTotalAmount(totalAmount);

        booking.setBookingStatus(BookingStatus.CONFIRMED);

        // 5. Save booking
        bookingRepository.save(booking);

        logger.info("Booking created successfully with reference: {}",
                booking.getBookingReference());

        // 6. Publish Kafka event
        String message =
                "Booking created: " + booking.getBookingReference();

        bookingKafkaProducer.sendBookingCreatedEvent(message);
    }

    @Override
    public Page<BookingResponse> getAllBookings(Pageable pageable) {

        logger.info("Fetching bookings - page: {}, size: {}, sort: {}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort());

        Page<Booking> bookings = bookingRepository.findAll(pageable);

        logger.info("Found {} bookings on page {}",
                bookings.getNumberOfElements(),
                bookings.getNumber());

        return bookings.map(this::mapToResponse);
    }




    private BookingResponse mapToResponse(Booking booking) {

        BookingResponse response = new BookingResponse();

        response.setId(booking.getId());
        response.setBookingReference(booking.getBookingReference());
        response.setUserId(booking.getUserId());
        response.setEventId(booking.getEventId());
        response.setNumberOfTickets(booking.getNumberOfTickets());
        response.setTotalAmount(booking.getTotalAmount());
        response.setBookingStatus(booking.getBookingStatus());
        response.setBookingDate(booking.getBookingDate());

        return response;
    }

    @Override
    public BookingResponse getBookingById(Long id) {

        logger.info("Fetching booking with id: {}", id);

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Booking not found with id: {}", id);

                    return new BookingNotFoundException(
                            "Booking not found with id: " + id);
                });

        logger.info("Booking found with id: {}", id);

        return mapToResponse(booking);
    }

    @Override
    public void cancelBooking(Long id) {

        logger.info("Cancelling booking with id: {}", id);

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Cannot cancel booking. Booking not found with id: {}", id);

                    return new BookingNotFoundException(
                            "Booking not found with id: " + id);
                });

        booking.setBookingStatus(BookingStatus.CANCELLED);

        bookingRepository.save(booking);

        logger.info("Booking cancelled successfully with id: {}", id);
    }

    @Override
    public List<BookingResponse> getBookingsByUser(Long userId) {

        logger.info("Fetching bookings for userId: {}", userId);

        List<Booking> bookings = bookingRepository.findByUserId(userId);

        logger.info("Found {} bookings for userId: {}",
                bookings.size(), userId);

        return bookings.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<BookingResponse> getBookingsByEvent(Long eventId) {

        logger.info("Fetching bookings for eventId: {}", eventId);

        List<Booking> bookings = bookingRepository.findByEventId(eventId);

        logger.info("Found {} bookings for eventId: {}",
                bookings.size(), eventId);

        return bookings.stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public EventResponse getEventDetails(Long eventId) {

        logger.info("Fetching event details from Event Service for eventId: {}", eventId);

        EventResponse event = eventServiceClient.getEventById(eventId);

        logger.info("Received event details for eventId: {}", eventId);

        return event;
    }
}
