package org.springboot.causeconnect.controllers;

import org.springboot.causeconnect.DTO.EventTaskAssignDto;
import org.springboot.causeconnect.DTO.JoinEventRequestDto;
import org.springboot.causeconnect.entities.Event;
import org.springboot.causeconnect.entities.EventVolunteer;
import org.springboot.causeconnect.entities.NGO;
import org.springboot.causeconnect.entities.Volunteer;
import org.springboot.causeconnect.services.*;
import org.springboot.causeconnect.utilities.ApiException;
import org.springboot.causeconnect.utilities.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class EventManagementController {

    @Autowired
    private NGOService ngoService;

    @Autowired
    private EventService eventService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private EventVolunteerService eventVolunteerService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/Event/{id}")
    public ResponseEntity<ApiResponse> getEvent(@PathVariable int id) throws ApiException {
        try {
            Event event=this.eventService.getEvent(id);
            return ResponseEntity.status(200).body(new ApiResponse(200,event,"Event Fetched"));
        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
        }
    }


    @PostMapping("/Ngo/DeclineRequest")
    public ResponseEntity<ApiResponse> declineEvent(@RequestHeader("email") String email, @RequestHeader("password") String password, @RequestBody EventTaskAssignDto eventTaskAssignDto) throws ApiException {
        try {
            NGO ngo=this.ngoService.checkNGO(email,password);
            Event event=this.eventService.getEvent(eventTaskAssignDto.getEventId());
            if(event.getHost()!=ngo){
                throw new ApiException("Unauthorized access",401);
            }
            Volunteer volunteer=this.volunteerService.getVolunteerByEmail(eventTaskAssignDto.getVolunteerEmail());

            volunteer.getEventsRequestList().remove(event);
            event.getVolunteerRequestList().remove(volunteer);


            Volunteer updatedVolunteer=this.volunteerService.saveVolunteer(volunteer);
            int eventId=this.eventService.updateEvent(event);

            String body=this.emailService.createVolunteerDeclinedEmailBody(volunteer.getFullName(),ngo.getNgoName(),event.getName(),eventTaskAssignDto.getTask());
            String subject = "Your Participation Request for " + event.getName() + " Has Been Declined" ;
            this.emailService.sendEmail(volunteer.getEmail(), body, subject);

            return ResponseEntity.status(200).body(new ApiResponse(200,event,"Request Declined"));
        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
        }
    }

    @PostMapping("/Ngo/AssignTask")
    public ResponseEntity<ApiResponse> getAssignTask(@RequestHeader("email") String email, @RequestHeader("password") String password, @RequestBody EventTaskAssignDto eventTaskAssignDto){
        try {
            NGO ngo=this.ngoService.checkNGO(email,password);
            Event event=this.eventService.getEvent(eventTaskAssignDto.getEventId());
            if(event.getHost()!=ngo){
                throw new ApiException("Unauthorized access",401);
            }
            Volunteer volunteer=this.volunteerService.getVolunteerByEmail(eventTaskAssignDto.getVolunteerEmail());

            EventVolunteer eventVolunteer=new EventVolunteer();
            eventVolunteer.setTask(eventTaskAssignDto.getTask());


            event.getVolunteerRequestList().remove(volunteer);
            event.getEventVolunteer().add(eventVolunteer);
            volunteer.getEventsRequestList().remove(event);
            volunteer.getEvents().add(eventVolunteer);

            eventVolunteer.setEvent(event);
            eventVolunteer.setVolunteer(volunteer);

            Volunteer updatedVolunteer=this.volunteerService.saveVolunteer(volunteer);
            int eventId=this.eventService.updateEvent(event);

            String body=this.emailService.createVolunteerAcceptedEmailBody(volunteer.getFullName(), ngo.getNgoName(), event.getName() ,event.getEventDate(),event.getAddress(),eventVolunteer.getTask());
            String subject = "Confirmation of Your Participation in the Event: " + event.getName();
            this.emailService.sendEmail(volunteer.getEmail(), body, subject);

            return ResponseEntity.status(200).body(new ApiResponse(200,event,"Task assigned"));

        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
        }
    }


}
