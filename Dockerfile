FROM maven:3.6.3-openjdk-17.0.9 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn package

FROM openjdk:17.0.9-jdk-slim
EXPOSE 8080

COPY --from=build /app/target/Real-Time-Chat-Application-0.0.1-SNAPSHOT.jar /app/app-jar

CMD ["java", "-jar", "/app/app-jar"]
