FROM amazoncorretto:11-alpine-jdk
WORKDIR /app
COPY /target/stat-server-0.0.1-SNAPSHOT.jar ./stat-server.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","stat-server.jar"]