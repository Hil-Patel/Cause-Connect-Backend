package org.springboot.causeconnect.services;

import org.springboot.causeconnect.entities.EventVolunteer;
import org.springboot.causeconnect.repository.EventVolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventVolunteerService {
    @Autowired
    private EventVolunteerRepository eventVolunteerRepository;

    public EventVolunteer saveEventVolunteer(EventVolunteer eventVolunteer) {
        return this.eventVolunteerRepository.save(eventVolunteer);
    }
}
