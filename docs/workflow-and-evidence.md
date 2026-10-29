# Git, Scrum, TDD, CI/CD — real evidence checklist

## Git workflow

1. Clone the common repository; do not create six unrelated repositories.
2. `git checkout -b feature/member6-category-view`
3. Implement a small task, run `mvn test` and/or `npm test`.
4. Commit meaningful changes; open a Pull Request to `main`.
5. Resolve conflicts and review tests before merging.

## TDD — evidence must reflect what actually happened

For one new behaviour (e.g. category update validation):

1. Write a failing JUnit test **before** implementation (RED); capture the failed test output and commit.
2. Implement the minimum code to pass (GREEN); capture passed test output and commit.
3. Refactor while keeping tests passing; capture results/commit.
4. Explain test inputs, expected outputs and edge cases in the final report.

The starter includes tests, but **does not prove that these tests were written before the code**. Do not claim this starter alone demonstrates TDD.

## Scrum / Agile evidence

- Record actual Product Backlog, user stories and subtasks in Taiga or another tool.
- Record Sprint Backlog and screenshots each week.
- Keep true meeting minutes with date, attendees, progress, decisions, action items and owner.
- Show tutor feedback and subsequent changes where applicable.
- Collect each member's real contributions and signatures for report cover page.

## CI/CD evidence

- `.github/workflows/ci.yml`: automatic backend tests and frontend tests/build on push/PR.
- `.github/workflows/delivery.yml`: publishes tagged Docker images to GHCR (continuous delivery artifact).
- `docker compose up --build`: deploys a local running version of frontend+backend; capture terminal output, website and API screenshots.
- To meet the **deployment evidence** expectation, document an actual run/deployment of at least one feature, how it was triggered, and its result. A YAML file by itself is insufficient.
- If the group deploys to a cloud host, add the real host-specific deployment step and keep evidence; do not invent deployment logs.

## Demo and test data

- Generate enough realistic records to simulate scale (assignment gives e.g. 100 per data type).
- Run real user flows in the recorded demo.
- Show failures/validation, not only happy paths.
- Never publish real users' personal data or passwords in demo records.
