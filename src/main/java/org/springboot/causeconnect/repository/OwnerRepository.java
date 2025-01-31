package org.springboot.causeconnect.repository;

import org.springboot.causeconnect.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {
}
