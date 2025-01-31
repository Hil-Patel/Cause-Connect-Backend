package org.springboot.causeconnect.services;
import lombok.extern.slf4j.Slf4j;
import org.springboot.causeconnect.DTO.LoginNgoDto;
import org.springboot.causeconnect.entities.NGO;
import org.springboot.causeconnect.repository.NGORepository;
import org.springboot.causeconnect.utilities.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NGOService {
    @Autowired
    private NGORepository ngoRepository;

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

    public NGO geNGOById(int id) throws ApiException {
        Optional<NGO> optionalNGO = this.ngoRepository.findById(id);
        if(optionalNGO.isEmpty()) {
            throw new ApiException("NGO does not exist",404);
        }
        return optionalNGO.get();
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

    public List<NGO> getAllNgoUnapproved() {
        return this.ngoRepository.findNGOByisApprovedFalse();
    }

    public List<NGO> getAllNgoApproved() {
        return this.ngoRepository.findNGOByisApprovedTrue();
    }
}
