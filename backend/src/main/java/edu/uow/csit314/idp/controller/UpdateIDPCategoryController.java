package edu.uow.csit314.idp.controller;

import edu.uow.csit314.idp.dto.IDPCategoryResponse;
import edu.uow.csit314.idp.dto.UpdateIDPCategoryRequest;
import edu.uow.csit314.idp.entity.IDPCategory;
import edu.uow.csit314.idp.repository.IDPCategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/** BCE Control: validates and persists updates to an existing IDP category. */
@Service
public class UpdateIDPCategoryController {
    private final IDPCategoryRepository repository;

    public UpdateIDPCategoryController(IDPCategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public IDPCategoryResponse updateCategory(Long categoryId, UpdateIDPCategoryRequest request) {
        IDPCategory category = repository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "IDP category not found: " + categoryId));

        // Jakarta Bean Validation handles null, blank and length checks at the HTTP boundary.
        String name = request.name().trim();
        String description = request.description() == null ? "" : request.description().trim();
        if (name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name is required");
        }
        if (repository.existsByNameIgnoreCaseAndIdNot(name, categoryId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists");
        }

        category.rename(name);
        category.changeDescription(description);
        category.setActive(request.active());

        try {
            // Flush now so database uniqueness errors are returned as HTTP 409.
            return IDPCategoryResponse.from(repository.saveAndFlush(category));
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists", ex);
        }
    }
}
