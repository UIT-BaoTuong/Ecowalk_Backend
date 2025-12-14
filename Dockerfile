FROM openjdk:17-ea-3-slim
WORKDIR /app
COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
