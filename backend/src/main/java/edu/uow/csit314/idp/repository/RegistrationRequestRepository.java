package edu.uow.csit314.idp.repository;
import edu.uow.csit314.idp.entity.RegistrationRequest;
import java.util.*;
import java.time.Instant;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest,Long> { Optional<RegistrationRequest> findByEmailIgnoreCase(String email); List<RegistrationRequest> findAllByOrderByCreatedAtDesc(); List<RegistrationRequest> findByRequesterTokenHashOrderByCreatedAtDesc(String hash); @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select r from RegistrationRequest r where r.id=:id") Optional<RegistrationRequest> lockById(@Param("id") Long id); }
