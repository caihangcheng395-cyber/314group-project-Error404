package edu.uow.csit314.idp.boundary;

import edu.uow.csit314.idp.controller.UpdateIDPCategoryController;
import edu.uow.csit314.idp.dto.IDPCategoryResponse;
import edu.uow.csit314.idp.dto.UpdateIDPCategoryRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** BCE Boundary: accepts a category update from the UI. */
@RestController
@RequestMapping("/api/categories")
public class UpdateCategoryPage {
    private final UpdateIDPCategoryController controller;

    public UpdateCategoryPage(UpdateIDPCategoryController controller) {
        this.controller = controller;
    }

    @PutMapping("/{categoryId}")
    public IDPCategoryResponse updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody UpdateIDPCategoryRequest request) {
        return controller.updateCategory(categoryId, request);
    }
}
