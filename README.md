![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![TypeScript](https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white)
![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB)
# AgentScaffold

AgentScaffold is a full-stack application for generating a ready-to-use AI engineering team for software projects. Pick a curated preset, choose the technology stack, and download a ZIP containing specialized Markdown agents for tools such as Claude, Codex, OpenCode, or any coding assistant that supports agent/skill-style instructions.

The product is aimed at teams that want consistent AI coding collaborators across architecture, implementation, testing, and final review work. Instead of hand-writing agent prompts per project, AgentScaffold combines stack-specific definitions with reusable templates and packages the result as portable agent files.

## What It Generates

The generated output is a ZIP file named after the project, for example:

```text
demo-agents.zip
`-- agents/
    |-- backend-architect.md
    |-- backend-architect-reviewer.md
    |-- backend-implementer.md
    |-- backend-tester.md
    |-- frontend-architect.md
    |-- frontend-architect-reviewer.md
    |-- frontend-implementer.md
    |-- frontend-tester.md
    `-- final-reviewer.md
```

The exact files depend on the selected preset.

## Features

- Curated presets for full-stack, backend-only, frontend-only, startup, React, and Spring workflows.
- Stack-aware agent generation using backend, frontend, and general agent categories.
- Mustache-based templates that inject stack definitions into reusable agent files.
- In-memory ZIP generation with sanitized project filenames.
- React multi-step generation flow with validation and download handling.
- Lookup pages for available agents, presets, and stacks.
- REST API with OpenAPI/Swagger support.
- Unit and integration tests for backend services, validation, resolvers, utilities, and APIs.
- Frontend tests for API helpers, theme behavior, form schema validation, hooks, and multi-step UI.
- Dockerized backend and frontend services.

## Supported Presets

| Preset | Purpose |
| --- | --- |
| `enterprise-fullstack` | Full backend and frontend pipeline with architecture, review, implementation, testing, and final review agents. |
| `enterprise-spring` | Backend-focused Spring pipeline with architecture, review, implementation, testing, and final review agents. |
| `enterprise-react` | Frontend-focused React pipeline with architecture, review, implementation, testing, and final review agents. |
| `startup-ready` | Lean full-stack setup with architecture, implementation, and testing agents. |
| `spring-ready` | Streamlined Spring backend setup. |
| `react-ready` | Streamlined React frontend setup. |

## Supported Stacks

| Category | Stack ID |
| --- | --- |
| Backend | `java-spring` |
| Frontend | `typescript-react` |
| General | `general` |

## Tech Stack

### Frontend

- React 19
- TypeScript
- Vite
- Tailwind CSS 4
- React Router
- TanStack Query
- React Hook Form
- Zod
- Vitest and Testing Library
- shadcn/Base UI-style components

### Backend

- Java 25
- Spring Boot 4.1
- Spring Web MVC
- Spring Validation
- Spring Actuator
- Springdoc OpenAPI
- Mustache template rendering
- JUnit-based unit and integration tests
- JaCoCo coverage checks

## Project Structure

```text
.
|-- backend/
|   |-- src/main/java/com/lfav07/agentscaffold/
|   |   |-- controller/       # REST endpoints
|   |   |-- dto/              # API request/response records
|   |   |-- model/            # preset, stack, and agent enums
|   |   |-- registry/         # lookup registries for UI/API discovery
|   |   |-- resolver/         # preset and stack context resolution
|   |   |-- service/          # generation and retrieval services
|   |   `-- util/             # template rendering and ZIP generation
|   |-- src/main/resources/
|   |   |-- definitions/      # stack-specific agent instructions
|   |   `-- templates/        # reusable Mustache agent templates
|   `-- src/test/             # unit and integration tests
|-- frontend/
|   |-- src/features/
|   |   |-- agents/           # available agents UI/API hooks
|   |   |-- command-palette/  # app command palette
|   |   |-- generation/       # multi-step scaffold generation flow
|   |   |-- home/             # product landing page
|   |   |-- presets/          # preset listing
|   |   `-- stacks/           # stack listing
|   `-- src/shared/           # API client, theme, shared components
`-- docker-compose.yml
```

## How Generation Works

1. The frontend loads available presets and stacks from the backend.
2. The user enters a project name, chooses a preset, and selects the required stack values.
3. `POST /api/v1/scaffold` sends the generation request.
4. The backend resolves the selected preset into a set of core agent types.
5. Each agent type is matched to the correct stack category: backend, frontend, or general.
6. The backend loads a stack definition from `backend/src/main/resources/definitions`.
7. The backend renders a matching Mustache template from `backend/src/main/resources/templates`.
8. Rendered files are packed into an in-memory ZIP and returned as a download.

## Local Development

### Prerequisites

- Java 25
- Node.js 20+
- npm
- Docker, optional

### Backend

```bash
cd backend
./mvnw spring-boot:run
```

The backend runs on:

```text
http://localhost:8080
```

Useful backend URLs:

```text
http://localhost:8080/api/v1/presets
http://localhost:8080/api/v1/stacks
http://localhost:8080/api/v1/agents
http://localhost:8080/swagger-ui/index.html
```

On Windows PowerShell, use:

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

### Frontend

Create a frontend environment file:

```bash
cd frontend
printf "VITE_API_URL=http://localhost:8080/api/v1\n" > .env
npm install
npm run dev
```

The frontend runs on:

```text
http://localhost:5173
```

On Windows PowerShell, create the env file with:

```powershell
cd frontend
"VITE_API_URL=http://localhost:8080/api/v1" | Out-File -Encoding utf8 .env
npm install
npm run dev
```

## Docker

Run both services:

```bash
docker compose up --build
```

Default container ports:

```text
Frontend: http://localhost:3000
Backend:  http://localhost:8080
```

The backend CORS origin can be configured with `CORS_ORIGIN`.

## API Reference

Base URL:

```text
http://localhost:8080/api/v1
```

| Method | Path | Description |
| --- | --- | --- |
| `GET` | `/agents` | List available core agent types. |
| `GET` | `/presets` | List generation presets. |
| `GET` | `/stacks` | List all stacks grouped by category. |
| `GET` | `/stacks/backend` | List backend stacks. |
| `GET` | `/stacks/frontend` | List frontend stacks. |
| `POST` | `/scaffold` | Generate and download the agents ZIP. |

Example generation request:

```json
{
  "preset": "enterprise-fullstack",
  "projectName": "demo",
  "backendStack": "java-spring",
  "frontendStack": "typescript-react"
}
```

Example `curl`:

```bash
curl -X POST http://localhost:8080/api/v1/scaffold \
  -H "Content-Type: application/json" \
  -o demo-agents.zip \
  -d '{
    "preset": "enterprise-fullstack",
    "projectName": "demo",
    "backendStack": "java-spring",
    "frontendStack": "typescript-react"
  }'
```

## Testing

Run backend tests:

```bash
cd backend
./mvnw test
```

Run backend verification, including integration tests and JaCoCo checks:

```bash
cd backend
./mvnw verify
```

Run frontend tests:

```bash
cd frontend
npm test
```

Run frontend linting and build:

```bash
cd frontend
npm run lint
npm run build
```

## Extending AgentScaffold

### Add a Backend Stack

1. Add a value to `BackendStack`.
2. Add the stack to `StackRegistry`.
3. Create matching definition files in `backend/src/main/resources/definitions`.
4. Add or update tests for stack lookup, context resolution, and generation.

### Add a Frontend Stack

1. Add a value to `FrontendStack`.
2. Add the stack to `StackRegistry`.
3. Create matching definition files in `backend/src/main/resources/definitions`.
4. Update the frontend schema and stack UI if the new stack should be selectable.

### Add a Preset

1. Add a value to `GenerationPreset`.
2. Add display metadata in `PresetRegistry`.
3. Map the preset to core agents in `PresetAgentResolver`.
4. Update frontend preset validation and stack requirements.
5. Add tests for resolver behavior and request validation.

### Add an Agent Type

1. Add the agent to `CoreAgentType`.
2. Create a template in `backend/src/main/resources/templates`.
3. Create stack definitions in `backend/src/main/resources/definitions`.
4. Include the agent in one or more presets.
5. Add tests for output filenames, template rendering, and ZIP generation.

## Current Notes
- `customCoreAgents` and `additionalAgents` exist in the backend request DTO as extension points, but are not part of the current frontend generation flow.
- Frontend API calls require `VITE_API_URL` to point at the backend API base URL.
