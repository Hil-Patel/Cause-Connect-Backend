package org.springboot.causeconnect.controllers;

import org.springboot.causeconnect.DTO.AdminDto;
import org.springboot.causeconnect.DTO.AdminFetchApprovedNgoDto;
import org.springboot.causeconnect.DTO.NGOUnApprovedDetailsDTO;
import org.springboot.causeconnect.entities.NGO;
import org.springboot.causeconnect.services.EmailService;
import org.springboot.causeconnect.services.NGOService;
import org.springboot.causeconnect.utilities.ApiException;
import org.springboot.causeconnect.utilities.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/Admin")
public class AdminController {

    @Autowired
    private NGOService ngoService;

    @Autowired
    private EmailService emailService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> index(@RequestBody AdminDto adminDto) {
        if (adminDto.getEmail().equals("causeconnect12@gmail.com")){
            if (adminDto.getPassword().equals("HilDp@1234")){
                return ResponseEntity.status(200).body(new ApiResponse(200,true,"Login successful"));
            }
            else {
                return ResponseEntity.status(401).body(new ApiResponse(401,false,"Invalid Password"));
            }
        }
        else {
            return ResponseEntity.status(401).body(new ApiResponse(401,false,"Invalid Email"));
        }
    }

    @GetMapping("/UnapprovedNgo")
    public ResponseEntity<ApiResponse> UnapprovedNgo() {
        List<NGOUnApprovedDetailsDTO> ngos=this.ngoService.getAllNgoUnapproved();
        return ResponseEntity.status(200).body(new ApiResponse(200,ngos,"UnapprovedNgo fetch successfully"));
    }

    @GetMapping("/ApprovedNgo")
    public ResponseEntity<ApiResponse> ApprovedNgo() {
        List<AdminFetchApprovedNgoDto> ngos=this.ngoService.getAllNgoApproved();
        return ResponseEntity.status(200).body(new ApiResponse(200,ngos,"ApprovedNgo fetch successfully"));
    }

    @GetMapping("/Disapprove/Ngo/{id}")
    public ResponseEntity<ApiResponse> DisapproveNgo(@PathVariable int id) {
        try {
            NGO ngo=this.ngoService.DisapproveNGO(id);
            this.emailService.sendNgoDisapprovalEmail(ngo.getEmail(),ngo.getOwner().getFullName());
            return ResponseEntity.status(200).body(new ApiResponse(200,null,"Disapproved Ngo Deleted successfully"));

        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(),null,e.getMessage()));
        }
    }

    @GetMapping("/Approve/Ngo/{id}")
    public ResponseEntity<ApiResponse> ApproveNgo(@PathVariable int id) throws ApiException {
        try {
            System.out.println(id);
            NGO ngo=this.ngoService.ApproveNGO(id);
            this.emailService.sendNgoApprovalEmail(ngo.getEmail(),ngo.getOwner().getFullName());
            return ResponseEntity.status(200).body(new ApiResponse(200,null,"Ngo Approved successfully"));

        }catch (ApiException e){
            return ResponseEntity.status(e.getStatusCode()).body(new ApiResponse(e.getStatusCode(),null,e.getMessage()));
        }

    }
}
