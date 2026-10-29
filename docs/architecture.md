# Architecture & BCE mapping

## Implemented vertical slice: View IDP Category

```
React ViewIDPCategoryPage.jsx
   └─ categoryApi.listCategories() → GET /api/categories
       └─ Java boundary/ViewIDPCategoryPage.java  [<<boundary>>]
           └─ Java controller/ViewIDPCategoryController.java  [<<control>>]
               └─ repository/IDPCategoryRepository.java  [persistence adapter]
                   └─ entity/IDPCategory.java  [<<entity>>]
                       └─ H2 table idp_categories
```

- **Frontend React page**: presents data, handles user interactions, calls APIs; it is distinct from the server-side BCE Boundary class.
- **Boundary**: maps HTTP routes and parameters; delegates business decisions to Control.
- **Control**: implements one use-case workflow; coordinates Entity and Repository; maps Entity to DTO.
- **Entity**: models business state and rules; JPA maps it to persistent storage.
- **Repository**: technical persistence interface; *not* a replacement for the BCE Entity.
- **DTO**: data sent to the browser; avoid returning JPA entities directly.

The project PDF requires design based on BCE and at least an object-oriented backend/middleware. It does not prescribe a framework.

## UML consistency for this example

- User story: **#42 View an IDP Category** (according to the team's earlier list; verify final group version).
- Actor: Platform Management User.
- Boundary: `ViewIDPCategoryPage`.
- Control: `ViewIDPCategoryController`.
- Entity: `IDPCategory`.
- Calls: `listCategories()` / `viewCategory(categoryId)` → controller → repository → entity.
- Return: `IDPCategoryResponse` JSON → React page.

If your current UML shows different methods, **revise the UML or implementation** before submission. Do not claim they match if they do not.

## Features not implemented yet

Registration/login, permissions, ID company, IDP CRUD, favourites, interest tracking, category CRUD beyond viewing, reporting, suspend and logout. See `team-plan.md` and `database-design.md`.
