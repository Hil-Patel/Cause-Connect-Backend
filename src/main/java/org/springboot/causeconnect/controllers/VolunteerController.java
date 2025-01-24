package org.springboot.causeconnect.controllers;

import org.springboot.causeconnect.entities.Volunteer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController{
        @PostMapping("/volunteerLogin")
    public ResponseEntity<String> registerUser(@RequestBody Volunteer volunteer) {
            System.out.println(volunteer);
            return ResponseEntity.ok().body("Hello World");
        }
}
