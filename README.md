# URL-Shortener & Analytics Service

A demonstration project showcasing a horizontally scalable URL-shortening service with real-time analytics. It consists of two Spring Boot microservices, a Kafka pipeline, a React dashboard, and full observability via Prometheus & Grafana.

## Tech Stack

- **Language & Frameworks:** Java 21, Spring Boot, React (Vite)
- **Databases & Cache:** PostgreSQL, Redis
- **Message Bus:** Kafka
- **Containerization:** Docker, Kubernetes (Kind/k3d)
- **Observability:** Prometheus, Grafana, Micrometer
- **Build & Package:** Gradle 8, pnpm (or npm/yarn)

## 🛠 Prerequisites

- Java 21 (LTS) (e.g., Temurin or Amazon Corretto)
- Gradle 8.x
- Node.js 20+ (LTS) and pnpm (preferred) or npm/yarn v3+
- Docker Desktop (BuildKit + Compose V2)
- kubectl v1.26+ and Kind or k3d
- Helm 3 (optional)

## Getting Started

### Clone Repository
```bash
git clone https://github.com/your-username/url-shortener.git
cd url-shortener