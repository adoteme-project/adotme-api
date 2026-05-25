# Adotme API

A REST API for an animal adoption platform that connects shelters (ONGs) with potential adopters. Shelters publish dogs and cats available for adoption, manage adoption applications, and track lost animals. Adopters browse available animals, favorite them, and submit adoption forms.

Built with **Spring Boot 3.3** and **Java 17**, backed by **MySQL 8** and **Cloudinary** for image storage.

---

## Stack

- **Language / framework** — Java 17 + Spring Boot 3.3.2
- **Build** — Maven (wrapper included; no global Maven install required)
- **Database** — MySQL 8 (JPA / Hibernate, schema auto-managed via `ddl-auto=update`)
- **Authentication** — JWT (stateless) via `spring-security`
- **Image storage** — Cloudinary
- **Email** — Spring Mail (used for password reset flow)
- **External APIs** — TheCatAPI (breed metadata for cats)
- **API docs** — Springdoc OpenAPI (Swagger UI auto-generated)
- **Containers** — Docker + Docker Compose (app + MySQL)

---

## Features

- **Adopter management** — registration with multipart photo upload, adoption form (preferences, environment, experience), favorites (animals + ONGs), full profile lifecycle
- **Shelter (ONG) management** — registration, user accounts under each ONG, dashboard with adoption metrics
- **Animal catalog** — dogs and cats with personality traits, photos, breed info (cats enriched from TheCatAPI), CSV import/export
- **Lost animals** — separate flow for community-reported lost pets
- **Adoption workflow** — application submission, status tracking, ONG-side review dashboard
- **Authentication & access control** — JWT-based, role separation between adopter and ONG users
- **Password reset** — token via email
- **Image uploads** — Cloudinary integration with multipart endpoints (up to 10 MB per file)

---

## Quick start

### Prerequisites

- **Docker Desktop** (for the Docker Compose path — recommended)
- **Java 17** + **Git** (only needed for the local Maven path)

### 1. Clone the repo

```bash
git clone https://github.com/adoteme-project/adotme-api.git
cd adotme-api
```

### 2. Set up environment variables

```bash
cp .env.example .env
# Open .env and fill in the values (see "Credentials" below)
```

### 3a. Run with Docker Compose (one command)

```bash
docker compose up -d --build
```

This builds the app image, starts MySQL with a healthcheck, waits for it to be ready, then starts the API on **http://localhost:8080**.

Logs:

```bash
docker compose logs -f app
```

Shutdown:

```bash
docker compose down                  # stops containers
docker compose down -v               # also removes the MySQL volume (fresh DB next start)
```

### 3b. Or run with Maven locally

If you prefer to run the app outside Docker (faster iteration, IDE debugging):

```bash
# Start only MySQL via docker-compose:
docker compose up -d db

# Load environment variables and run the app:
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

Windows PowerShell (set env vars first):

```powershell
Get-Content .env | Where-Object { $_ -match '^\w+=' } | ForEach-Object {
    $name, $value = $_ -split '=', 2
    Set-Item "env:$name" $value
}
.\mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

### 4. Verify it's running

```bash
curl http://localhost:8080/swagger-ui.html
# Opens Swagger UI with the full API spec
```

---

## API documentation

Once the app is running, interactive API docs are available at:

- **Swagger UI** — http://localhost:8080/swagger-ui.html
- **OpenAPI JSON** — http://localhost:8080/v3/api-docs

### Main endpoints

| Resource | Base path | Highlights |
|---|---|---|
| Authentication | `/api/autenticacao` | Login, JWT issuance |
| Adopters | `/api/adotantes` | CRUD + adoption form + favorites + photo upload |
| Animals | `/api/animais` | Dogs/cats CRUD, personality, CSV import/export |
| Lost animals | `/api/animais-perdidos` | Community-reported lost pet flow |
| ONGs (shelters) | `/api/ongs` | Shelter registration and management |
| ONG users | `/api/ong-users` | User accounts under an ONG |
| Adoption applications | `/api/adocoes` | Application lifecycle + dashboard metrics |
| Adoption form | `/api/formularios` | Adopter profile (preferences, environment) |
| Adoption requests | `/api/requisicoes` | Request tracking |
| Addresses | `/api/enderecos` | Address normalization |
| Image upload | `/api/cloudinary` | Cloudinary proxy for photos |
| Password reset | `/api/password-reset` | Token-based reset via email |

---

## Project structure

```
src/main/java/com/example/adpotme_api/
├── AdpotmeApiApplication.java   # entry point
├── controller/                  # REST endpoints (12 controllers)
├── service/                     # business logic
├── repository/                  # JPA repositories
├── entity/                      # JPA entities (DB-mapped)
├── dto/                         # request/response DTOs
├── mapper/                      # entity ↔ DTO mapping
├── security/                    # JWT config, Spring Security filter chain
├── integration/                 # external APIs (TheCatAPI, Cloudinary)
├── adapter/                     # adapters for external services
├── exception/                   # custom exceptions + global handler
└── util/                        # helpers (CSV import/export, etc)
```

---

## Technical decisions

- **Spring Boot 3.3 + Java 17** — both current LTS versions; Java 17 enables Records and Pattern Matching, which simplify DTO definitions and exhaustive switches in mappers.
- **MySQL over Postgres** — chosen for ecosystem familiarity in the team and broad managed-MySQL availability in Brazilian hosting. For production at scale I'd consider Postgres for native JSON support and richer extensions (PostGIS for geo queries on animal location, full-text search).
- **JPA / Hibernate with `ddl-auto=update`** — fast feedback loop during development. For production, this should be replaced by Flyway/Liquibase migrations to keep schema changes reviewable and reversible.
- **Layered architecture (controller → service → repository)** — explicit boundaries; controllers stay thin, business logic concentrated in services, repositories return entities (no leaky abstractions back to the web layer).
- **DTO + Mapper layer** — entities never cross the HTTP boundary directly. Prevents accidental exposure of internal fields and enables versioning request/response shapes independently.
- **JWT (stateless) authentication** — no session affinity required; horizontal scaling is straightforward. Secret loaded from env var.
- **Cloudinary for images** — offloads image storage and transformations; the API only stores Cloudinary URLs in MySQL. Avoids putting binary blobs in the relational store.
- **TheCatAPI for cat breed metadata** — third-party enrichment isolated in `integration/`; if the API becomes unavailable, only cat-creation paths degrade (other flows are unaffected).
- **Docker Compose for local dev** — one command starts MySQL + the app; new contributors don't need to install MySQL on the host.
- **Environment-based configuration** — `application-local.properties` (dev) and `application-prod.properties` (production); both read sensitive values from environment variables, never from committed files.

---

## Tests

Test files under `src/test/java/`:

- `AdpotmeApiApplicationTests.java` — context load smoke test
- `controller/` — controller-layer tests for adopters, animals, ONGs, authentication, adoptions, lost animals, addresses
- `service/AdotanteServiceTest.java` — service-layer test for the adopter use case

Run with:

```bash
./mvnw test
```

---

## Security note

This repository follows a strict no-secrets-in-Git policy:

- `application-local.properties` and `application-prod.properties` only reference environment variables (no hard-coded values).
- Sensitive values live in `.env` (gitignored). A documented template is in `.env.example`.
- `.gitignore` covers `.env`, `.env.*.local`, and `*.local.properties`.

If you fork or contribute, **never commit a populated `.env`**. Treat any credential exposure as a P0 incident — rotate immediately.

---

## License

See [LICENSE](LICENSE).
