# API contract — adapted implementation

Base: `/api`. JSON unless stated. Browser requests use same-origin session cookies. Errors use `{ "message": "..." }`. Expected error statuses are 400 validation, 401 missing/invalidated session, 403 wrong role/ownership/CSRF, 404 missing record, and 409 conflicting or linked records.

## Authentication and signup

| Method | Route | Access / payload |
|---|---|---|
| GET | `/auth/csrf` | Public session; returns `{token}` |
| GET | `/auth/me` | Current AccountResponse, or empty 200 when signed out |
| POST | `/auth/login` | `{email,password,workspace?}`; workspace must be assigned |
| POST | `/auth/logout` | Invalidate session |
| POST | `/auth/workspace` | `{workspace}`; must be assigned |
| GET | `/auth/demo-users` | Demo team metadata only; empty when disabled |
| POST | `/auth/demo-login` | `{userId,workspace}`; demo-only configured accounts |
| POST | `/auth/signup` | `{name,email,role,password,confirmPassword,phone?,companyName?}`; Customer/Designer only; 201 pending request |
| GET | `/auth/signup-status` | Requests associated with this browser/server session |
| GET | `/registrations` | User Admin only |
| POST | `/registrations/{id}/approve` | User Admin; `{role}` Customer/Designer; creates profile and account atomically |
| POST | `/registrations/{id}/reject` | User Admin; creates no account |

Every POST/PUT/PATCH/DELETE needs `X-CSRF-Token` from `/auth/csrf`. Login rotates the session ID while preserving the CSRF token. Logout invalidates the session and the frontend clears its cached token. Role values are `CUSTOMER`, `DESIGNER`, `USER_ADMIN`, `PLATFORM_MANAGER`.

## User Admin

| Method | Route | Payload / behavior |
|---|---|---|
| GET, POST | `/profiles` | List/search `?query=`; create ProfileRequest |
| GET, PUT, DELETE | `/profiles/{id}` | View/update/remove personal profile; linked login account is retained |
| PATCH | `/profiles/{id}/suspension` | Toggle ACTIVE/SUSPENDED profile status |
| GET, POST | `/accounts` | List/search `?query=`; create AccountRequest |
| GET, PUT | `/accounts/{id}` | View/update account |
| PATCH | `/accounts/{id}/suspension` | Toggle account status and revoke existing sessions |

ProfileRequest: `{name,email,phone?,address?,bio?}`. AccountRequest: `{email,password?,defaultRole,roles?,profileId?,name?}`. New accounts require a password. Supply an unlinked existing profileId or a name to create a profile. The default role is always included; admin cannot remove their own admin access or suspend their own account. Account login email and profile contact email are deliberately separate.

## Designer / Customer

| Method | Route | Access |
|---|---|---|
| GET, POST | `/companies` | Designer's own companies; GET `?query=` |
| GET, PUT, DELETE | `/companies/{id}` | Owner designer |
| GET | `/companies/{id}/public` | Public business profile for project details |
| GET | `/projects` | Public listing; `?query=&stage=CURRENT/PAST&mine=true`; mine requires Designer |
| GET, PUT, DELETE | `/projects/{id}` | Read public; writes owner Designer |
| POST | `/projects` | Designer; own company and active category |
| GET | `/projects/metrics` | Designer's own views/shortlist counts |
| GET | `/favourites` | Customer's own saved projects; optional query |
| POST, DELETE | `/favourites/{projectId}` | Customer; save is idempotent |
| POST | `/projects/{projectId}/views` | Customer; records one detail-open event |

CompanyRequest: `{name,email,phone?,address?,description?}`. ProjectRequest: `{title,companyId,categoryId,stage,propertyType,location?,budget,area,progress,imageUrl?,description?}`. propertyType is HDB/Condominium/Landed/Commercial; stage CURRENT/PAST. PAST forces progress to 100. HTTPS images only. Deleting a project removes its active favourites/view-detail records; timestamped activity remains available to reports. Companies/categories in use return 409 on deletion.

## Category and report APIs

The original `GET /categories` and `GET /categories/{id}` response `{id,name,description,active}` is unchanged. Public reads support frontend filters; no authentication is needed for the starter's original category tests. Platform Managers alone can `POST /categories`, `PUT /categories/{id}` and `DELETE /categories/{id}`. CategoryRequest: `{name,description?,active}`.

`GET /reports/{period}` and `/reports/{period}/export` require Platform Management. period = daily/weekly/monthly, using rolling UTC windows of 1/7/30 days. Export returns `text/csv` with Content-Disposition. Reports derive counts from timestamped activity, not hardcoded numbers; initial demo inventory is not counted as creation activity.
