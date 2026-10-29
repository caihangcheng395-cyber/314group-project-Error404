package edu.uow.csit314.idp.repository;

import edu.uow.csit314.idp.entity.IDPCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/** Persistence adapter, separate from the BCE Control. */
public interface IDPCategoryRepository extends JpaRepository<IDPCategory, Long> {
    List<IDPCategory> findAllByOrderByNameAsc();

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}
