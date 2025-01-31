package org.springboot.causeconnect.services;

import org.springboot.causeconnect.DTO.EditVolunteerDto;
import org.springboot.causeconnect.entities.Volunteer;
import org.springboot.causeconnect.repository.VolunteerRepository;
import org.springboot.causeconnect.utilities.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerService {
    @Autowired
    private VolunteerRepository volunteerRepository;

    public Volunteer registerVolunteer(Volunteer volunteer) throws ApiException {
        if(volunteerRepository.existsByEmail(volunteer.getEmail())){
            throw new ApiException("Email already exists",400);
        }
        return this.volunteerRepository.save(volunteer);
    }

    public boolean loginVolunteer(Volunteer volunteer) throws ApiException {
        Optional<Volunteer> volunteer1 =  this.volunteerRepository.findByEmail(volunteer.getEmail());
        if(!volunteer1.isPresent()) {
            throw new ApiException("Volunteer not found...",404);
        }
        Volunteer volunteer2 = volunteer1.get();
        if(!volunteer2.getPassword().equals(volunteer.getPassword())){
            throw new ApiException("Wrong password...",406);
        }
        return true;
    }

    public Volunteer getVolunteerById(int id) throws ApiException {
        if(volunteerRepository.findById(id).isEmpty()) {
            throw new ApiException("Volunteer not found...",404);
        }
        Volunteer volunteer = volunteerRepository.findById(id).get();
        volunteer.setPassword(null);
        return volunteer;
    }

    public List<Volunteer> getAllVolunteers() throws ApiException {
        List<Volunteer> volunteers = volunteerRepository.findAll();
        return volunteers;
    }

    public Volunteer getVolunteerByEmail(String email) throws ApiException {
        if(volunteerRepository.findByEmail(email).isEmpty()) {
            throw new ApiException("Volunteer not found...",404);
        }
        Volunteer volunteer = volunteerRepository.findByEmail(email).get();
        volunteer.setPassword(null);
        return volunteer;
    }

    public Volunteer updateVolunteer(EditVolunteerDto editVolunteerDto) throws ApiException {
        boolean result = this.volunteerRepository.existsById(editVolunteerDto.getId());
        if (!result) {
            throw new ApiException("Volunteer not found...",404);
        }
        Volunteer volunteer = volunteerRepository.findById(editVolunteerDto.getId()).get();
        volunteer.setFullName(editVolunteerDto.getFullName());
        volunteer.setAge(editVolunteerDto.getAge());
        volunteer.setAddress(editVolunteerDto.getAddress());
        volunteer.setCity(editVolunteerDto.getCity());
        volunteer.setExperience(editVolunteerDto.getExperience());
        volunteer.setPassword(null);
        this.volunteerRepository.save(volunteer);
        return volunteer;
    }

    public Volunteer saveVolunteer(Volunteer volunteer) throws ApiException {
        return this.volunteerRepository.save(volunteer);
    }

    public boolean checkIfVolunteerExists(String email , String password) throws ApiException {
        boolean isPresent =this.volunteerRepository.findByEmailAndPassword(email,password).isPresent();
        if (!isPresent) {
            throw new ApiException("Volunteer not found...",404);
        }
        return true;
    }
}

