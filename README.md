# Product Catalog Service

This project is a backend service for a take-home exercise. It allows online sellers to upload their product catalog from a CSV file, validates the data, stores it in a database, and provides REST APIs to list and search the products.
## Features

* **CSV Upload**: Upload a product catalog via a single CSV file.
* **Data Validation**: Each product row is validated against a set of rules (e.g., required fields, price logic, quantity).
* **Database Storage**: Valid products are persisted to a MySQL database.
* **Paginated Product Listing**: Retrieve a list of all stored products with support for pagination.
* **Advanced Search**: Dynamically filter products by brand, color, and price range.
* **Dockerized Solution**: The entire application stack (Spring Boot App + MySQL DB) can be run easily with a single Docker command.
## Tech Stack
* **Backend**: Java 21, Spring Boot 3
* **Database**: MySQL 8.0
* **Build Tool**: Maven
* **Testing**: JUnit 5, Mockito
* **Containerization**: Docker, Docker Compose
---

## Getting Started
You can run this application in one of two ways: using Docker (recommended for ease of use) or by setting it up locally.

### Prerequisites

* **For Docker Setup**:
    * Docker and Docker Compose installed.
* **For Local Setup**:
    * JDK 21 or later installed.
    * Maven installed.
    * A running instance of MySQL 8.0.

---
## Setup and Running the Application
### Method 1: Running with Docker (Recommended)

This is the simplest way to get the entire application stack running.

1.  **Clone the repository:**
    ```bash
    git clone <your-repository-url>
    cd <repository-directory>
    ```

2.  **Build and Start the Containers:**
    Run the following command from the root of the project:
    ```bash
    docker-compose up --build
    ```
    This command will:
    * Build the Spring Boot application's Docker image using the `Dockerfile`.
    * Start a MySQL container.
    * Start your application container, automatically connecting it to the database.

    The application will be available at `http://localhost:8080`.

3.  **Stopping the Application:**
    Press `Ctrl + C` in the terminal, then run the following command to remove the containers:
    ```bash
    docker-compose down
    ```

### Method 2: Running Locally

1.  **Clone the repository:**
    ```bash
    git clone <your-repository-url>
    cd <repository-directory>
    ```

2.  **Setup the Database:**
    * Ensure your local MySQL server is running.
    * Create a database named `HomeExercise_db`.
        ```sql
        CREATE DATABASE HomeExercise_db;
        ```

3.  **Configure the Application:**
    * Open the `src/main/resources/application.properties` file.
    * Update the `spring.datasource.username` and `spring.datasource.password` properties with your local MySQL credentials.

4.  **Run the Application:**
    Use the Maven wrapper to start the application:
    ```bash
    ./mvnw spring-boot:run
    ```
    The application will be available at `http://localhost:8080`.

---

## API Documentation

The base URL for all APIs is `http://localhost:8080/api`.

### 1. Upload CSV File

Uploads a CSV file of products, validates them, and stores the valid ones.

* **Endpoint**: `POST /api/upload`
* **Request Type**: `multipart/form-data`

**Sample Request (`curl`):**
```bash
curl -X POST -F "file=@/path/to/your/products.csv" http://localhost:8080/api/upload