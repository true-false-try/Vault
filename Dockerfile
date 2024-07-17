FROM openjdk:17
EXPOSE 8095
COPY "/target/Redis-0.0.1-SNAPSHOT.jar" redis-api.jar
CMD [ "java", "-jar", "redis-api.jar" ]