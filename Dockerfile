FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/rocket23-rocket23.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
