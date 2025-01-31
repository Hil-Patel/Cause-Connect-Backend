package org.springboot.causeconnect.repository;

import org.springboot.causeconnect.entities.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<Volunteer,Integer> {
    Optional<Volunteer> findByEmail(String username);

    Optional<Volunteer> findByFullName(String fullName);

    boolean existsByEmail(String email);

    Optional<Object> findByEmailAndPassword(String email, String password);


}
