# AGENTS.md

## Repo shape
- Two independent Spring Boot 4.1.1 / Java 17 Maven apps. There is **no root `pom.xml` or aggregator**; run Maven from inside `ProductCatalogService/` or `UserAuthService/`.
- `ProductCatalogService`: product REST API (`/products`), DummyJSON import (`/internal/importAllDummyProducts`), Groq-generated taglines (`/products/{id}/pitch`).
- `UserAuthService`: JWT signup/login (`/api/auth/**`) plus a test-only `/test/findme`.
- Boot 4 names differ from Boot 3: `spring-boot-starter-webmvc`, `spring-boot-restclient`, and `org.springframework.boot.restclient.RestTemplateBuilder`. Do not copy Boot 3 starter/package names.

## Build and test
- `mvn` is not on PATH and no `mvnw` is committed. Use IntelliJ's bundled Maven:
  `C:\Program Files\JetBrains\IntelliJ IDEA 2026.1\plugins\maven\lib\maven3\bin\mvn.cmd`
- From a service directory:
  - quick compile check: `mvn -B -DskipTests compile`
  - all tests: `mvn -B test`
  - one test: `mvn -B -Dtest=ProductCatalogServiceApplicationTests test`
- No lint, formatter, or typecheck plugin is configured; compile/test are the only verification steps.

## Required configuration
- `ProductCatalogService` requires `AUTH_SERVICE_URL` (`auth.service.url`) and `groq_api_key`; either missing stops context startup.
- `UserAuthService` requires `JWT_SECRET` (at least 32 bytes for HS256) and `JWT_EXPIRY_SECONDS`.
- Both services default to port `8080`. Set `--server.port=8081` for one of them when running both.
- Example (PowerShell, after defining `$mvn` as the path above):
  ```powershell
  # UserAuthService
  $env:JWT_SECRET='<32+ byte secret>'; $env:JWT_EXPIRY_SECONDS='3600'
  & $mvn spring-boot:run

  # ProductCatalogService, with auth service on 8080
  $env:AUTH_SERVICE_URL='http://localhost:8080'; $env:groq_api_key='<key>'
  & $mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=8081"
  ```
- `src/test/.../*ApplicationTests` are full `@SpringBootTest` context loads, not unit tests. They need the properties above and a reachable database, so `mvn test` is not a cheap smoke test.

## Data
- `ProductCatalogService` points at an AWS RDS MySQL instance (`easyshopping-db...`); `UserAuthService` points at local MySQL `userauthentication`.
- Both use `spring.jpa.hibernate.ddl-auto=update`, so starting either app can mutate schema. Do not run against a shared DB casually.
- DB credentials are hardcoded in the committed `application.properties`; do not add more secrets there.
- The root `.gitignore` contains `git rm` commands, not ignore patterns, so `target/` is not ignored. Never commit build output.

## Auth flow / current gap
- `TokenValidationFilter` in ProductCatalogService lets every `GET` through unauthenticated. Other methods need `Authorization: Bearer <jwt>`, and `DELETE` additionally needs `ADMIN`.
- That filter calls `GET {auth.service.url}/auth/validateToken`, but UserAuthService exposes only `/api/auth/**` and `/test/findme`. Non-GET product requests currently return `503`; only product GETs work end-to-end.
- Signup always creates a `USER`; there is no admin-creation endpoint. Promote a user directly in the DB to test `DELETE`.
