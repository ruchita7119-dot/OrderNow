# OrderNow – Food Ordering Microservices Backend

OrderNow is a **microservices-based food ordering backend** built with Java and Spring Boot. The application demonstrates service-to-service communication, JWT authentication, API Gateway routing, Redis caching, and order-payment integration.

## Architecture

```text
                         Client
                           |
                           v
                    API Gateway :8080
                           |
        +------------------+------------------+
        |                  |                  |
        v                  v                  v
 User Service        Hotel Service      Order Service
   :8081                 :8082              :8083
        |                  |                  |
    MongoDB             MySQL + Redis       MySQL
                                              |
                                              v
                                       Payment Service
                                            :8084
                                              |
                                            v
                                            MySQL
```

## Microservices

| Service         | Port | Responsibility                         |
| --------------- | ---: | -------------------------------------- |
| API Gateway     | 8080 | Centralized API routing                |
| User Service    | 8081 | Registration, login, users & roles     |
| Hotel Service   | 8082 | Hotel management, search & caching     |
| Order Service   | 8083 | Order creation & management            |
| Payment Service | 8084 | Payment processing & status management |

## Tech Stack

**Backend**

* Java 21
* Spring Boot 3.5.15
* Spring Security
* JWT
* Spring Cloud Gateway
* Spring Cloud OpenFeign
* Spring Data JPA
* Spring Data MongoDB
* Bean Validation

**Databases & Infrastructure**

* MySQL
* MongoDB
* Redis
* Docker

**Tools**

* Maven
* Git & GitHub
* Postman
* Swagger / OpenAPI
* Spring Boot Actuator

## Key Features

### Authentication & Authorization

* User registration and login
* JWT-based authentication
* BCrypt password encryption
* Role-based authorization

### Hotel Management

* Hotel CRUD operations
* Search and pagination
* Rating and verification filters
* Active/inactive hotel management
* Redis caching for frequently accessed hotel data

### Order Management

* Order creation and retrieval
* Order item and total amount management
* User and hotel validation through OpenFeign
* Order and payment status tracking

### Payment

* Payment creation and retrieval
* Cash on Delivery support
* Payment status management
* Payment ID generation
* Automatic synchronization of payment status with Order Service

### API Gateway

* Centralized entry point for backend services
* Routes requests to individual microservices
* Runs on port `8080`

## Service Communication

OrderNow uses **Spring Cloud OpenFeign** for synchronous communication between services.

```text
Order Service
    |
    +----> User Service
    |
    +----> Hotel Service
    |
    +----> Payment Service

Payment Service
    |
    +----> Order Service
```

## Payment Flow

```text
Create Order
     |
     v
Create Payment
     |
     v
Cash on Delivery
     |
     v
Payment SUCCESS
     |
     v
Update Order Payment Status
```

## Data Storage

The project follows a **database-per-service** approach.

```text
User Service     → MongoDB
Hotel Service    → MySQL + Redis
Order Service    → MySQL
Payment Service  → MySQL
```

## API Documentation

Swagger UI is available for each service:

```text
User Service     → http://localhost:8081/swagger-ui.html
Hotel Service    → http://localhost:8082/swagger-ui.html
Order Service    → http://localhost:8083/swagger-ui.html
Payment Service  → http://localhost:8084/swagger-ui.html
```

## Running Locally

### Prerequisites

* Java 21
* Maven
* MySQL
* MongoDB
* Redis
* Docker (optional)

### Start Services

Start the infrastructure first:

```text
MySQL
MongoDB
Redis
```

Then start the services:

```text
User Service       → 8081
Hotel Service      → 8082
Order Service      → 8083
Payment Service    → 8084
API Gateway        → 8080
```

From each service directory:

```bash
mvn spring-boot:run
```

## Testing

The APIs can be tested using **Postman** or **Swagger UI**.

Recommended flow:

```text
Register/Login
      ↓
Create/Verify Hotel
      ↓
Create Order
      ↓
Create Payment
      ↓
Verify Payment Status
      ↓
Verify Order Status
```

## Project Structure

```text
OrderNow/
├── userservice/
├── hotelservice/
├── orderservice/
├── paymentservice/
├── apigateway/
└── README.md
```

## Future Enhancements

* Resilience4J Circuit Breaker
* Kafka-based asynchronous communication
* Service discovery
* Centralized configuration
* CI/CD pipeline
* Cloud deployment
* UPI payment integration
* Frontend application

## Project Status

**Core backend implementation completed.**

The current implementation includes User, Hotel, Order, Payment, and API Gateway services with JWT authentication, OpenFeign communication, Redis caching, database persistence, API documentation, and order-payment integration.

## Author

**Ruchita Jerripotula**

Java | Spring Boot | Microservices | Backend Development
