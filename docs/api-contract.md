# API contract — team-owned shared document

**Base path:** `/api`. All requests/responses use JSON unless documented otherwise. Use plural resource names. Errors should use suitable HTTP status codes; implement a shared error schema before multiple modules add write operations.

## Implemented now

### GET /api/categories
Returns HTTP 200 and an array of objects:

```json
[
  { "id": 1, "name": "Living Room", "description": "Comfortable spaces", "active": true }
]
```

### GET /api/categories/{categoryId}
Returns HTTP 200 with one category object, or HTTP 404 when missing.

### PUT /api/categories/{categoryId} — User Story #41

Updates a category. Request body:

```json
{ "name": "Master Bedroom", "description": "Restful interiors", "active": true }
```

Returns HTTP 200 with updated `IDPCategoryResponse`, HTTP 400 for invalid input, HTTP 404 for unknown ID, or HTTP 409 for duplicate name. **Development only until server-side platform-manager authorization is implemented.** See `docs/user-story-41.md`.

## Proposed routes — NOT implemented

| Owner | Proposed endpoint | Use case |
|---|---|---|
| Member 1 | POST /api/auth/login | Login |
| Member 1 | POST /api/auth/logout | Logout (coordinate with Member 6) |
| Member 1 | GET /api/users/me | Current user profile |
| Member 2 | GET/PUT /api/companies/{id} | Manage company |
| Member 3 | GET/POST /api/projects | List/create IDPs |
| Member 3 | GET/PUT/DELETE /api/projects/{id} | Manage IDP |
| Member 4 | GET /api/projects?query= | Search IDPs |
| Member 4 | POST/DELETE /api/favourites/{projectId} | Save/remove favourite |
| Member 5 | POST /api/projects/{id}/views | Record view (validate duplicate policy) |
| Member 5 | GET /api/projects/{id}/metrics | Views and shortlists |
| Member 6 | GET /api/reports/daily?date=YYYY-MM-DD | Generate/view daily report |
| Member 6 | GET /api/reports/daily/export?date=YYYY-MM-DD | Export report |
| Member 6 | PATCH /api/designers/{id}/suspension | Suspend IDP account (coordinate with Member 1) |

**Before coding:** confirm final user-story list and define request/response DTOs, field names, HTTP methods, validation, authentication and ownership. These are design suggestions, not mandated URLs in the assignment PDF.
