FROM amazoncorretto:11-alpine-jdk
WORKDIR /app
COPY /target/main-service-0.0.1-SNAPSHOT.jar ./main-server.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","main-server.jar"]