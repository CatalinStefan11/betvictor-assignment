FROM gradle:7.2.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /home/gradle/src/build/libs/repository-1.0-SNAPSHOT.jar repository.jar
ENTRYPOINT ["java", "-jar", "repository.jar"]