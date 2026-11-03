# Validation record — 3 November 2026

These are checks actually run during the adaptation, not proposed checks or fabricated Scrum evidence.

| Check | Result |
|---|---|
| Java 17 compilation | Passed |
| Maven verify, including original category tests | 12 tests, 0 failures, 0 errors |
| Original category unit/boundary/integration tests | All 5 retained and passed |
| Added cross-feature integration tests | 7 passed |
| Frontend API-adapter tests | 7 passed (original 2 plus 5 new) |
| Vite production build | Passed |
| Spring Boot executable JAR packaging | Passed |
| Packaged server startup and cookie/CSRF login | Passed |
| H2 company record after backend restart | Persisted; check passed |
| Seed records after restart | No duplicate categories/projects/team accounts |
| Original category model/read classes/test files and compose.yaml | Byte-for-byte retained; repository extended separately |
| Docker build and compose startup | Not run: Docker unavailable here |
| Full React-to-Java browser walkthrough | Not verified: browser preview host could not launch Java alongside Vite |
| GitHub Actions / GHCR publication / online deployment | Not triggered or performed |

The Java integration suite checks CSRF, wrong-role access, all six named accounts' assigned-role logins, pending signup, admin-only approval, no self-assigned management role, separate profile/account lifecycles, session revocation after suspend/restore, project ownership, category relationships, in-use deletion guards, public company details, shortlists, view counts, rolling reports and CSV output. The frontend tests check request URLs, JSON contracts, cookie credentials, CSRF reuse/reset and error handling; they are not component acceptance tests.

The original category model, read Boundary/Control, three Java test source files, category API adapter/test and compose.yaml were compared with the uploaded source and retained byte-for-byte. New code extends them without redefining IDPCategory.

Next, run the README commands on your Windows/Mac machine, check Docker and all user stories in the browser, and retain the resulting real team/CI/UML evidence. This archive does not claim exhaustive story acceptance, performance, accessibility, penetration or production-readiness testing.
