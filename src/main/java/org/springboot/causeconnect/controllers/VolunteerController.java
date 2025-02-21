package org.springboot.causeconnect.controllers;

import org.modelmapper.ModelMapper;
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

import java.util.ArrayList;
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

    @Autowired
    ModelMapper modelMapper;

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
        public ResponseEntity<ApiResponse> registerVolunteer(@RequestBody RegisterVolunteerDto volunteer){
            try{
                Volunteer v=this.modelMapper.map(volunteer,Volunteer.class);
                System.out.println(v.getEmail());
                Volunteer registeredVolunteer =  volunteerService.registerVolunteer(v);
                return ResponseEntity.ok().body(new ApiResponse(201,true,"Volunteer registered successfully"));
            }catch (ApiException e){
                    return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(),false,e.getMessage()));
            }
        }

        @PostMapping("/loginVolunteer")
        public ResponseEntity<ApiResponse> loginVolunteer(@RequestBody LoginNgoDto volunteer){
            try{
                System.out.println(volunteer.getPassword());
                Boolean isLoggedVolunteer = this.volunteerService.loginVolunteer(volunteer);
                System.out.println(isLoggedVolunteer);
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
                if (!isPresent){
                    throw new ApiException("Volunteer not found",404);
                }
                Volunteer volunteer=this.volunteerService.getVolunteerByEmail(email);
                return ResponseEntity.status(200).body(new ApiResponse(200, volunteer.getId(), "Volunteer Access successful"));
            }catch (ApiException e){
                return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(),null, "unauthorised access"));
            }
        }

        @GetMapping("/Events")
        public ResponseEntity<ApiResponse> getAllEvent(@RequestHeader("email") String email,@RequestHeader("password")String password) throws ApiException {
            try {
                List<Event> allEvent=this.eventService.getAllUpcomingEvents();
                Volunteer volunteer=this.volunteerService.getVolunteerByEmail(email);
                List<Event> response = new ArrayList<>();
                allEvent.forEach(event->{
                    if (!volunteer.getEventsRequestList().contains(event)){
                        response.add(event);
                    }
                });
                return ResponseEntity.status(200).body(new ApiResponse(200,response,"Events Fetched successfully"));
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
    public ResponseEntity<ApiResponse> requestToJoinEvent(@RequestHeader("email") String email, @RequestHeader("password") String password,@RequestBody JoinEventRequestDto joinEventRequestDto) throws ApiException {
        try {
            Event event=this.eventService.getEvent(joinEventRequestDto.getEventId());
            Volunteer volunteer=this.volunteerService.getVolunteerByEmail(email);
            event.getVolunteerRequestList().add(volunteer);
            int id=this.eventService.updateEvent(event);
            return ResponseEntity.status(200).body(new ApiResponse(200,true,"Request sent successfully"));
        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(),null,e.getMessage()));
        }
    }

    @GetMapping("/UpcomingEvent/{id}")
    public ResponseEntity<ApiResponse> getUpcomingEvent(@PathVariable int id) throws ApiException {
            try {
                Volunteer volunteer = this.volunteerService.getVolunteerById(id);
                return ResponseEntity.status(200).body(new ApiResponse(200,volunteer.getEvents(),"Upcoming events fetched successfully"));

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
