FROM maven:3.8-openjdk-17 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn -B dependency:go-offline

COPY src src

RUN mvn -B package

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/pocketurl-0.0.1-SNAPSHOT.jar /app/pocketurl-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "pocketurl-0.0.1-SNAPSHOT.jar"]

