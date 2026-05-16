# Investment Portfolio & Asset Allocation Service

A Spring Boot–based backend service that builds, manages, allocates, and rebalances an investment portfolio based on user risk profile and investment amount. This project simulates real-world portfolio management logic used in fintech and wealth-management platforms.

---

## 📌 Project Overview

The **Investment Portfolio & Asset Allocation Service** allows:

* Creating a user portfolio with an investment amount
* Allocating assets based on risk profile
* Tracking current allocation
* Rebalancing the portfolio when it deviates from the target allocation
* Generating actionable rebalance instructions (BUY / SELL)

This project is designed to demonstrate **core backend engineering skills** using Java and Spring Boot, along with **business logic modeling**.

---

## 🛠️ Tech Stack

* **Java 17**
* **Spring Boot**
* **Spring Web (REST APIs)**
* **Spring Data JPA**
* **PostgreSQL**
* **JUnit 5** (unit testing)
* **Spring Security - JWT**
* **Swagger / OpenAPI**
* **Maven**

---
## 🚀 Key Features

- User portfolio creation and management
- Asset allocation based on **risk profile**
- Automatic **rebalancing engine**
- Supports multiple asset classes:
  - Equity
  - Debt
  - Gold
  - Cash
- Buy / Sell action generation
- Secure APIs using **JWT Authentication**
- RESTful design with clean layered architecture
- Swagger API documentation

-
## 🔐 Security

- JWT-based authentication
- Stateless REST APIs
- Secured endpoints
- Custom filters:
  - `JwtFilter`
  - `JwtUtil`

## 🧩 Core Modules & Responsibilities

### 1️⃣ Asset Master Module

Stores static asset definitions used across the system.

Example assets:

* EQUITY
* DEBT
* GOLD
* CASH

This data is inserted once and reused for allocation and rebalancing.

---

### 2️⃣ Portfolio Module

Represents a user’s investment portfolio.

**Entities:**

* `Portfolio`
* `PortfolioAsset`

Each portfolio contains multiple assets with allocated amounts.

---

### 3️⃣ Allocation Engine

Responsible for distributing investment amounts based on **risk profile**.

#### Supported Risk Profiles

| Risk Profile | Equity | Debt | Gold | Cash |
| ------------ | ------ | ---- | ---- | ---- |
| LOW          | 30%    | 50%  | 10%  | 10%  |
| MEDIUM       | 50%    | 30%  | 10%  | 10%  |
| HIGH         | 70%    | 15%  | 10%  | 5%   |

#### Example

Input:

```text
Risk Profile: HIGH
Investment Amount: 100,000
```

Output Allocation:

```text
EQUITY → 70,000
DEBT   → 15,000
GOLD   → 10,000
CASH   → 5,000
```

---

### 4️⃣ Rebalancing Engine

Compares **current portfolio allocation** with **target allocation** and generates rebalance actions.

#### Responsibilities

* Identify over-allocated assets → SELL
* Identify under-allocated assets → BUY
* Ignore assets already balanced

#### Sample Input

Current Allocation:

```text
EQUITY = 50,000
DEBT   = 40,000
GOLD   = 10,000
CASH   = 0
Total  = 100,000
```

Target Allocation (HIGH Risk):

```text
EQUITY = 70,000
DEBT   = 15,000
GOLD   = 10,000
CASH   = 5,000
```

#### Output (Rebalance Instructions)

```json
[
  "SELL DEBT worth 25000",
  "BUY EQUITY worth 20000",
  "BUY CASH worth 5000"
]
```

---

## 🔄 End-to-End Flow

1. User creates a portfolio with investment amount
2. AllocationService calculates target allocation
3. Portfolio assets are stored in DB
4. RebalanceService compares current vs target allocation
5. System generates BUY / SELL actions

---

## 🧪 Testing Strategy

### Unit Testing

* Allocation logic tested independently
* Rebalancing logic tested using in-memory maps

### Manual Testing

* Insert asset master data
* Create portfolio via REST API
* Trigger rebalance API
* Verify generated actions


### 📂 Package Structure
```
 com.investment_portfolio_service
├── InvestmentPortfolioServiceApplication.java
├── config
│   ├── SecurityConfig.java
│   ├── JwtFilter.java
│   └── SwaggerConfig.java
├── auth
│   ├── AuthController.java
│   ├── AuthService.java
│   ├── AuthRequest.java
│   └── AuthResponse.java
├── user
│   ├── User.java
│   ├── UserRepository.java
│   └── UserService.java
├── asset
│   ├── Asset.java
│   ├── AssetRepository.java
│   ├── AssetService.java
│   └── AssetController.java
├── portfolio
│   ├── Portfolio.java
│   ├── PortfolioAsset.java
│   ├── PortfolioRepository.java
│   ├── PortfolioAssetRepository.java
│   ├── PortfolioService.java
│   └── PortfolioController.java
├── allocation
│   └── AllocationService.java
├── rebalance
│   └── RebalanceService.java
|   └── RebalanceController.java
├── exception
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
└── util
    └── JwtUtil.java

```

## 📘 Sample API Endpoints

### Create Portfolio

```http
POST /api/portfolios
```

### Get Portfolio Allocation

```http
GET /api/portfolios/{id}
```

### Rebalance Portfolio

```http
POST /api/portfolios/{id}/rebalance

```
---

## 🎯 What This Project Demonstrates

* Real-world backend design
* Clean separation of business logic
* Financial domain modeling
* Spring Boot best practices
* Rebalancing algorithm design

---

## 👨‍💻 Author

**Saravanakumar M**
Java | Spring Boot Developer

---

⭐ If you’re reviewing this project: this is a backend-focused system emphasizing correctness, clarity, and extensibility.


git remote add origin <https://github.com/saravanakumar-m4/Investment-Portfolio-Asset-Allocation-Service>
