FROM openjdk:11-jre-slim
ARG JAR_FILE=build/libs/file-rocket23.jar
COPY ${JAR_FILE} app.jar
ENV REDIS_HOST=localhost
ENV REDIS_PORT=6379
ENV REDIS_PASSWORD=initcloud
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.redis.host=$REDIS_HOST -Dspring.redis.port=$REDIS_PORT -Dspring.redis.password=$REDIS_PASSWORD ./app.jar"]