package org.springboot.causeconnect.repository;

import org.springboot.causeconnect.entities.EventVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventVolunteerRepository extends JpaRepository<EventVolunteer, Integer> {
}
