# Hospital Management System

Spring Boot API for a full-stack hospital operations app (Admin, Doctor, Patient). The Vue UI lives in the companion frontend repo.

## Overview

Staff and patients share a Vue UI and this Spring Boot API. JWT login picks a role. Redis holds remaining appointment slots so a time period cannot be oversold. MySQL stores clinical and admin data. The API has no servlet context-path (routes are `/patient/login`, not `/hospital/patient/login`). The frontend Nginx / Vue proxy strips `/hospital` before forwarding here.

Demo data uses New Zealand-style names, mobiles, NHI / MCNZ numbers, and NZD. Seed file: `hospital_manager.sql`.

## Features

**Shared**

- JWT login for Admin, Doctor, and Patient
- English UI (Element UI)

**Admin**

- Doctor and patient CRUD, Excel import/export (EasyPOI)
- Appointment list, bed list, drug and exam catalogues
- Doctor roster by department and date
- ECharts dashboards: age bands, gender mix, department volume, recent booking counts

**Doctor**

- Today's appointments and completed visits
- Diagnosis, drugs, lab checks, extra charges
- Admit / discharge beds
- PDF visit report (iText)

**Patient**

- Register, book a slot by department and date
- View bookings, ratings, and bed stay
- Pay outstanding fees

**API guards**

- Field length and request-size limits on write paths
- Redis decrement of slot counts on `addOrder`

## Tech Stack

| Area | Stack |
| ---- | ----- |
| Frontend | Vue 2, Vue Router, Element UI, Axios, ECharts, Sass |
| Backend | Java 8, Spring Boot 2.2, MyBatis-Plus, MyBatis XML mappers |
| Auth | JWT (`java-jwt`) |
| Data | MySQL 8, Redis (Jedis) |
| Files | iText PDF, EasyPOI Excel |
| Run locally | Vue CLI 4 (`npm run serve` on 8082), Spring Boot on 9092 |
| Deploy | Docker, Docker Compose, Nginx, GitHub Actions, Hostinger VPS |

## Getting Started

### Requirements

- Node.js 18+ (for the Vue app)
- JDK 8
- Maven 3.6+
- MySQL 8
- Redis 6+

### 1. Database

```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS hospital_manager CHARACTER SET utf8mb4;"
mysql -u root -p hospital_manager < hospital_manager.sql
```

Default Spring datasource is `root` / `root` on `127.0.0.1:3306`. Override with `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`.

### 2. Redis

Start Redis on `127.0.0.1:6379`. Override with `REDIS_HOST` and `REDIS_PORT`.

### 3. Backend

```bash
mvn spring-boot:run
```

API: `http://localhost:9092` (example: `POST /patient/login`).

### 4. Frontend

In the Vue project:

```bash
npm install --registry=https://registry.npmjs.org/
npm run serve
```

UI: `http://localhost:8082`. Vue CLI proxies `/hospital` to port 9092 and strips the prefix.

### Demo accounts

Password for all of these is `123456`.

| Role | Account ID | Name |
| ---- | ---------- | ---- |
| Admin | `22401717` | Sarah Mitchell |
| Doctor | `1000` | James Wilson |
| Patient | `2000` | Liam Thompson |

## Testing

There is no automated unit or e2e suite. Smoke-test locally:

1. Log in as each role with the demo accounts.
2. Patient: book a slot (confirm remaining count drops; booking again when 0 should fail).
3. Doctor: open today's list, add diagnosis / drugs / checks, export PDF.
4. Admin: open dashboards, edit a doctor, import/export Excel if needed.
5. Confirm `/hospital/patient/login` from the browser reaches `/patient/login` on 9092.


## Live Demo

Hosted on a Hostinger VPS (Docker Compose + GitHub Actions).

Replace this line with the public URL when you publish it, for example:

https://hospital.bowang.tech/
