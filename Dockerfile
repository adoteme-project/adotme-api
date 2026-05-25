# Multi-stage build keeps the final image small (~250 MB).
#
# Stage 1: build the fat JAR using the official Maven image
# (avoids issues with mvnw line endings on Windows hosts).
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /workspace
COPY pom.xml ./
RUN mvn dependency:go-offline -B
COPY src src
RUN mvn clean package -DskipTests -B

# Stage 2: minimal JRE runtime image — only the final JAR ships.
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
