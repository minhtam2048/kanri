FROM gradle:7-jdk11 AS build

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN ./gradlew shadowJar --no-daemon

FROM openjdk:11
EXPOSE 8080:8080
RUN mkdir /app
RUN mkdir /app/resources
RUN chmod -R 755 /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/kanri.jar
COPY --from=build /home/gradle/src/build/resources/ /app/resources

CMD ["java", "-server", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseContainerSupport", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication", "-jar", "/app/kanri.jar"]
