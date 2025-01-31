package org.springboot.causeconnect.repository;

import org.springboot.causeconnect.entities.NGO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NGORepository extends JpaRepository<NGO, Integer> {
    Optional<NGO> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<NGO> findByEmailAndPassword(String email, String password);
    List<NGO> findNGOByisApprovedTrue();
    List<NGO> findNGOByisApprovedFalse();
    List<NGO> findNGOByCityContainingIgnoreCaseAndIsApproved(String city,boolean isApproved);



}
