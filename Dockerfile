FROM eclipse-temurin:17-jdk-alpine

VOLUME /tmp

COPY . .

RUN ./mvnw clean package

ENTRYPOINT ["java","-jar","target/coffeenances-api-0.0.1-SNAPSHOT.jar"]
