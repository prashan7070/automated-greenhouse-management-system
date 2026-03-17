# Automated Greenhouse Management System (AGMS)

## 📌 Project Overview
The **Automated Greenhouse Management System (AGMS)** is a high-precision, cloud-native platform built using a **Microservices Architecture**. It leverages live IoT telemetry data to monitor greenhouse conditions and automates climate control actions (like switching fans or heaters) through a custom rule engine.

---

## 🛠 Tech Stack
*   **Java 17** & **Spring Boot 3.4.3**
*   **Spring Cloud Infrastructure:** Eureka (Discovery), Config Server (Centralized Properties), API Gateway (Routing).
*   **Security:** JWT (JSON Web Token) implementation at the Gateway level.
*   **Databases:** Polyglot Persistence using **MySQL** (Separate DBs for Auth, Zone, Automation, and Crop).
*   **Communication:** Synchronous inter-service communication using **OpenFeign**.
*   **Scheduling:** Spring Scheduling for automated IoT data polling every 10 seconds.

---

## 🏗 System Architecture & Ports
| Service Name | Port | Description |
| :--- | :--- | :--- |
| **Service Registry** | 8761 | Netflix Eureka server for service discovery. |
| **Config Server** | 8888 | Manages centralized YAML properties via a Git repository. |
| **API Gateway** | 8080 | Single entry point with JWT validation & request routing. |
| **Auth Service** | 8085 | Manages user registration and JWT token generation. |
| **Zone Service** | 8081 | Manages greenhouse zones and integrates with External IoT API. |
| **Sensor Service** | 8082 | Polls live telemetry from external hardware simulation. |
| **Automation Service** | 8083 | The "Brain" - triggers actions based on temperature rules. |
| **Crop Service** | 8084 | Tracks plant growth lifecycle (Seedling, Vegetative, Harvested). |

---

## 🚀 Getting Started (Startup Order)
To run the system correctly, start the services in this specific order:

1.  **Service Registry:** Wait until the dashboard is available at `localhost:8761`.
2.  **Config Server:** Ensures all domain services can fetch their properties.
3.  **Auth Service:** Required for generating security tokens.
4.  **API Gateway:** Routes must be initialized.
5.  **Zone, Sensor, Automation, & Crop Services:** Start the business logic layer.

---

## 💡 Key Features Implemented
*   **JWT Security:** Every request to internal domain services is protected by the API Gateway. Unauthorized requests are rejected with a 401/403 status.
*   **External IoT Integration:** Automatically registers a new digital device in the remote IoT server (104.211.95.241) whenever a new Greenhouse Zone is created.
*   **Rule Engine:** Processes live temperature data. If the temperature exceeds the zone's `maxTemp`, it logs `TURN_FAN_ON`. If it falls below `minTemp`, it logs `TURN_HEATER_ON`.
*   **Interface-based Design:** All services use a decoupled architecture (Interface + Impl) with DTO mapping to follow clean coding standards.

---

## 📂 Submission Attachments
*   **Postman Collection:** Found in the root directory as `AGMS_Postman_Collection.json`.
*   **Eureka Dashboard:** Screenshot available in the `docs/` folder.
*   **Configuration:** All YAML files are managed via the `config-repo` folder.

---