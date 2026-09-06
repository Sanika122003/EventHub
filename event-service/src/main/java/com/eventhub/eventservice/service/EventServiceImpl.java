package com.eventhub.eventservice.service;

import com.eventhub.eventservice.dto.request.AddEventRequest;
import com.eventhub.eventservice.dto.response.EventResponse;
import com.eventhub.eventservice.entity.Event;
import com.eventhub.eventservice.enums.Category;
import com.eventhub.eventservice.exception.EventNotFoundException;
import com.eventhub.eventservice.repository.EventRepository;
import com.eventhub.eventservice.specification.EventSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.eventhub.eventservice.dto.request.UpdateEventRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EventServiceImpl implements EventService {
    private static final Logger logger =
            LoggerFactory.getLogger(EventServiceImpl.class);

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void addEvent(AddEventRequest request) {

        Event event = new Event();

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setDate(request.getDate());
        event.setTime(request.getTime());
        event.setPrice(request.getPrice());
        event.setTotalSeats(request.getTotalSeats());
        event.setImageUrl(request.getImageUrl());
        event.setCategory(request.getCategory());

        // Temporary value until authentication is added
        event.setCreatedBy("admin@eventhub.com");

        Event savedEvent = eventRepository.save(event);

        logger.info("Event created successfully with ID: {}", savedEvent.getId());
    }
    @Override
    public List<EventResponse> getAllEvents() {

        List<Event> events = eventRepository.findAll();

        return events.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    private EventResponse mapToResponse(Event event) {

        EventResponse response = new EventResponse();

        response.setId(event.getId());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setLocation(event.getLocation());
        response.setDate(event.getDate());
        response.setTime(event.getTime());
        response.setPrice(event.getPrice());
        response.setTotalSeats(event.getTotalSeats());
        response.setAvailableSeats(event.getAvailableSeats());
        response.setImageUrl(event.getImageUrl());
        response.setCategory(event.getCategory());
        response.setStatus(event.getStatus());

        return response;
    }
    @Override
    public EventResponse getEventById(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Event not found with ID: {}", id);
                    return new EventNotFoundException("Event not found with id: " + id);
                });

        logger.info("Fetched event with ID: {}", id);

        return mapToResponse(event);
    }

    @Override
    public void updateEvent(Long id, UpdateEventRequest request) {
        logger.info("Updating event with ID: {}", id);

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Event not found with ID: {}", id);
                    return new EventNotFoundException("Event not found with id: " + id);
                });

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setDate(request.getDate());
        event.setTime(request.getTime());
        event.setPrice(request.getPrice());
        event.setTotalSeats(request.getTotalSeats());
        event.setImageUrl(request.getImageUrl());
        event.setCategory(request.getCategory());


        if (event.getAvailableSeats() > request.getTotalSeats()) {
            event.setAvailableSeats(request.getTotalSeats());
        }

        eventRepository.save(event);
        logger.info("Event updated successfully with ID: {}", id);
    }

    @Override
    public void deleteEvent(Long id) {
        logger.info("Deleting event with ID: {}", id);

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Event not found with ID: {}", id);
                    return new EventNotFoundException("Event not found with id: " + id);
                });

        eventRepository.delete(event);
        logger.info("Event deleted successfully with ID: {}", id);
    }
    @Override
    public List<EventResponse> searchEvents(String keyword) {

        List<Event> events =
                eventRepository.findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(
                        keyword,
                        keyword
                );

        return events.stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public List<EventResponse> getEventsByCategory(Category category) {

        List<Event> events = eventRepository.findByCategory(category);

        return events.stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public List<EventResponse> getUpcomingEvents() {

        List<Event> events = eventRepository
                .findByDateGreaterThanEqualOrderByDateAsc(LocalDate.now());

        return events.stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public Page<EventResponse> getAllEvents(Pageable pageable) {

        Page<Event> events = eventRepository.findAll(pageable);

        return events.map(this::mapToResponse);
    }
    @Override
    public Page<EventResponse> filterEvents(
            String keyword,
            Category category,
            Pageable pageable) {

        Specification<Event> specification =
                EventSpecification.hasKeyword(keyword)
                        .and(EventSpecification.hasCategory(category));

        Page<Event> events =
                eventRepository.findAll(specification, pageable);

        return events.map(this::mapToResponse);
    }
    @Override
    public void updateAvailableSeats(Long id, Integer seats) {

        logger.info("Updating available seats for eventId: {} to {}",
                id, seats);

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Event not found with ID: {}", id);

                    return new EventNotFoundException(
                            "Event not found with id: " + id);
                });

        if (seats < 0) {
            throw new IllegalArgumentException(
                    "Available seats cannot be negative.");
        }

        if (seats > event.getTotalSeats()) {
            throw new IllegalArgumentException(
                    "Available seats cannot exceed total seats.");
        }

        event.setAvailableSeats(seats);

        eventRepository.save(event);

        logger.info(
                "Available seats updated successfully for eventId: {}. Available seats: {}",
                id,
                seats
        );
    }
}