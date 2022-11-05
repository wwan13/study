package com.taewan.inflearnSpringRESTAPI.events;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder()
                .name("Inflearn Spring REST API")
                .description("REST API development with spring")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        Event event = new Event();
        String name = "Event";
        String description = "Spring";
        event.setName(name);
        event.setDescription(description);

        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

    @Test
    public void testFree() {
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .build();

        event.update();

        assertThat(event.isFree()).isTrue();

        event = Event.builder()
                .basePrice(100)
                .maxPrice(0)
                .build();

        event.update();

        assertThat(event.isFree()).isFalse();

        event = Event.builder()
                .basePrice(0)
                .maxPrice(100)
                .build();

        event.update();

        assertThat(event.isFree()).isFalse();

    }

    @Test
    public void testOffline() {
        Event event = Event.builder()
                .location("seoul")
                .build();

        event.update();

        assertThat(event.isOffline()).isTrue();

        event = Event.builder()
                .location("")
                .build();

        event.update();

        assertThat(event.isOffline()).isFalse();
    }

}