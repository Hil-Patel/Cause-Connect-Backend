package org.springboot.causeconnect.repository;

import org.springboot.causeconnect.entities.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Volunteer,Integer> {
    Volunteer findByEmail(String username);
}
