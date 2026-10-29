# User Story #41 — Update an IDP Category

**Story:** As a platform manager, I want to update an IDP category so that the category information remains accurate.

## Scope and acceptance criteria

1. From the category details modal, the user can open **Edit Category**.
2. The edit form pre-fills the current category name, description, and active status.
3. A valid submission calls `PUT /api/categories/{id}` and saves changes to H2.
4. After saving, the list and details display the updated category without refreshing the browser.
5. A blank name, name longer than 100 characters, description longer than 500 characters, or missing active status returns HTTP 400.
6. An unknown category ID returns HTTP 404.
7. A duplicate name (case-insensitive, excluding the category being edited) returns HTTP 409.
8. Cancel leaves the category unchanged.

## BCE mapping

- **Boundary:** `UpdateCategoryPage` (Java HTTP adapter); `UpdateIDPCategoryPage.jsx` (React form)
- **Control:** `UpdateIDPCategoryController`
- **Entity:** existing `IDPCategory` (reused, not duplicated)
- **Persistence:** existing `IDPCategoryRepository` with a duplicate-name query
- **Request DTO:** `UpdateIDPCategoryRequest`
- **Response DTO:** existing `IDPCategoryResponse`

## HTTP contract

`PUT /api/categories/{id}`

```json
{
  "name": "Master Bedroom",
  "description": "Modern and restful private interiors.",
  "active": true
}
```

Success: HTTP 200 with `{ "id": 3, "name": "Master Bedroom", "description": "...", "active": true }`.

Errors: HTTP 400 (invalid input), 404 (not found), 409 (duplicate name).

## Tests included

- `UpdateIDPCategoryControllerTest`: successful update and normalization; missing ID; duplicate name.
- `UpdateCategoryPageTest`: successful HTTP update; invalid name; missing active flag.
- `UpdateCategoryIntegrationTest`: HTTP-to-H2 persistence; duplicate name.
- `frontend/src/services/categoryApi.test.js`: PUT request and failure handling.

**TDD evidence note:** Tests are provided but this patch by itself is **not proof** that Red → Green → Refactor occurred. Record the real failing-test run, implementation run, and refactoring commits/screenshots in your team workflow.

## Security and integration note

This starter currently has **no authentication or authorization**. The PUT endpoint is not yet restricted to platform managers. **Do not deploy or expose this as a real administrative system** until Member 1 and Member 6 integrate login, roles, and server-side access checks. Hiding an edit button in React is not access control.

The project specification calls for much larger test datasets (e.g., 100 records per datatype) and actual client/tutor feedback. The 12 seed categories are only development demo data.
