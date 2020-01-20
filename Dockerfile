FROM gradle:6.0.1-jdk13 AS build 
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle build --no-daemon

FROM openjdk:13

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/findDate.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]




