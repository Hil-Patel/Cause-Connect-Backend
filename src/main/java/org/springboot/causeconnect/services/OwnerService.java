package org.springboot.causeconnect.services;

import org.springboot.causeconnect.entities.Owner;
import org.springboot.causeconnect.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;

    public void registerOwner(Owner owner) {
        this.ownerRepository.save(owner);
    }
}
