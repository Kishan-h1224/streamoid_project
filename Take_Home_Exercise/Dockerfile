FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw package -DskipTests
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/Take_Home_Exercise-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

