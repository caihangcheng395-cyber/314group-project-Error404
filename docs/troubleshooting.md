# Mac / Windows troubleshooting

## `mvn` not found / not recognized
Install Maven 3.9+ and ensure its `bin` directory is on `PATH`, then reopen Terminal or PowerShell. Alternatively use Docker Desktop and `docker compose up --build` from the repository root.

## Java version mismatch
Use JDK 17 (`java -version`). The project targets Java 17. Newer JDKs may work but everyone should standardise on 17 to reduce differences.

## `npm` not recognized
Install Node.js 20 and restart your terminal. Check `node --version` and `npm --version`.

## Website loads but categories fail
Check backend at `http://localhost:8080/api/categories` first. With Vite dev server use `http://localhost:5173`; with Docker Compose use `http://localhost:3000`. Avoid opening `index.html` as a file.

## Ports in use
Only one process can bind to a port. Stop other local servers/containers or change Vite/Docker mappings and backend `server.port` consistently.

## Database records persist between runs
Local mode uses a file in `backend/data`. Docker mode uses the `backend-data` named volume. Do not delete these while you need data. `docker compose down -v` deletes the volume.

## Windows Docker
Use Docker Desktop with Linux containers / WSL2 backend enabled. Run `docker compose up --build` from PowerShell in the root folder containing `compose.yaml`.

## Git line endings
The `.gitattributes` file keeps shell scripts LF across Mac and Windows. Avoid manually editing generated dependencies or `target/`.

## First build requires internet
Maven and npm download dependencies and Docker downloads base images on the first build. A disconnected computer cannot complete initial setup.
