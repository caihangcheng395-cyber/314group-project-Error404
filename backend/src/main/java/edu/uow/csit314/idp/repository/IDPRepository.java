package edu.uow.csit314.idp.repository;
import edu.uow.csit314.idp.entity.IDP;
import java.util.*;
import java.time.Instant;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface IDPRepository extends JpaRepository<IDP,Long> { List<IDP> findByOwnerId(Long ownerId); boolean existsByCompanyId(Long id); boolean existsByCategoryId(Long id); List<IDP> findAllByOrderByCreatedAtDesc(); }
