package edu.uow.csit314.idp.boundary;

import edu.uow.csit314.idp.controller.ViewIDPCategoryController;
import edu.uow.csit314.idp.dto.IDPCategoryResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** BCE Boundary: HTTP entry point for the View IDP Category use case. */
@RestController
@RequestMapping("/api/categories")
public class ViewIDPCategoryPage {
    private final ViewIDPCategoryController controller;

    public ViewIDPCategoryPage(ViewIDPCategoryController controller) {
        this.controller = controller;
    }

    @GetMapping
    public List<IDPCategoryResponse> listCategories() {
        return controller.viewAllCategories();
    }

    @GetMapping("/{categoryId}")
    public IDPCategoryResponse viewCategory(@PathVariable Long categoryId) {
        return controller.viewCategory(categoryId);
    }
}
