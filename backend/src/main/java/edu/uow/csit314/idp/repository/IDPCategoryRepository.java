package edu.uow.csit314.idp.repository;

import edu.uow.csit314.idp.entity.IDPCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/** Persistence adapter, separate from the BCE Control. */
public interface IDPCategoryRepository extends JpaRepository<IDPCategory, Long> {
    List<IDPCategory> findAllByOrderByNameAsc();
    Optional<IDPCategory> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
