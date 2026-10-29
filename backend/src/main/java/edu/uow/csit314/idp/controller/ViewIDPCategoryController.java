package edu.uow.csit314.idp.controller;

import edu.uow.csit314.idp.dto.IDPCategoryResponse;
import edu.uow.csit314.idp.repository.IDPCategoryRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/** BCE Control: orchestrates the View IDP Category use case. */
@Service
public class ViewIDPCategoryController {
    private final IDPCategoryRepository repository;

    public ViewIDPCategoryController(IDPCategoryRepository repository) {
        this.repository = repository;
    }

    public List<IDPCategoryResponse> viewAllCategories() {
        return repository.findAllByOrderByNameAsc().stream()
                .map(IDPCategoryResponse::from)
                .toList();
    }

    public IDPCategoryResponse viewCategory(Long categoryId) {
        return repository.findById(categoryId)
                .map(IDPCategoryResponse::from)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "IDP category not found: " + categoryId));
    }
}
