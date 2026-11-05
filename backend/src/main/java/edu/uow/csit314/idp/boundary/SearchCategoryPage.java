
package edu.uow.csit314.idp.boundary;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.uow.csit314.idp.controller.SearchIDPCategoryController;
import edu.uow.csit314.idp.dto.IDPCategoryResponse;

/**
 * US43: Search an IDP Category.
 * BCE Boundary: receives category search requests.
 */
@RestController
@RequestMapping("/api/categories")
public class SearchCategoryPage {

    private final SearchIDPCategoryController controller;

    public SearchCategoryPage(
            SearchIDPCategoryController controller) {
        this.controller = controller;
    }

    @GetMapping("/search")
    public List<IDPCategoryResponse> searchCategory(
            @RequestParam(defaultValue = "") String keyword) {

        return controller.searchCategory(keyword);
    }
}
