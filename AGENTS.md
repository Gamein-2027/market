# Market Microservice Guide (AGENTS.md)

## 📌 Overview
The **market** service is the core trading and transaction engine of Gamein 2026. It handles real-time player-to-player trades, marketplace buy/sell orders, direct Gamein supply purchases, manufacturing factory lines, warehouse storage, and research projects.

- **Directory**: `F:\projects\Gamein-2022\market`
- **Port**: Default host port `8080` (container port `8081`)
- **Context Path**: `/service`
- **Tech Stack**: Spring Boot 3.3.5, Java 21 (Virtual Threads enabled), Gradle 8.10.2, PostgreSQL 16, Redis, Jakarta Persistence (Hibernate 6), JJWT 0.12.6.

---

## 🏗️ Architecture & High-Concurrency Optimizations
In Gamein 2022, `market` suffered severe latency because every incoming request made a synchronous HTTP call to `dashboard` via `RestUtil.getAuthInfo()` using `new RestTemplate()`.

In Gamein 2026:
1. **Zero-Roundtrip Stateless Auth**: [`InitController.java`](file:///F:/projects/Gamein-2022/market/src/main/java/org/gamein/marketservergamein2022/web/controller/InitController.java) uses local [`JwtUtils.java`](file:///F:/projects/Gamein-2022/market/src/main/java/org/gamein/marketservergamein2022/infrastructure/util/JwtUtils.java) to extract `teamId` and `userId` directly from JWT claims in `< 0.05ms`.
2. **Java 21 Virtual Threads**: Handles thousands of concurrent trading requests without thread starvation.
3. **Connection Pooling**: Replaced C3P0 (max 2000 connections) with lean **HikariCP (pool size 30)**.

---

## 📁 Key Packages & Files

| Package / File | Purpose |
| :--- | :--- |
| [`web.controller.market.OrderController`](file:///F:/projects/Gamein-2022/market/src/main/java/org/gamein/marketservergamein2022/web/controller/market/OrderController.java) | Create buy/sell orders, query marketplace orderbook, trade history, and cancel orders. |
| [`web.controller.market.OfferController`](file:///F:/projects/Gamein-2022/market/src/main/java/org/gamein/marketservergamein2022/web/controller/market/OfferController.java) | Submit trade offers on open orders and complete transactions. |
| [`web.controller.market.TradeController`](file:///F:/projects/Gamein-2022/market/src/main/java/org/gamein/marketservergamein2022/web/controller/market/TradeController.java) | Direct purchasing from Gamein system supply and product catalog. |
| [`web.controller.factory.ManufactureController`](file:///F:/projects/Gamein-2022/market/src/main/java/org/gamein/marketservergamein2022/web/controller/factory/ManufactureController.java) | Factory production lines, starting manufacturing, and assembly lines. |
| [`web.controller.factory.StorageController`](file:///F:/projects/Gamein-2022/market/src/main/java/org/gamein/marketservergamein2022/web/controller/factory/StorageController.java) | Warehouse capacity, product storage queries, and storage upgrades. |
| [`web.controller.factory.ResearchController`](file:///F:/projects/Gamein-2022/market/src/main/java/org/gamein/marketservergamein2022/web/controller/factory/ResearchController.java) | R&D research subject unlocking and research trees. |
| [`infrastructure.util.JwtUtils`](file:///F:/projects/Gamein-2022/market/src/main/java/org/gamein/marketservergamein2022/infrastructure/util/JwtUtils.java) | Cryptographic signature verification and claims extraction. |

---

## ⚙️ Configuration & Endpoints

```properties
server.port=8081
server.servlet.context-path=/service

spring.threads.virtual.enabled=true
spring.jpa.open-in-view=false

# HikariCP Connection Pool
spring.datasource.hikari.maximum-pool-size=30
spring.datasource.hikari.minimum-idle=10
spring.datasource.hikari.connection-timeout=5000
```

---

## 🚀 Build & Run

```bash
# Compile and check Java 21 compatibility
./gradlew compileJava

# Build executable fat JAR
./gradlew bootJar

# Run service locally
./gradlew bootRun
```
Executable JAR is placed at: `build/libs/market-0.0.1-SNAPSHOT.jar`.
