# 💳 PayFlow

**PayFlow** is a production-inspired **Spring Boot Microservices** backend project that demonstrates how modern payment systems are designed using **event-driven architecture** and enterprise backend patterns.

The project is built as a hands-on learning journey to understand how real-world financial systems manage authentication, wallet operations, distributed transactions, notifications, and audit logging while maintaining scalability and reliability.

---

# 🚀 Features

* 🔐 JWT Authentication & Authorization
* 🔄 Refresh Token Support
* 👤 User Profile Management
* 💰 Wallet Creation & Balance Management
* 💸 Money Transfer Between Wallets
* 📜 Transaction History
* 📡 Apache Kafka Event-Driven Communication
* 🔁 Choreography Saga Pattern
* 📦 Transactional Outbox Pattern
* ⚡ Redis Cache Integration
* 🔔 Notification Service
* 📝 Audit Logging Service
* 📖 Swagger / OpenAPI Documentation
* 🐳 Docker Support
* ✅ Unit & Integration Testing

---

# 🏗️ Microservices

| Service              | Responsibility                                                  |
| -------------------- | --------------------------------------------------------------- |
| Auth Service         | User registration, login, JWT authentication and refresh tokens |
| User Service         | User profile management                                         |
| Wallet Service       | Wallet creation, balance management, transactions               |
| Saga Service         | Distributed transaction orchestration through Kafka events      |
| Notification Service | Creates notifications after successful transactions             |
| Audit Service        | Stores audit logs for important business events                 |
| Gateway Service      | Single entry point for routing and request filtering            |
| Eureka Service       | Service discovery and registration for all microservices        |
| Config Service       | Centralized configuration management for the application        |

---

# 🛠️ Technology Stack

| Technology        | Purpose                        |
| ----------------- | ------------------------------ |
| Java 21           | Programming Language           |
| Spring Boot       | Microservice Framework         |
| Spring Security   | Authentication & Authorization |
| JWT               | Secure Token Authentication    |
| Spring Data JPA   | Database Access                |
| MySQL             | Persistent Storage             |
| Apache Kafka      | Event Streaming                |
| Redis             | Caching                        |
| Spring Cloud      | Microservices Infrastructure   |
| Docker            | Containerization               |
| Swagger / OpenAPI | API Documentation              |
| JUnit 5           | Unit & Integration Testing     |

---

# 📌 Project Goals

The main objective of PayFlow is to understand and implement enterprise backend concepts including:

* Microservices Architecture
* Event-Driven Communication
* Distributed Transactions
* Saga Pattern
* Transactional Outbox Pattern
* Idempotent Consumers
* Redis Caching
* Secure Authentication using JWT
* REST API Design
* Service Discovery with Eureka
* Centralized Configuration with Config Server
* API Gateway Routing
* Docker-based Deployment
* API Documentation with Swagger

---

# 📚 Learning Objectives

This project focuses on practical implementation of:

* Spring Boot
* Spring Security
* Spring Data JPA
* Spring Cloud Gateway
* Eureka Service Discovery
* Spring Cloud Config
* Kafka
* Redis
* Docker
* Swagger/OpenAPI
* Distributed System Design
* Enterprise Backend Best Practices

---

> **Note**
>
> This project is built as a learning-focused implementation inspired by enterprise backend architectures. The design will continue evolving with Kubernetes deployment, CI/CD pipelines, observability, and cloud-native practices.

---

                     🏛️ System Architecture

```mermaid
flowchart TD

    Client["👤 Client"]

    Auth["🔐 Auth Service"]
    User["👤 User Service"]
    Wallet["💰 Wallet Service"]

    Outbox["📦 Outbox Table"]
    Publisher["📤 Outbox Publisher"]

    Kafka["📡 Apache Kafka"]

    Saga["🎯 Saga Service"]

    Notification["🔔 Notification Service"]

    Audit["📑 Audit Service"]

    WalletDB[("Wallet DB")]
    NotificationDB[("Notification DB")]
    AuditDB[("Audit DB")]

    Client --> Auth
    Client --> User
    Client --> Wallet

    Wallet --> WalletDB

    Wallet --> Outbox

    Outbox --> Publisher

    Publisher --> Kafka

    Kafka --> Saga

    Saga --> Kafka

    Kafka --> Notification

    Notification --> NotificationDB

    Notification --> Kafka

    Kafka --> Audit

    Audit --> AuditDB
```
                ▼                  ▼                  ▼
        Wallet Commands     Notification DB       Audit Database
                │
                ▼
         Wallet Service

## Architecture Overview

PayFlow follows an event-driven microservices architecture.

The client interacts with the Wallet, Auth, and User services through REST APIs.

The Wallet Service persists transactions and writes integration events into an Outbox table.

An Outbox Publisher asynchronously publishes these events to Apache Kafka, ensuring reliable event delivery.

The Saga Service orchestrates distributed transactions by coordinating debit, credit, notification, and compensation workflows.

Notification Service generates user notifications and publishes notification events.

Audit Service consumes business events and stores immutable audit logs for traceability.

---

# 🔄 Saga Pattern Workflow

