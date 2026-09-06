package com.eventhub.eventservice.repository;

import com.eventhub.eventservice.entity.Event;
import com.eventhub.eventservice.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    List<Event> findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(
            String title,
            String location
    );
    List<Event> findByCategory(Category category);
    List<Event> findByDateGreaterThanEqualOrderByDateAsc(LocalDate date);

}