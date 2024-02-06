FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/docker-scan.jar
COPY ${JAR_FILE} app.jar
ENV REDIS_HOST_IP=localhost
ENV REDIS_PORT=6379
ENV REDIS_PASSWORD=initcloud
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.redis.host=$REDIS_HOST_IP -Dspring.redis.port=$REDIS_PORT -Dspring.redis.password=$REDIS_PASSWORD ./app.jar"]