FROM amazoncorretto:11-alpine-jdk
WORKDIR /app
COPY /target/stat-server-0.0.1-SNAPSHOT.jar ./stats-server.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","stats-server.jar"]