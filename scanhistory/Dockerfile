FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/scanhistory-main.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.redis.host=192.168.0.24","-Dspring.redis.port=6379","-Dspring.redis.password=nhncloud","./app.jar"]