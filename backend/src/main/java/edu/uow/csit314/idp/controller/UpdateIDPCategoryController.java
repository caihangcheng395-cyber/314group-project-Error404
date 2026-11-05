
package edu.uow.csit314.idp.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import edu.uow.csit314.idp.dto.IDPCategoryResponse;
import edu.uow.csit314.idp.dto.UpdateIDPCategoryRequest;
import edu.uow.csit314.idp.entity.IDPCategory;
import edu.uow.csit314.idp.entity.Role;
import edu.uow.csit314.idp.entity.UserAccount;
import edu.uow.csit314.idp.repository.IDPCategoryRepository;
import jakarta.servlet.http.HttpSession;

/** BCE Control: validates and persists updates to an existing IDP category. */
@Service
public class UpdateIDPCategoryController {

    private final IDPCategoryRepository repository;
    private final AuthenticationController auth;
    private final ActivityController activity;

    public UpdateIDPCategoryController(
            IDPCategoryRepository repository,
            AuthenticationController auth,
            ActivityController activity) {

        this.repository = repository;
        this.auth = auth;
        this.activity = activity;
    }

    @Transactional
    public IDPCategoryResponse updateCategory(
            Long categoryId,
            UpdateIDPCategoryRequest request,
            HttpSession session) {

        // Only Platform Managers can update categories.
        UserAccount user = auth.require(session, Role.PLATFORM_MANAGER);

        // Find the existing category.
        IDPCategory category = repository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "IDP category not found: " + categoryId));

        // Validate the category information.
        String name = request.name().trim();
        String description = request.description() == null
                ? ""
                : request.description().trim();

        if (name.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Category name is required");
        }

        // Prevent duplicate category names.
        if (repository.existsByNameIgnoreCaseAndIdNot(name, categoryId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Category name already exists");
        }

        // Update the entity.
        category.rename(name);
        category.changeDescription(description);
        category.setActive(request.active());

        try {
            IDPCategory updated = repository.saveAndFlush(category);

            // Record the update for platform activity reports.
            activity.record("category_updated", user, null);

            return IDPCategoryResponse.from(updated);

        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Category name already exists",
                    ex);
        }
    }
}
