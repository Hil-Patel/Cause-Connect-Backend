package org.springboot.causeconnect.controllers;

import org.modelmapper.ModelMapper;
import org.springboot.causeconnect.DTO.*;
import org.springboot.causeconnect.entities.*;
import org.springboot.causeconnect.services.*;
import org.springboot.causeconnect.utilities.ApiException;
import org.springboot.causeconnect.utilities.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/Ngo")
public class NGOController {
    @Autowired
    FileService fileService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EventService eventService;

    @Autowired
    NGOService ngoService;

    @Autowired
    EmailService emailService;

    @Autowired
    OwnerService ownerService;

    @PostMapping("/SendOtp")
    public ResponseEntity<ApiResponse> sendOtp(@RequestBody OTPDto otpDto) throws ApiException {
        try {
            System.out.println(otpDto);
            int OtpId=this.emailService.generateAndSendNgoOtp(otpDto.getEmail());
            return ResponseEntity.status(200).body(new ApiResponse(200,OtpId,"Email sent successfully"));
        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
        }
    }

    @PostMapping("/VerifyOtp")
    public ResponseEntity<ApiResponse> verifyOtp(@RequestBody VerifyOtpDto verifyOtpDto) throws ApiException {
        try {
            boolean isVerified=this.emailService.verifyEmail(verifyOtpDto.getId(),verifyOtpDto.getOtp());
            return ResponseEntity.status(200).body(new ApiResponse(200, isVerified,"Email Verified successfully"));
        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
        }
    }

