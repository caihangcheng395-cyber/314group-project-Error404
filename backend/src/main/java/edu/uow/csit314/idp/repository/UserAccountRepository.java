package edu.uow.csit314.idp.repository;
import edu.uow.csit314.idp.entity.UserAccount;
import java.util.*;
import java.time.Instant;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> { Optional<UserAccount> findByEmailIgnoreCase(String email); boolean existsByEmailIgnoreCase(String email); Optional<UserAccount> findByProfileId(Long id); List<UserAccount> findAllByOrderByIdAsc(); }
