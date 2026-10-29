# Database design: implemented vs planned

## Already implemented

`IDPCategory` → `idp_categories`

- `id`: Long, auto-generated primary key
- `name`: required, unique, max 100 characters
- `description`: optional, max 500 characters
- `active`: required boolean

H2 local development URL: `jdbc:h2:file:./data/idpdb;DB_CLOSE_ON_EXIT=FALSE` (relative to backend working directory). Docker persists in named volume `backend-data`.

`SampleDataInitializer` inserts 12 example categories **only when the table is empty**. This is not the full-scale test dataset expected by the assignment.

## Planned — not yet implemented

| Entity | Suggested key fields | Module owner |
|---|---|---|
| User | id, email, passwordHash, role, status | Member 1 |
| InteriorDesignerProfile | id, userId, displayName | Member 1/2 agree owner |
| Company | id, designerId, name, description, contact | Member 2 |
| IDP | id, designerId, categoryId, title, description, status | Member 3 |
| Favourite | customerId, projectId, createdAt | Member 4 |
| ProjectViewEvent | id, projectId, viewerId/anonymousToken, viewedAt | Member 5 |
| DailyReport | date, aggregate metrics (derived or stored) | Member 6 |

Relationships should be agreed before implementing: Company→Designer, IDP→Designer, IDP→Category, Favourite→Customer+IDP, ViewEvent→IDP.

**Rules:** no duplicate User/IDP entity classes across modules; use migrations (e.g. Flyway) before production; do not store plain-text passwords; protect sensitive user data. Actual final design must follow the team's UML and tutor feedback.
