FROM gradle:8-jdk21 AS build
WORKDIR /app

RUN apt-get update && apt-get install -y nodejs npm

COPY . .
RUN gradle build -Pvaadin.productionMode=true -x test --no-daemon

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/build/libs/*[^plain].jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]