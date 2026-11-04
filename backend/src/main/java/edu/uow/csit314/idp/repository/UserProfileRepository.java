package edu.uow.csit314.idp.repository;
import edu.uow.csit314.idp.entity.UserProfile;
import java.util.*;
import java.time.Instant;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> { List<UserProfile> findAllByOrderByIdDesc(); }
