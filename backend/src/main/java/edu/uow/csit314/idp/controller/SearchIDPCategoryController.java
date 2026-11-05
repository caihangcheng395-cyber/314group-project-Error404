
package edu.uow.csit314.idp.controller;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uow.csit314.idp.dto.IDPCategoryResponse;
import edu.uow.csit314.idp.repository.IDPCategoryRepository;

/**
 * US43: Search an IDP Category.
 * BCE Controller: handles category search requests.
 */
@Service
@Transactional(readOnly = true)
public class SearchIDPCategoryController {

    private final IDPCategoryRepository repository;

    public SearchIDPCategoryController(
            IDPCategoryRepository repository) {
        this.repository = repository;
    }

    public List<IDPCategoryResponse> searchCategory(String keyword) {

        String query = keyword == null ? "" : keyword.trim();

        if (query.isEmpty()) {
            return repository.findAllByOrderByNameAsc()
                    .stream()
                    .map(IDPCategoryResponse::from)
                    .toList();
        }

        return repository
                .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrderByNameAsc(
                        query, query)
                .stream()
                .map(IDPCategoryResponse::from)
                .toList();
    }
}
