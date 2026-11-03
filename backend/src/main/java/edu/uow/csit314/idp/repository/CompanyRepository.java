package edu.uow.csit314.idp.repository;
import edu.uow.csit314.idp.entity.Company;
import java.util.*;
import java.time.Instant;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface CompanyRepository extends JpaRepository<Company,Long> { List<Company> findByOwnerId(Long ownerId); boolean existsByNameIgnoreCase(String name); }
