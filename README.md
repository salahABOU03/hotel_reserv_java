# 🏨 Hotel Reservation API

> A Java-based hotel reservation management system with booking engine, room inventory, and customer management.

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)

---

## ✨ Features

- 🛏️ **Room Management** — Track room types, availability, and pricing
- 📅 **Booking Engine** — Create, modify, and cancel reservations
- 👤 **Customer Profiles** — Manage guest information and booking history
- 💰 **Billing** — Generate invoices and track payments
- 📡 **REST API** — Clean endpoints for frontend integration

## 🚀 Quick Start

```bash
# Clone
git clone https://github.com/salahABOU03/hotel-reservation-api.git

# Configure database
# Update src/main/resources/application.properties

# Build & Run
mvn spring-boot:run
```

## 🏗️ Architecture

```
src/main/java/
├── controller/     # REST endpoints
├── model/          # JPA entities (Room, Reservation, Customer)
├── repository/     # Spring Data repositories
├── service/        # Business logic layer
└── dto/            # Data transfer objects
```

## 📄 License

© 2025 Salah Eddine Abouelkemhe
