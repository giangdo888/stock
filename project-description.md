# Logistics Management API

## Overview

A backend REST API for managing warehouse logistics — warehouses, products, and
shipments — built to demonstrate production-grade Java/Spring Boot engineering
practices: clean layered architecture, caching for performance, and
load-tested, measurable results rather than unverified claims.

The domain was chosen deliberately: several target job postings call out
logistics/automated logistics experience as a plus, so the project doubles as
both a technical showcase and a domain-relevant one.

## Problem it models

A company operates multiple warehouses. Each warehouse stores products.
Shipments move specific quantities of products out of a warehouse to
fulfill orders. This gives a realistic relational structure — one-to-many
(Warehouse → Products) and many-to-many with extra attributes
(Shipment ↔ Products, via a join entity carrying quantity) — instead of a
flat, single-table CRUD toy app.

## Core entities

- **Warehouse** — id, name, location; owns many Products
- **Product** — id, SKU, name, price, quantity on hand, belongs to a Warehouse
- **Shipment** — id, status, destination, created date; contains many
  ShipmentItems
- **ShipmentItem** — join entity linking Shipment ↔ Product with a quantity,
  modeling the many-to-many relationship properly instead of flattening it

## Architecture

Standard N-layer structure (Controller → Service → Repository → Database),
the same logical separation as a typical ASP.NET/EF Core 3-tier app, just
expressed with Spring/JPA idioms:

- **Controller layer** — REST endpoints, request/response DTOs (never
  exposing entities directly over the wire), input validation
- **Service layer** — business logic, transaction boundaries, caching
  annotations
- **Repository layer** — Spring Data JPA interfaces over PostgreSQL
- **Cross-cutting** — global exception handling (`@ControllerAdvice`),
  OpenAPI/Swagger documentation, structured logging

## Performance story

Rather than an unverified "handles X requests/sec" claim, the project
includes an actual measurement:

1. Identify read-heavy endpoints (e.g. fetching a warehouse's shipment
   history, product lookups)
2. Baseline load test with **JMeter** against the uncached endpoints
3. Add a **Redis** caching layer (`@Cacheable`/`@CacheEvict`) on those
   endpoints, with deliberate TTL and invalidation-on-write logic
4. Re-run the identical JMeter test plan
5. Publish both results (`.jmx` test plan + result summaries) in the repo, so
   the throughput/latency improvement is reproducible and defensible in an
   interview — not a marketing number

## Testing

Unit tests on the service layer using **JUnit 5** and **Mockito**
(repository mocked, business logic verified in isolation) — matching the
"experience in unit testing: JUnit, Mockito" requirement that appears across
nearly every target job description.

## Deployment

Containerized locally with **Docker Compose** (app + PostgreSQL + Redis) for
development, and deployed live to **Azure App Service**, backed by
**Azure Database for PostgreSQL** and **Azure Cache for Redis** — giving a
working public URL to share with recruiters/interviewers, not just a
repository link.

## Why this project, for this candidate

The author has 3+ years of professional C++ engineering experience and is
transferring that foundation (OOP, data structures, algorithms, git,
testing discipline, system design instincts) into the Java/Spring
ecosystem. This project is the concrete evidence of that transfer: it
applies the same engineering judgment (measure before claiming, cache
deliberately not reflexively, model relationships properly, layer
responsibilities cleanly) in a new language and framework, rather than
demonstrating years of Java-specific idiom fluency the author doesn't yet
have. It's framed honestly as a fast, focused bridge project — not as proof
of 3 years of Java experience.

## Tech stack summary

| Layer | Technology |
|---|---|
| Language | Java 17/21 |
| Framework | Spring Boot 3.x (Spring Web, Spring Data JPA, Validation) |
| Database | PostgreSQL |
| Caching | Redis (Spring Cache abstraction) |
| Testing | JUnit 5, Mockito |
| Load testing | Apache JMeter |
| Build | Maven |
| Containerization | Docker, Docker Compose |
| Deployment | Azure App Service, Azure Database for PostgreSQL, Azure Cache for Redis |
| Docs | OpenAPI/Swagger |
