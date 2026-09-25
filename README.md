# 🎟️ EventHub

**EventHub** is a full-stack event management and ticket booking application built using **React** and **Spring Boot Microservices**.

The application allows users to discover events, search and filter events, view event details, register and log in securely, book events, view their bookings, and cancel bookings.

---

## ✨ Features

### 👤 User Authentication

* User registration
* User login
* JWT-based authentication
* Secure API access
* User profile
* Logout

### 🎫 Event Management

* Browse events
* Search events
* Filter events
* View event details
* Event categories
* Event availability
* Seat management

### 🎟️ Booking Management

* Book an event
* View **My Bookings**
* Cancel bookings
* Booking status management
* Event seat availability updates

### ⚙️ Microservices

* Service discovery using Eureka
* API Gateway
* REST APIs
* Inter-service communication using OpenFeign
* Global exception handling
* Event-driven communication using Apache Kafka

### 🐳 Docker

* Dockerized backend services
* Dockerized React frontend
* Nginx for frontend serving
* Docker Compose configuration

---

## 🏗️ System Architecture

```text
                         ┌─────────────────────┐
                         │   React Frontend    │
                         │     + Nginx         │
                         └──────────┬──────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │     API Gateway     │
                         └──────────┬──────────┘
                                    │
              ┌─────────────────────┼─────────────────────┐
              │                     │                     │
              ▼                     ▼                     ▼
      ┌──────────────┐      ┌──────────────┐      ┌──────────────┐
      │ User Service │      │ Event Service │      │Booking Service│
      │              │      │              │      │              │
      └──────────────┘      └──────┬───────┘      └──────┬───────┘
                                   │                     │
                                   └──────────┬──────────┘
                                              │
                                              ▼
                                      ┌──────────────┐
                                      │    Kafka     │
                                      └──────────────┘

                         ┌─────────────────────┐
                         │    Eureka Server    │
                         │   Service Discovery │
                         └─────────────────────┘
```

---

## 🧩 Microservices

| Service             | Responsibility                                       |
| ------------------- | ---------------------------------------------------- |
| **Eureka Server**   | Service discovery and registration                   |
| **API Gateway**     | Central entry point and request routing              |
| **User Service**    | Registration, login, authentication and user profile |
| **Event Service**   | Event management, search, filtering and availability |
| **Booking Service** | Event booking, cancellation and booking management   |
| **Frontend**        | React-based user interface                           |

---

## 🛠️ Technologies Used

### Frontend

* React
* JavaScript
* HTML5
* CSS3
* Vite
* Nginx

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* REST APIs
* OpenFeign
* JWT

### Microservices & Communication

* Spring Cloud Eureka
* Spring Cloud Gateway
* OpenFeign
* Apache Kafka

### Database

* MySQL

### DevOps & Tools

* Docker
* Docker Compose
* Git
* GitHub
* IntelliJ IDEA

---

## 📁 Project Structure

```text
EventHub/
│
├── api-gateway/
│
├── booking-service/
│
├── eureka-server/
│
├── event-service/
│
├── user-service/
│
├── frontend/
│
├── docker-compose.yml
├── .env.example
├── .gitignore
└── README.md
```

---

## 🔐 Authentication & Security

EventHub uses **JWT-based authentication** to secure protected APIs.

The authentication flow includes:

```text
User
 │
 ▼
Login
 │
 ▼
User Service
 │
 ▼
JWT Token
 │
 ▼
Protected API Requests
 │
 ▼
JWT Authentication Filter
```

Sensitive configuration values are stored using environment variables.

The actual `.env` file is excluded from Git tracking, while `.env.example` is provided as a template.

---

## ⚡ Kafka Communication

EventHub uses **Apache Kafka** for event-driven communication between services.

A booking can trigger asynchronous communication between the Booking Service and Event Service.

```text
User
 │
 ▼
Booking Service
 │
 ▼
Kafka Producer
 │
 ▼
Kafka
 │
 ▼
Kafka Consumer
 │
 ▼
Event Service
 │
 ▼
Update Event Availability
```

This allows services to communicate asynchronously while keeping the microservices loosely coupled.

---

## 🔄 Service Communication

EventHub uses different communication mechanisms depending on the requirement.

### Synchronous Communication

**OpenFeign** is used for communication between microservices when an immediate response is required.

```text
Booking Service
       │
       │ OpenFeign
       ▼
Event Service
```

### Asynchronous Communication

**Apache Kafka** is used for event-driven communication.

```text
Booking Service
       │
       ▼
     Kafka
       │
       ▼
Event Service
```

---

## 🐳 Running the Project with Docker

### Prerequisites

Make sure the following are installed:

* Java
* Node.js
* Docker Desktop
* Git
* MySQL

### Clone the Repository

```bash
git clone https://github.com/Sanika122003/EventHub.git
```

Navigate to the project:

```bash
cd EventHub
```

### Configure Environment Variables

Create your local `.env` file using the provided example:

```text
.env.example → .env
```

Add the required local configuration values to `.env`.

**Do not commit the `.env` file to GitHub.**

### Start the Application

Run:

```bash
docker compose up --build
```

Docker Compose will build and start the configured services.

---

## 🌐 Frontend

The frontend is developed using **React + Vite** and is served using **Nginx** when running through Docker.

The frontend provides:

* Home page
* Event browsing
* Search
* Event filtering
* Event details
* Login
* Registration
* User profile
* My Bookings
* Booking and cancellation

---

## 🎯 Project Objectives

The main objectives of EventHub are to demonstrate practical implementation of:

* Java and Spring Boot
* Microservices architecture
* REST API development
* JWT authentication
* Service discovery
* API Gateway
* Inter-service communication
* OpenFeign
* Event-driven architecture with Kafka
* React frontend development
* Docker containerization
* Git and GitHub

---

## 📌 Key Learning Outcomes

Through EventHub, the project demonstrates practical experience with:

* Designing a microservices-based application
* Building and consuming REST APIs
* Implementing JWT authentication
* Managing service-to-service communication
* Using Eureka for service discovery
* Using Kafka for asynchronous communication
* Building a React frontend
* Containerizing applications using Docker
* Managing source code using Git and GitHub

---

## 👩‍💻 Author

**Sanika Shinde**

GitHub: [Sanika122003](https://github.com/Sanika122003)

---

## 📄 License

This project was developed for educational and portfolio purposes.
