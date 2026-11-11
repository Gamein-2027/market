# Gamein 2026 - Market Microservice

[![Java](https://img.shields.io/badge/Java-21%20LTS-orange.svg)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Author](https://img.shields.io/badge/Author-ARASH%20ESLAMI-informational)](https://github.com/arashae)

The **market** microservice is the high-frequency trading, supply chain, and production engine for the Gamein 2026 competition.

---

## 📌 Features

- **Orderbook Engine**: Real-time marketplace buy and sell order placement, trade matching, and offers.
- **Factory Lines & Manufacturing**: Managing production lines, manufacturing intermediate/final goods, and assembly.
- **Warehouse Storage**: Storage capacity monitoring, product deposits, and warehouse upgrades.
- **Stateless Zero-Roundtrip Auth**: Validates JWT claims locally in memory in `< 0.05ms` without blocking HTTP calls.
- **Concurrency**: Powered by **Java 21 Virtual Threads (Loom)** and **HikariCP** connection pooling.

For complete developer specifications, refer to [**`AGENTS.md`**](./AGENTS.md).

---

## 🚀 Build & Run

```bash
# Compile with Java 21
./gradlew compileJava

# Build executable fat JAR
./gradlew bootJar

# Run service locally
./gradlew bootRun
```

Default port: `8080` (context path `/service`).
