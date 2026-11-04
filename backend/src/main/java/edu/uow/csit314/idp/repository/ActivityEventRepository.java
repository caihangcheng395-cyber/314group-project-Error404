package edu.uow.csit314.idp.repository;
import edu.uow.csit314.idp.entity.ActivityEvent;
import java.util.*;
import java.time.Instant;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface ActivityEventRepository extends JpaRepository<ActivityEvent,Long> { List<ActivityEvent> findByCreatedAtBetweenOrderByCreatedAtAsc(Instant from,Instant to); }
