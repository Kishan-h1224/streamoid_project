Product Catalog Service

This project is a backend service for a take-home exercise. It allows online sellers to upload their product catalog from a CSV file, validates the data, stores it in a database, and provides REST APIs to list and search the products.

Features

CSV Upload: Upload a product catalog via a single CSV file.

Data Validation: Each product row is validated against a set of rules (e.g., required fields, price logic, quantity).

Database Storage: Valid products are persisted to a MySQL database.

Paginated Product Listing: Retrieve a list of all stored products with support for pagination.

Advanced Search: Dynamically filter products by brand, color, and price range.

Dockerized Solution: The entire application stack (Spring Boot App + MySQL DB) can be run easily with a single Docker command.

Tech Stack

Backend: Java 21, Spring Boot 3

Database: MySQL 8.0

Build Tool: Maven

Testing: JUnit 5, Mockito

Containerization: Docker, Docker Compose

Getting Started

You can run this application in one of two ways: using Docker (recommended for ease of use) or by setting it up locally.

Prerequisites

For Docker Setup:

Docker and Docker Compose installed.

For Local Setup:

JDK 21 or later installed.

Maven installed.

A running instance of MySQL 8.0.

Setup and Running the Application

Method 1: Running with Docker (Recommended)

This is the simplest way to get the entire application stack running.

Clone the repository:

git clone <your-repository-url>
cd <repository-directory>


Build and Start the Containers:
Run the following command from the root of the project:

docker-compose up --build


This command will:

Build the Spring Boot application's Docker image using the Dockerfile.

Start a MySQL container.

Start your application container, automatically connecting it to the database.

The application will be available at http://localhost:8080.

Stopping the Application:
Press Ctrl + C in the terminal, then run the following command to remove the containers:

docker-compose down


Method 2: Running Locally

Clone the repository:

git clone <your-repository-url>
cd <repository-directory>


Setup the Database:

Ensure your local MySQL server is running.

Create a database named HomeExercise_db.

CREATE DATABASE HomeExercise_db;


Configure the Application:

Open the src/main/resources/application.properties file.

Update the spring.datasource.username and spring.datasource.password properties with your local MySQL credentials.

Run the Application:
Use the Maven wrapper to start the application:

./mvnw spring-boot:run


The application will be available at http://localhost:8080.

API Documentation

The base URL for all APIs is http://localhost:8080/api.

1. Upload CSV File

Uploads a CSV file of products, validates them, and stores the valid ones.

Endpoint: POST /api/upload

Request Type: multipart/form-data

Sample Request (curl):

curl -X POST -F "file=@/path/to/your/products.csv" http://localhost:8080/api/upload


Sample Success Response (200 OK):

{
"stored": 20,
"failed": 0
}


2. List All Products

Returns a paginated list of all products stored in the database.

Endpoint: GET /api/products

Query Parameters:

page (optional, default: 0): The page number to retrieve.

limit (optional, default: 10): The number of items per page.

Sample Request (curl):

curl -X GET "http://localhost:8080/api/products?page=0&limit=5"


Sample Success Response (200 OK):

{
"content": [
{
"sku": "TSHIRT-RED-001",
"name": "Classic Cotton T-Shirt",
"brand": "Stream Threads",
"color": "Red",
"size": "M",
"mrp": 799.00,
"price": 499.00,
"quantity": 20
}
],
"pageable": {
"pageNumber": 0,
"pageSize": 5,
"sort": {
"empty": true,
"sorted": false,
"unsorted": true
},
"offset": 0,
"paged": true,
"unpaged": false
},
"totalPages": 4,
"totalElements": 20,
"last": false,
"size": 5,
"number": 0,
"sort": {
"empty": true,
"sorted": false,
"unsorted": true
},
"numberOfElements": 5,
"first": true,
"empty": false
}


3. Search Products

Returns a paginated list of products that match the specified filter criteria. All filters are optional.

Endpoint: GET /api/products/search

Query Parameters:

brand (optional): Filter by brand name (e.g., DenimWorks).

color (optional): Filter by color (e.g., Blue).

minPrice (optional): Filter for products with a price greater than or equal to this value.

maxPrice (optional): Filter for products with a price less than or equal to this value.

page (optional, default: 0).

limit (optional, default: 10).

Sample Request (curl):

curl -X GET "http://localhost:8080/api/products/search?brand=BloomWear&maxPrice=2000"


Sample Success Response (200 OK):
The response structure is the same as the "List All Products" endpoint, but the content array will only contain products matching the search filters.

Running Unit Tests

To run the full suite of unit tests, execute the following command from the project's root directory:

./mvnw test


This will run tests for CSV parsing, validation, and search filter logic.