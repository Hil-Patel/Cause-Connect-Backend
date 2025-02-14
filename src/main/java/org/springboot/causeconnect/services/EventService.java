package org.springboot.causeconnect.services;

import org.springboot.causeconnect.entities.Event;
import org.springboot.causeconnect.entities.EventVolunteer;
import org.springboot.causeconnect.entities.Volunteer;
import org.springboot.causeconnect.repository.EventRepository;
import org.springboot.causeconnect.utilities.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    @Qualifier("taskScheduler")
    private TaskScheduler taskScheduler;

    public int addEvent(Event event) {
        System.out.println(event.getEventDate());
        System.out.println(event.getLastDateToRegister());
        this.eventRepository.save(event);
//        taskScheduler.schedule(()->{
//            this.eventRepository.delete(event);
//        },triggerContext -> {
//            return event.getLastDateToRegister().toInstant();
//        });
        return event.getEvent_id();
    }

    public int updateEvent(Event event) {
        eventRepository.save(event);
        return event.getEvent_id();
    }

    public Event getEvent(int id) throws ApiException {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            return event.get();
        }
        throw new ApiException("Event does not exist",404);
    }

    public Event deleteEvent(int id) throws ApiException {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            eventRepository.delete(event.get());
            return event.get();
        }
        throw new ApiException("Event does not exist",404);
    }

    public List<Volunteer> getVolunteersRequestList(int id) throws ApiException {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new ApiException("Event does not exist",404);
        }
        Event fetchedEvent = event.get();
        return fetchedEvent.getVolunteerRequestList();
    }

    public List<Event> getAllEvents() throws ApiException {
        return eventRepository.findAll();
    }

    public List<Event> getEventsByCity(String city) throws ApiException {
        return this.eventRepository.findNGOByCityContainingIgnoreCase(city);
    }
}
