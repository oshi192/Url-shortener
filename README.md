# URL Shortener & Analytics Service

A full-stack microservices project that lets you generate and redirect short URLs, collect click events in real time, and view analytics in a React dashboard.

---

## Description

- **URL-Shortener Service** (`service-url-shortener`):  
  A Spring Boot service (Java 21, Gradle) that provides:
    - `POST /api/shorten` – idempotent endpoint to shorten any long URL (with optional custom alias).
    - `GET /{alias}` – redirect endpoint (302) that looks up the long URL, emits a click event, and routes the client.

- **Click-Collector Service** (`service-click-collector`):  
  A Spring Boot consumer (Java 21, Gradle) that subscribes to a Kafka “clicks” topic, persists click events (alias, user agent, geo, timestamp) into PostgreSQL, and exposes analytics:
    - `GET /api/metrics/rate` – total clicks in the last 60 seconds.
    - `GET /api/metrics/geo` – click counts per region/IP.
    - `GET /api/metrics/top` – top 10 URLs by click count.

- **Analytics UI** (`ui-analytics`):  
  A React + Vite dashboard that fetches analytics and displays:
    - Real-time click rate chart
    - Geo-distribution heatmap
    - Top URLs table
    - **New**: form to create a short URL and click-through.

- **Infrastructure** (`infra/docker-compose.yml`):  
  Docker Compose stack provisioning:
    - PostgreSQL 15 (master)
    - Redis 7 (cache)
    - Kafka 3.5 + ZooKeeper 3.8
    - `service-url-shortener` on 8080
    - `service-click-collector` on 8081
    - `ui-analytics` containerized (Node build + Nginx)

---

## Tech Stack

| Component              | Technology                  |
| ---------------------- | --------------------------- |
| Services               | Java 21, Spring Boot        |
| Data persistence       | PostgreSQL, Redis           |
| Message bus            | Kafka + ZooKeeper           |
| Metrics & Observability| Prometheus & Grafana (optional) |
| UI                     | React, Vite, pnpm           |
| Containerization       | Docker, Docker Compose      |
| Kubernetes (optional)  | Minikube / GKE              |

---

## Prerequisites

- [Docker Engine & Compose V2](https://docs.docker.com/engine/install/ubuntu/)
- [pnpm](https://pnpm.io/) & Node 20+
- Java 21 & Gradle (wrapper included)
- (Optional for Kubernetes) `kubectl`, Minikube/GKE

---

## Setup & Run (Docker Compose)

1. **Clone the repo**
```bash
git clone <your-repo-url>
cd url-shortener
```
2. Build & start all services
```
cd infra
docker compose up -d --build
```

(Optional) Verify services
```bash
curl http://localhost:8080/actuator/health
curl http://localhost:8081/actuator/health
```



