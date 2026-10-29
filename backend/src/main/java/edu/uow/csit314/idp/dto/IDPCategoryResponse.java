package edu.uow.csit314.idp.dto;

import edu.uow.csit314.idp.entity.IDPCategory;

/** HTTP response DTO: avoids exposing the JPA entity directly. */
public record IDPCategoryResponse(Long id, String name, String description, boolean active) {
    public static IDPCategoryResponse from(IDPCategory category) {
        return new IDPCategoryResponse(category.getId(), category.getName(),
                category.getDescription(), category.isActive());
    }
}
