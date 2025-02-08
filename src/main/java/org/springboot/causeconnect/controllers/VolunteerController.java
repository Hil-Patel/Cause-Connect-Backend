package org.springboot.causeconnect.controllers;

import org.springboot.causeconnect.DTO.*;
import org.springboot.causeconnect.entities.Event;
import org.springboot.causeconnect.entities.Volunteer;
import org.springboot.causeconnect.services.EmailService;
import org.springboot.causeconnect.services.EventService;
import org.springboot.causeconnect.services.VolunteerService;
import org.springboot.causeconnect.utilities.ApiException;
import org.springboot.causeconnect.utilities.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Volunteer")
@CrossOrigin(origins = "*")
public class VolunteerController {
    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private EventService eventService;

    @Autowired
    private EmailService emailService;

        @PostMapping("/SendOtp")
        public ResponseEntity<ApiResponse> sendOtp(@RequestBody OTPDto otpDto) throws ApiException {
            try {
                int OtpId=this.emailService.generateAndSendVolunteerOtp(otpDto.getEmail());
                return ResponseEntity.status(200).body(new ApiResponse(200,OtpId,"Email sent successfully"));
            }catch (ApiException e){
                return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
            }
        }

        @PostMapping("/VerifyOtp")
        public ResponseEntity<ApiResponse> verifyOtp(@RequestBody VerifyOtpDto verifyOtpDto) throws ApiException {
            try {
                boolean isVerified=this.emailService.verifyEmail(verifyOtpDto.getId(),verifyOtpDto.getOtp());
                return ResponseEntity.status(200).body(new ApiResponse(200,isVerified,"Email Verified successfully"));

            }catch (ApiException e){
                return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
            }
        }

        @PostMapping("/registerVolunteer")
        public ResponseEntity<ApiResponse> registerVolunteer(@RequestBody Volunteer volunteer){
            try{
                Volunteer registeredVolunteer =  volunteerService.registerVolunteer(volunteer);
                return ResponseEntity.ok().body(new ApiResponse(201,true,"Volunteer registered successfully"));
            }catch (ApiException e){
                    return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(),false,e.getMessage()));
            }
        }

        @PostMapping("/loginVolunteer")
        public ResponseEntity<ApiResponse> loginVolunteer(@RequestBody Volunteer volunteer){
            try{
                Boolean isLoggedVolunteer = this.volunteerService.loginVolunteer(volunteer);
                return ResponseEntity.status(200).body(new ApiResponse(200,isLoggedVolunteer,"Volunteer loggedIn Successfully..."));
            }catch (ApiException e){
                return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(),false,e.getMessage()));
            }
        }

        @GetMapping("/profile/{id}")
        public ResponseEntity<ApiResponse> getVolunteer(@PathVariable int id) {
            try{
                Volunteer volunteer  = this.volunteerService.getVolunteerById(id);
                return ResponseEntity.status(200).body(new ApiResponse(200,volunteer,"Volunteer found..."));
            } catch (ApiException e) {
                return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(),null,e.getMessage()));
            }
        }

        @PatchMapping("/profile")
        public ResponseEntity<ApiResponse> updateVolunteer( @RequestBody EditVolunteerDto editVolunteerDto){
            try {
                System.out.println(editVolunteerDto);
                Volunteer updated = this.volunteerService.updateVolunteer(editVolunteerDto);
                return ResponseEntity.status(200).body(new ApiResponse(200,updated,"Volunteer updated successfully"));
            }catch (ApiException e){
                return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(),false,e.getMessage()));
            }
        }

        @GetMapping("")
        public ResponseEntity<ApiResponse> checkVolunteer(@RequestHeader("email") String email,@RequestHeader("password")String password) {
            try{
                boolean isPresent=this.volunteerService.checkIfVolunteerExists(email,password);
                return ResponseEntity.status(200).body(new ApiResponse(200, isPresent, "Volunteer Access successful"));
            }catch (ApiException e){
                return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(),null, "unauthorised access"));
            }
        }

        @GetMapping("/Events")
        public ResponseEntity<ApiResponse> getAllEvent() throws ApiException {
            try {
            List<Event> allEvent=this.eventService.getAllEvents();
            return ResponseEntity.status(200).body(new ApiResponse(200,allEvent,"Events Fetched successfully"));
            }catch (ApiException e){
                return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(),null,e.getMessage()));
            }
        }

        @GetMapping("/Event/{city}")
        public ResponseEntity<ApiResponse> getAllEvent(@PathVariable String city) throws ApiException {
            try {
                List<Event> events=this.eventService.getEventsByCity(city);
                return ResponseEntity.status(200).body(new ApiResponse(200,events,"Events Fetched successfully"));
            }catch (ApiException e){
                return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(),null,e.getMessage()));
            }
        }

    @PostMapping("/JoinRequest")
    public ResponseEntity<ApiResponse> requestToJoinEvent(@RequestBody JoinEventRequestDto joinEventRequestDto, @RequestHeader("VolunteerEmail") String email, @RequestHeader("VolunteerPassword")String password) throws ApiException {
        try {
            Event event=this.eventService.getEvent(joinEventRequestDto.getEventId());
            Volunteer volunteer=this.volunteerService.getVolunteerByEmail(email);
            event.getVolunteerRequestList().add(volunteer);
            volunteer.getEventsRequestList().add(event);
            return ResponseEntity.status(200).body(new ApiResponse(200,true,"Request sent successfully"));
        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(),null,e.getMessage()));
        }
    }

    @GetMapping("/UpcomingEvent/{id}")
    public ResponseEntity<ApiResponse> getUpcomingEvent(@PathVariable int id) throws ApiException {
            try {
                Volunteer volunteer = this.volunteerService.getVolunteerById(id);
                return ResponseEntity.status(200).body(new ApiResponse(200,volunteer.getUpcomingEvents(),"Upcoming events fetched successfully"));

            }catch (ApiException e){
                return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(),null,e.getMessage()));
            }
    }

    @GetMapping("/All")
    public ResponseEntity<ApiResponse> getAllVolunteer() throws ApiException {
            try {
                List<VolunteerDetailsDto> volunteerList=this.volunteerService.getAllVolunteers();
                return ResponseEntity.status(200).body(new ApiResponse(200,volunteerList,"Volunteers fetched successfully"));
            }catch (ApiException e){
                return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(),null,e.getMessage()));
            }
    }


}
