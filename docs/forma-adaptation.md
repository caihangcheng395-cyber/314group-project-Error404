# Forma adaptation handoff

## What was preserved

The existing `backend/`, `frontend/`, `.github/workflows/`, `scripts/`, `docs/`, `compose.yaml`, Java package, Maven parent/version, React/Vite versions, H2 persistence setup, IDPCategory model, original category GET API, original category controller/boundary and original five Java/two frontend category tests. The original category React component is retained; the active Forma category screen uses the same contract and adds management actions.

## What changed

Forma's UI was rebuilt as React components in `AuthPages`, `UserPages`, `DesignPages`, `PlatformPages`, shared components, and App. Its TypeScript/Cloudflare backend was replaced with Java Boundary/Control/Entity/Repository/DTO classes. No Cloudflare bindings, ChatGPT identity headers, hidden platform files or old backend are needed. `npm ci` now uses your committed package-lock in Docker and CI. A Mockito subclass mock maker avoids JVM agent attachment restrictions without skipping the original tests.

## Story-to-control mapping

| Stories | Main implementation |
|---|---|
| 1–5 | UserManagementController profile methods |
| 6–10 | UserManagementController account methods |
| 11–12,18–19,35–36,47–48 | AuthenticationController login/logout/assigned-role methods |
| 13–17 | CompanyManagementController |
| 20–29 | IDPManagementController, CURRENT/PAST stage |
| 30–31 | IDPManagementController public list/view, DiscoverPage filters |
| 32–34 | CustomerProjectsController favourites, DiscoverPage shortlist |
| 37–38 | CustomerProjectsController recordView; IDPManagementController metrics |
| 39,41,42 | CategoryManagementController create/update/delete |
| 40,43 | Original category read controls and CategoriesPage search |
| 44–46 | PlatformReportsController daily/weekly/monthly generation |

Signup is an additional approval workflow in RegistrationController, consistent with admin-owned profile/account creation. Reports are rolling windows; CSV export is additional. No production migration or email service has been added.

## Before merging

Review the code and exact domain naming with your team and tutor; update final UML. Run Docker on your machine and perform complete acceptance tests against the latest stories. Use `docs/github-handoff.md` and keep the resulting actual test/CI evidence. Do not claim the new test suite proves every possible UI behavior or assignment requirement.
