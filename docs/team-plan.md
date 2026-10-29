# Suggested six-person module ownership

This is a proposed allocation, **not** a statement about your teammates' actual progress.

| Member | Primary scope | Cross-module dependency |
|---|---|---|
| 1 | User registration/login/profile, auth and roles | Shared `User`, logout, suspension |
| 2 | Interior designer company information | User/Designer identity |
| 3 | IDP portfolio CRUD and images | Designer, Category |
| 4 | Customer search, view and favourites | IDP, User |
| 5 | View count / shortlist count analytics | IDP, Favourite |
| 6 | Category management, daily report, export, suspension, logout | Category, User, IDP, metrics |

## Member 6's previously discussed stories

- #41 Update IDP Category
- #42 View IDP Category (**starter example implemented**)
- #43 Search IDP Category
- #44 Generate Daily Report
- #45 View Daily Report
- #46 Export Daily Report
- #47 Suspend IDP Account
- #48 Log out

**Verify the exact final stories with the group's latest approved list.** Story #47 and #48 require Member 1's shared authentication/role model. Reports require data from Members 3–5.

## Rules for shared ownership

1. Member 1 owns User and authentication contract.
2. Member 3 owns IDP entity; Member 6 does not redefine it.
3. Member 6 owns IDPCategory entity and category API; Member 3 references it.
4. Member 4 owns Favourite events; Member 5 derives shortlist metrics with agreed semantics.
5. No member changes a shared field/API without a pull request reviewed by affected members.
6. One person integrates and verifies main branch; all six still own and test their modules.
