package org.springboot.causeconnect.services;
import lombok.extern.slf4j.Slf4j;
import org.springboot.causeconnect.DTO.*;
import org.springboot.causeconnect.entities.Event;
import org.springboot.causeconnect.entities.FileSystem;
import org.springboot.causeconnect.entities.NGO;
import org.springboot.causeconnect.entities.Owner;
import org.springboot.causeconnect.repository.NGORepository;
import org.springboot.causeconnect.utilities.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NGOService {
    @Autowired
    private NGORepository ngoRepository;

    public void updateNGO(NGO ngo) {
        ngoRepository.save(ngo);
    }

    public void registerNGO(NGO ngo) throws ApiException {
        if (ngoRepository.existsByEmail(ngo.getEmail())) {
            throw new ApiException("NGO already exists on this Email",400);
        }
        this.ngoRepository.save(ngo);
    }

    public NGO loginNgo(LoginNgoDto loginNgoDto) throws ApiException {
        Optional<NGO> optionalNGO = this.ngoRepository.findByEmail(loginNgoDto.getEmail());
        if(optionalNGO.isEmpty()) {
            throw new ApiException("No NGO found", 404);
        }
        NGO loggedInNGO = optionalNGO.get();
        if(!loggedInNGO.getPassword().equals(loginNgoDto.getPassword())){
            throw new ApiException("Wrong password", 400);
        }
        if(!loggedInNGO.isApproved()){
            throw new ApiException("NGO is not approved, please wait for approval", 404);
        }
        return loggedInNGO;
    }

    public NGO getNGO(String ngoEmail) throws ApiException {
        Optional<NGO> optionalNGO= this.ngoRepository.findByEmail(ngoEmail);
        if(optionalNGO.isEmpty()) {
            throw new ApiException("NGO does not exist",400);
        }
        return optionalNGO.get();
    }

    public NGO ApproveNGO(int id) throws ApiException {
        Optional<NGO> optionalNGO = this.ngoRepository.findById(id);
        if(optionalNGO.isEmpty()) {
            throw new ApiException("NGO does not exist",404);
        }
        NGO approvedNGO = optionalNGO.get();
        approvedNGO.setApproved(true);
        this.ngoRepository.save(approvedNGO);
        return approvedNGO;
    }

    public NGO DisapproveNGO(int id) throws ApiException {
        Optional<NGO> optionalNGO = this.ngoRepository.findById(id);
        if(optionalNGO.isEmpty()) {
            throw new ApiException("NGO does not exist",404);
        }
        NGO disapprovedNGO = optionalNGO.get();
        this.ngoRepository.delete(disapprovedNGO);
        return disapprovedNGO;
    }

    public NGO checkNGO(String email,String password) throws ApiException {
        Optional<NGO> ngo = this.ngoRepository.findByEmailAndPassword(email,password);
        if(ngo.isEmpty()) {
            throw new ApiException("Unauthorised access", 400);
        }
        return ngo.get();
    }

    public NGOProfileDataToNgoDto geNGOByEmail(String email) throws ApiException {
        Optional<NGO> optionalNGO = this.ngoRepository.findByEmail(email);
        if(optionalNGO.isEmpty()) {
            throw new ApiException("NGO does not exist",404);
        }
        NGO getNGO = optionalNGO.get();
        return mapToNGOProfileDataToNgoDto(getNGO);
    }
    private NGOProfileDataToNgoDto mapToNGOProfileDataToNgoDto(NGO ngo){
        NGOProfileDataToNgoDto dto = new NGOProfileDataToNgoDto();

        dto.setId(ngo.getId());
        dto.setNgoName(ngo.getNgoName());
        dto.setNgoAim(ngo.getNgoAim());
        dto.setNgoDescription(ngo.getNgoDescription());
        dto.setEmail(ngo.getEmail());
        dto.setPhoneNumber(ngo.getPhoneNumber());
        dto.setAddress(ngo.getAddress());
        dto.setNumberOfMember(ngo.getNumberOfMember());
        dto.setCity(ngo.getCity());
        dto.setAccountNumber(ngo.getAccountNumber());
        dto.setPassword(ngo.getPassword());
        dto.setOwner(ngo.getOwner());

        List<Event>  completedEvents = new ArrayList<>();
        List<Event> pendingFutureEvents= new ArrayList<>();

        ngo.getEvents().forEach(event -> {
            if (event.getStatus().equals("UPCOMING")) {
                pendingFutureEvents.add(event);
            }
            else {
                completedEvents.add(event);
            }
            event.setHost(null);
        });

        dto.setCompletedEvents(completedEvents);
        dto.setPendingFutureEvents(pendingFutureEvents);

        FileSystem fileSystem = ngo.getFileSystem();
        if (fileSystem != null) {
            FileSystemDto fileSystemDto = new FileSystemDto();
            int ngoId = ngo.getId();

            fileSystemDto.setBankStatementUrl("/api/v1/files/" + ngoId + "/bankStatement");
            fileSystemDto.setTranscriptUrl("/api/v1/files/" + ngoId + "/transcript");
            dto.setProfilePicUrl("/api/v1/files/" + ngoId + "/profilePic");

            dto.setFileSystemDto(fileSystemDto);
        }

        return dto;
    }

    public List<NGO> getAllNGOsByCity(String city) throws ApiException {
        List<NGO> ngos =this.ngoRepository.findNGOByCityContainingIgnoreCaseAndIsApproved(city,true);
        if(ngos.isEmpty()) {
            throw new ApiException("NGO does not exist in this city",404);
        }
        for (NGO ngo : ngos) {
            ngo.setOwner(null);
            ngo.getFileSystem().setBankStatement(null);
            ngo.getFileSystem().setTranscript(null);
            ngo.setPassword(null);
        }
        return ngos;
    }

    public List<NGO> getAllNgoForUser() {
        List<NGO> ngos=this.ngoRepository.findNGOByisApprovedTrue();
        for (NGO ngo : ngos) {
            ngo.getFileSystem().setBankStatement(null);
            ngo.getFileSystem().setTranscript(null);
            ngo.setOwner(null);
        }
        return ngos;
    }

    public List<NGOUnApprovedDetailsDTO> getAllNgoUnapproved() {
        List<NGO> unapprovedNgos = this.ngoRepository.findNGOByisApprovedFalse();
        return unapprovedNgos.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    private NGOUnApprovedDetailsDTO mapToDTO(NGO ngo) {
        NGOUnApprovedDetailsDTO dto = new NGOUnApprovedDetailsDTO();

        dto.setId(ngo.getId());
        dto.setNgoName(ngo.getNgoName());
        dto.setNgoAim(ngo.getNgoAim());
        dto.setNgoDescription(ngo.getNgoDescription());
        dto.setEmail(ngo.getEmail());
        dto.setPhoneNumber(ngo.getPhoneNumber());
        dto.setAddress(ngo.getAddress());
        dto.setNumberOfMember(ngo.getNumberOfMember());
        dto.setCity(ngo.getCity());
        dto.setAccountNumber(ngo.getAccountNumber());
        dto.setOwner(ngo.getOwner());

        // Convert FileSystem to FileSystemDto
        FileSystem fileSystem = ngo.getFileSystem();
        if (fileSystem != null) {
            FileSystemDto fileSystemDto = new FileSystemDto();
            int ngoId = ngo.getId();

            fileSystemDto.setBankStatementUrl("/api/v1/files/" + ngoId + "/bankStatement");
            fileSystemDto.setTranscriptUrl("/api/v1/files/" + ngoId + "/transcript");
            dto.setProfilePicUrl("/api/v1/files/" + ngoId + "/profilePic");

            dto.setFileSystemDto(fileSystemDto);
        }

        return dto;
    }

    public List<AdminFetchApprovedNgoDto> getAllNgoApproved() {
        List<NGO> approvedNGO= this.ngoRepository.findNGOByisApprovedTrue();
        return approvedNGO.stream().map(this::mapApprovedNgoToDto).collect(Collectors.toList());
    }
    private AdminFetchApprovedNgoDto mapApprovedNgoToDto(NGO ngo) {
        AdminFetchApprovedNgoDto dto = new AdminFetchApprovedNgoDto();

        dto.setId(ngo.getId());
        dto.setNgoName(ngo.getNgoName());
        dto.setNgoAim(ngo.getNgoAim());
        dto.setNgoDescription(ngo.getNgoDescription());
        dto.setEmail(ngo.getEmail());
        dto.setPhoneNumber(ngo.getPhoneNumber());
        dto.setAddress(ngo.getAddress());
        dto.setNumberOfMember(ngo.getNumberOfMember());
        dto.setCity(ngo.getCity());
        dto.setAccountNumber(ngo.getAccountNumber());
        dto.setOwner(ngo.getOwner());
        List<Event>  completedEvents = new ArrayList<>();
        List<Event> pendingFutureEvents= new ArrayList<>();

        ngo.getEvents().forEach(event -> {
            if (event.getStatus().equals("UPCOMING")) {
                completedEvents.add(event);
            }
            else {
                pendingFutureEvents.add(event);
            }
        });

        dto.setCompletedEvents(completedEvents);
        dto.setPendingFutureEvents(pendingFutureEvents);


        FileSystem fileSystem = ngo.getFileSystem();
        if (fileSystem != null) {
            FileSystemDto fileSystemDto = new FileSystemDto();
            int ngoId = ngo.getId();

            fileSystemDto.setBankStatementUrl("/api/v1/files/" + ngoId + "/bankStatement");
            fileSystemDto.setTranscriptUrl("/api/v1/files/" + ngoId + "/transcript");
            dto.setProfilePic("/api/v1/files/" + ngoId + "/profilePic");

            dto.setFileSystemDto(fileSystemDto);
        }

        return dto;
    }
}
