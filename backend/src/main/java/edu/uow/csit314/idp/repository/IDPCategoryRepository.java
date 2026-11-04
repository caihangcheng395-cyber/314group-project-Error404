
package edu.uow.csit314.idp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.uow.csit314.idp.entity.IDPCategory;

/** Persistence adapter, separate from the BCE Control. */
public interface IDPCategoryRepository extends JpaRepository<IDPCategory, Long> {

    List<IDPCategory> findAllByOrderByNameAsc();

    // US41: Check duplicate names when updating a category.
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    // Main branch: Find a category by name.
    Optional<IDPCategory> findByNameIgnoreCase(String name);

    // Main branch: Check whether a category name already exists.
    boolean existsByNameIgnoreCase(String name);
}

