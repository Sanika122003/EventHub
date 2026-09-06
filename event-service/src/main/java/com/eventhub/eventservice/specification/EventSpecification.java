package com.eventhub.eventservice.specification;

import com.eventhub.eventservice.entity.Event;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.util.StringUtils;
import com.eventhub.eventservice.enums.Category;

public class EventSpecification {
    public static Specification<Event> hasKeyword(String keyword) {

        return (root, query, criteriaBuilder) -> {

            if (!StringUtils.hasText(keyword)) {
                return criteriaBuilder.conjunction();
            }

            String search = "%" + keyword.toLowerCase() + "%";

            Predicate title =
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("title")),
                            search
                    );

            Predicate location =
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("location")),
                            search
                    );

            return criteriaBuilder.or(title, location);
        };
    }
    public static Specification<Event> hasCategory(Category category) {

        return (root, query, criteriaBuilder) -> {

            if (category == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(
                    root.get("category"),
                    category
            );
        };
    }
}