    @PostMapping(value = "/registerNgo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity<ApiResponse> registerNgo(RegisterNgoDTO registerNgoDTO) {
        try{
            NGO ngo     = this.modelMapper.map(registerNgoDTO, NGO.class);
            ngo.setApproved(false);

            Owner owner = this.modelMapper.map(registerNgoDTO, Owner.class);
            ngo.setOwner(owner);
            FileSystem fileSystem = this.fileService.saveFiles(
                    registerNgoDTO.getBankStatement(),
                    registerNgoDTO.getTranscript(),
                    registerNgoDTO.getProfilePic()
            );
            ngo.setFileSystem(fileSystem);
            this.ngoService.registerNGO(ngo);
            this.ownerService.registerOwner(owner);
            return ResponseEntity.status(201).body(new ApiResponse(200, true, "Request for NGO registered sent"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse(400, null, e.getMessage()));
        }
    }


    @PostMapping("/loginNgo")
    public ResponseEntity<ApiResponse> loginNGO(@RequestBody LoginNgoDto loginNgoDto){
        try{
            NGO ngo = this.ngoService.loginNgo(loginNgoDto);
            return ResponseEntity.status(200).body(new ApiResponse(200, ngo, "NGO Login successful"));
        }catch(ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
        }
    }

    //for ngo dashboard
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> profile( @RequestHeader("email") String email, @RequestHeader("password") String password){
        try {
            NGOProfileDataToNgoDto ngo=this.ngoService.geNGOByEmail(email);
            if (ngo.getEmail().equals(email) && ngo.getPassword().equals(password)){
                ngo.setPassword(null);
            }
            else {
                ngo.setOwner(null);
                ngo.getFileSystemDto().setTranscriptUrl("");
                ngo.getFileSystemDto().setBankStatementUrl("");
                ngo.setPassword(null);
            }
            return ResponseEntity.status(200).body(new ApiResponse(200, ngo, "NGO profile Fetch successful"));

        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
        }
    }

    // for profile page fetch by admin
    @GetMapping("/access/{NgoEmail}")
    public ResponseEntity<ApiResponse> getNgo(@PathVariable String NgoEmail){
        try {
            NGO ngo=this.ngoService.getNGO(NgoEmail);
            return ResponseEntity.status(200).body(new ApiResponse(200, ngo, "NGO access successful"));
        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
        }
    }

    //for protected routes
    @GetMapping("")
    public ResponseEntity<ApiResponse> CheckNgoAccess(@RequestHeader("email") String email, @RequestHeader("password") String password){
        try {
            NGO ngo=this.ngoService.checkNGO(email,password);
            return ResponseEntity.status(200).body(new ApiResponse(200, ngo.getId(), "NGO Access successful"));
        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
        }
    }

    //used by volunteer to search ngo
    @GetMapping("/All")
    public ResponseEntity<ApiResponse> getAllNgoForUser(){
        List<NGO> ngos=this.ngoService.getAllNgoForUser();
        return ResponseEntity.status(200).body(new ApiResponse(200, ngos, "All NGO Fetched successful"));
    }

    @GetMapping("/{city}")
    public ResponseEntity<ApiResponse> getNgoByCity(@PathVariable String city) throws ApiException {
        try {
            List<NGO> ngos=this.ngoService.getAllNGOsByCity(city);
            return ResponseEntity.status(200).body(new ApiResponse(200, ngos, "NGO Fetched successful"));
        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
        }
    }


    @PostMapping("/CreateEvent")
    public ResponseEntity<ApiResponse> CreateEvent(@RequestHeader("email") String email, @RequestHeader("password") String password,@RequestBody CreateEventDto createEventDto) throws ApiException {
        try {
            Event event=this.modelMapper.map(createEventDto, Event.class);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            if (createEventDto.getLastDateToRegister() != null && !createEventDto.getLastDateToRegister().isEmpty()) {
                event.setLastDateToRegister(LocalDateTime.parse(createEventDto.getLastDateToRegister(), formatter));
            }
            if (createEventDto.getEventDate() != null && !createEventDto.getEventDate().isEmpty()) {
                event.setEventDate(LocalDateTime.parse(createEventDto.getEventDate(), formatter));
            }
            NGO ngo=this.ngoService.getNGO(email);
            if (!ngo.getEmail().equals(email) && !ngo.getPassword().equals(password)){
                throw new ApiException("Unauthorized access", 401);
            }
            event.setHost(ngo);
            event.setStatus("UPCOMING");
            int id=this.eventService.addEvent(event);
            return ResponseEntity.status(200).body(new ApiResponse(200, id, "Event created successfully"));
        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
        }
    }

    //manually complete an event
//    @GetMapping("/EventCompleted/{id}")
//    public ResponseEntity<ApiResponse> getEventCompleted(@PathVariable int id,@RequestHeader("NgoEmail") String email, @RequestHeader("NgoPassword") String password){
//        try {
//            NGO ngo=this.ngoService.checkNGO(email,password);
//            Event event=this.eventService.getEvent(id);
//            if (ngo != event.getHost()){
//                throw new ApiException("Unauthorised access",401);
//            }
//            event.setStatus("COMPLETED");
//            int eventId=this.eventService.updateEvent(event);
//            return ResponseEntity.status(200).body(new ApiResponse(200, eventId, "Event completed successfully"));
//        }catch (ApiException e){
//            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
//        }
//    }

    @GetMapping("/UpcomingEvent/{id}")
    public ResponseEntity<ApiResponse> getUpcomingEvents(@PathVariable int id ,@RequestHeader("email") String email, @RequestHeader("password") String password){
        try {
            NGO ngo=this.ngoService.getNGO(email);
            Event event=null;
            List<Event> e=ngo.getEvents();
            for (Event value : e) {
                if (value.getEvent_id() == id) {
                    event = value;
                }
            }
            if (event==null){
                throw new ApiException("Event not found", 404);
            }
            event.setHost(null);
//            if(event.getHost()!=ngo){
//                event.getHost().setOwner(null);
//                event.setVolunteerRequestList(null);
//                event.setEventVolunteer(null);
//            }
            return ResponseEntity.status(200).body(new ApiResponse(200, event, "Event Fetched successful"));
        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
        }
    }

//    @GetMapping("/CompletedEvent")
//    public ResponseEntity<ApiResponse> getPastEvents(@RequestHeader("NgoEmail") String email, @RequestHeader("NgoPassword") String password){
//        try {
//            NGO ngo=this.ngoService.getNGO(email);
//            return ResponseEntity.status(200).body(new ApiResponse(200,null, "NGO Fetched successful"));
//        }catch (ApiException e){
//            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
//        }
//    }

    @DeleteMapping("/Event")
    public ResponseEntity<ApiResponse> deleteEvent(@RequestBody DeleteEvent deleteEvent){
        try {
            Event event=this.eventService.deleteEvent(deleteEvent.getId());
            this.emailService.sendEventCancellationNotification(event, deleteEvent.getReason());
            return ResponseEntity.status(200).body(new ApiResponse(200, event.getEvent_id(), "Event deleted successfully"));
        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
        }
    }

    @GetMapping("/EventVolunteerRequest/{id}")
    public ResponseEntity<ApiResponse> getEventVolunteerRequest(@PathVariable int id){
        try {
            List<Volunteer> volunteerList=this.eventService.getVolunteersRequestList(id);
            return ResponseEntity.status(200).body(new ApiResponse(200, volunteerList, "Volunteer Request successful"));
        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(), null, e.getMessage()));
        }
    }



}