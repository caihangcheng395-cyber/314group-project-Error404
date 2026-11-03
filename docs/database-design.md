# Shared H2/JPA model

All entities live in the existing `entity` package. There is one shared model; no duplicate category or IDP class is introduced.

| Entity / table | Purpose and relationships |
|---|---|
| IDPCategory / idp_categories | Original Long ID, unique name, description, active flag |
| UserProfile / user_profiles | Personal/contact fields, independent ACTIVE/SUSPENDED status |
| UserAccount / user_accounts | Unique login email, PBKDF2 hash, default role, status, credential version; optional one-to-one profile |
| account_roles | Assigned workspaces, eager element collection |
| Company / companies | Designer owner account, business fields |
| IDP / interior_design_projects | Owner, company and category foreign keys; stage/progress and design details |
| Favourite / favourites | Unique customer/project pair, created timestamp |
| ProjectViewEvent / project_view_events | Customer detail-open events linked to project/viewer |
| ActivityEvent / activity_events | Action type, actor/project identifiers, UTC timestamp; reporting history |
| RegistrationRequest / registration_requests | Proposed role/profile, hashed password, pending/review state, requester token hash, reviewer and created account |
| ApplicationSetting / application_settings | One-time sample-data marker |

Profiles can exist without accounts. Creating an account may link an existing unassigned profile; deleting a profile unlinks it without deleting login access. Account status controls login; profile status controls personal-profile administration. Password/role changes and account status changes increment the credential version, invalidating old sessions.

Signup creates only a pending RegistrationRequest. User Admin approval creates its UserProfile and UserAccount in a transaction under a pessimistic request lock. Repeated approval and duplicate email are rejected. Passwords use PBKDF2-HMAC-SHA256, 210,000 iterations, random 16-byte salts, and constant-time comparison. Hashes never appear in response DTOs.

The original 12 category samples remain. New sample accounts/companies/projects use a separate one-time marker, so restarting does not restore deleted sample projects. A changed category name immediately appears through its existing IDP foreign-key relationship. Shortlist counts represent active customer saves; activity reports also record additions/removals over time.

Local H2 URL: `jdbc:h2:file:./data/idpdb;DB_CLOSE_ON_EXIT=FALSE`, relative to backend working directory. Docker uses the existing named volume and `/app/data`. The authoring smoke test confirmed persistence across process restart. Do not commit runtime data. `ddl-auto: update` is retained from the starter for coursework; production migrations/backups remain future work. Data from the former hosted D1 database is not migrated into this archive.
