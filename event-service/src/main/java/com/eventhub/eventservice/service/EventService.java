package com.eventhub.eventservice.service;

import com.eventhub.eventservice.dto.request.AddEventRequest;
import com.eventhub.eventservice.dto.request.UpdateEventRequest;
import com.eventhub.eventservice.dto.response.EventResponse;
import com.eventhub.eventservice.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {

    void addEvent(AddEventRequest request);

    List<EventResponse> getAllEvents();

    EventResponse getEventById(Long id);

    void updateEvent(Long id, UpdateEventRequest request);
    void updateAvailableSeats(Long id, Integer seats);

    void deleteEvent(Long id);
    List<EventResponse> searchEvents(String keyword);
    List<EventResponse> getEventsByCategory(Category category);
    List<EventResponse> getUpcomingEvents();
    Page<EventResponse> getAllEvents(Pageable pageable);
    Page<EventResponse> filterEvents(
            String keyword,
            Category category,
            Pageable pageable
    );
}