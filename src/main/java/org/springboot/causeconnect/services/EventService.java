package org.springboot.causeconnect.services;

import org.springboot.causeconnect.entities.Event;
import org.springboot.causeconnect.entities.Volunteer;
import org.springboot.causeconnect.repository.EventRepository;
import org.springboot.causeconnect.utilities.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    @Qualifier("taskScheduler")
    private TaskScheduler taskScheduler;

    private final Map<Integer, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public int addEvent(Event event) {
        this.eventRepository.save(event);
        System.out.println("Scheduling event completion for: " + event.getName() + " at " + event.getEventDate());

        if (scheduledTasks.containsKey(event.getEvent_id())) {
            System.out.println("Event already scheduled: " + event.getName());
            return 0;
        }

        ScheduledFuture<?> future = taskScheduler.schedule(() -> {
            System.out.println("Executing scheduled task for event: " + event.getName());

            // Check if already completed
            if ("COMPLETED".equals(event.getStatus())) {
                System.out.println("Event already completed: " + event.getName());
                return;
            }

            // Mark event as completed
            event.setStatus("COMPLETED");
            Event e= eventRepository.findById(event.getEvent_id()).get();
            e.setStatus("COMPLETED");
            eventRepository.save(e);
            System.out.println("Event marked as COMPLETED: " + event.getName());

            // Cancel the scheduled task
            ScheduledFuture<?> scheduledTask = scheduledTasks.remove(event.getEvent_id());
            if (scheduledTask != null) {
                scheduledTask.cancel(false);
                System.out.println("Task canceled for event: " + event.getName());
            }

        }, triggerContext -> {
            Instant eventInstant = event.getEventDate().atZone(ZoneId.systemDefault()).toInstant();

            // If event date is in the past, do not schedule
            if (eventInstant.isBefore(Instant.now())) {
                System.out.println("Skipping scheduling, event already passed: " + event.getEventDate());
                return null;
            }

            return eventInstant;
        });

        // Store future reference to cancel later
        scheduledTasks.put(event.getEvent_id(), future);

        System.out.println("Scheduling event completion for: " + event.getName() + " at " + event.getEventDate());


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

    public List<Event> getAllUpcomingEvents() throws ApiException {
        List <Event> events= eventRepository.findByStatus("UPCOMING");
        events.forEach(event->{
            event.getHost().setPassword(null);
            event.getHost().setOwner(null);
            event.setVolunteerRequestList(null);
            event.setEventVolunteer(null);
        });
        return events;
    }

    public List<Event> getEventsByCity(String city) throws ApiException {
        return this.eventRepository.findNGOByCityContainingIgnoreCase(city);
    }
}
