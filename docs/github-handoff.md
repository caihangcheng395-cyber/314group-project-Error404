# Add this version to your existing GitHub repository

1. Clone/download your existing repository and keep a backup.
2. Create a branch, for example `feature/forma-framework-adaptation`.
3. Extract this archive. Copy the **contents** of `Forma_Framework_Adapted` into your repository root, preserving the top-level backend/frontend/docs/scripts/.github layout. Do not add another nested project folder.
4. Review changed files. Keep your existing `.git` directory and remote. Do not add node_modules, target, dist, H2 data, logs or secrets; the existing .gitignore covers them.
5. Run the README test commands, then `docker compose up --build` and test localhost:3000.
6. Commit the source changes and push the feature branch. Open a pull request so your team can review before merging.

```bash
git checkout -b feature/forma-framework-adaptation
git add backend frontend docs scripts .github compose.yaml README.md .gitignore .gitattributes
git commit -m "Adapt Forma to React Spring Boot H2 BCE framework"
git push -u origin feature/forma-framework-adaptation
```

Only run the git commands inside your own repository after copying/reviewing the files. This archive contains no Git history or fabricated member commits. The previous ChatGPT-hosted site is separate; updating this repository does not deploy that site. Your live SQL data from that site is not included.