```mermaid
flowchart TD

    A["👤 Client requests Money Transfer"]

    B["💰 Wallet Service<br/>Create TransferRequestedEvent"]

    C["📡 Kafka"]

    D["🎯 Saga Service"]

    E["💸 Debit Wallet Command"]

    F{"Debit Successful?"}

    G["❌ MoneyDebitFailedEvent"]

    H["💳 Credit Wallet Command"]

    I{"Credit Successful?"}

    J["💵 MoneyCreditedEvent"]

    K["🔔 SendNotificationCommand"]

    L{"Notification Successful?"}

    M["✅ Saga Completed"]

    N["💰 RefundMoneyCommand"]

    O["💵 MoneyRefundedEvent"]

    P["✅ Compensation Completed"]

    A --> B
    B --> C
    C --> D

    D --> E

    E --> F

    F -- No --> G

    F -- Yes --> H

    H --> I

    I -- Yes --> J

    I -- No --> N

    J --> K

    K --> L

    L -- Yes --> M

    N --> O

    O --> P
```

---

      # 📦 Transactional Outbox Pattern

```mermaid
sequenceDiagram

    participant Client
    participant WalletService
    participant WalletDB
    participant OutboxTable
    participant OutboxPublisher
    participant Kafka

    Client->>WalletService: Transfer Money

    WalletService->>WalletDB: Save Transaction
    WalletService->>OutboxTable: Save TransferRequestedEvent

    Note over WalletDB,OutboxTable: Same Database Transaction

    WalletService-->>Client: Transfer Accepted

    loop Every Few Seconds
        OutboxPublisher->>OutboxTable: Fetch PENDING Events
        OutboxPublisher->>Kafka: Publish Event
        OutboxPublisher->>OutboxTable: Mark Event as PUBLISHED
    end
```

---
## Why Transactional Outbox?

Without the Outbox Pattern, the following issue can occur:

1. Wallet transaction is committed.
2. Kafka is unavailable.
3. Event is never published.
4. Other services never receive the event.

This causes data inconsistency across microservices.

To solve this, PayFlow uses the Transactional Outbox Pattern.

- The wallet transaction and integration event are stored in the same database transaction.
- An Outbox Publisher periodically scans the Outbox table.
- Pending events are published to Kafka.
- Successfully published events are marked as `PUBLISHED`.

This guarantees reliable event delivery even if Kafka is temporarily unavailable.

---

# 📡 Kafka Topics Flow

```mermaid
flowchart LR

    Client["👤 Client"]

    Wallet["💰 Wallet Service"]

    Saga["🎯 Saga Service"]

    Notification["🔔 Notification Service"]

    Audit["📝 Audit Service"]

    Kafka[(Kafka)]

    Client --> Wallet

    Wallet -- TransferRequestedEvent --> Kafka

    Kafka --> Saga

    Saga -- DebitMoneyCommand --> Kafka
    Kafka --> Wallet

    Wallet -- MoneyDebitedEvent --> Kafka
    Kafka --> Saga

    Saga -- CreditMoneyCommand --> Kafka
    Kafka --> Wallet

    Wallet -- MoneyCreditedEvent --> Kafka
    Kafka --> Saga

    Saga -- SendNotificationCommand --> Kafka
    Kafka --> Notification

    Notification -- NotificationCreatedEvent --> Kafka
    Kafka --> Audit

    Notification -- NotificationFailedEvent --> Kafka
    Kafka --> Audit

    Saga -- RefundMoneyCommand --> Kafka
    Kafka --> Wallet

    Wallet -- MoneyRefundedEvent --> Kafka
    Kafka --> Saga
```

## Kafka Communication Flow

PayFlow uses Apache Kafka as the communication backbone between microservices.

Instead of calling each service synchronously using REST APIs, services communicate by publishing and consuming events.

This provides:

- Loose coupling
- Better scalability
- Asynchronous processing
- Fault tolerance
- Independent deployments
- Event-driven architecture

Each service owns its own database and communicates only through Kafka events.

---

# 🗄️ Database Design

```mermaid
erDiagram

    AUTH_USERS {
        BIGINT id PK
        STRING username
        STRING email
        STRING password
        STRING role
        DATETIME created_at
    }

    USER_PROFILE {
        BIGINT id PK
        BIGINT user_id
        STRING first_name
        STRING last_name
        STRING phone
        STRING address
    }

    WALLET {
        BIGINT id PK
        BIGINT user_id
        DECIMAL balance
        DATETIME created_at
    }

    TRANSACTION {
        BIGINT id PK
        BIGINT sender_wallet_id
        BIGINT receiver_wallet_id
        DECIMAL amount
        STRING status
        DATETIME created_at
    }

    OUTBOX_EVENT {
        BIGINT id PK
        STRING event_id
        STRING event_type
        STRING payload
        STRING status
        DATETIME created_at
    }

    NOTIFICATION {
        BIGINT id PK
        BIGINT user_id
        STRING message
        STRING type
        BOOLEAN is_read
        DATETIME created_at
    }

    PROCESSED_EVENT {
        BIGINT id PK
        STRING event_id
        DATETIME processed_at
    }

    AUDIT_LOG {
        BIGINT id PK
        STRING event_id
        STRING event_type
        STRING service_name
        STRING description
        DATETIME created_at
    }

    AUTH_USERS ||--|| USER_PROFILE : owns
    AUTH_USERS ||--|| WALLET : owns
    WALLET ||--o{ TRANSACTION : sender
    WALLET ||--o{ TRANSACTION : receiver
```
## Database Ownership

Each microservice owns its own database.

| Service | Database Tables |
|----------|-----------------|
| Auth Service | users |
| User Service | user_profile |
| Wallet Service | wallet, transaction, outbox_event |
| Notification Service | notification, processed_event |
| Audit Service | audit_log |

No service directly accesses another service's database.

All communication happens through Kafka events.
