package edu.uow.csit314.idp.repository;
import edu.uow.csit314.idp.entity.Favourite;
import java.util.*;
import java.time.Instant;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface FavouriteRepository extends JpaRepository<Favourite,Long> { Optional<Favourite> findByCustomerIdAndProjectId(Long customer,Long project); List<Favourite> findByCustomerId(Long id); long countByProjectId(Long id); void deleteByProjectId(Long id); }
