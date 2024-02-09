FROM openjdk:17
EXPOSE 8095
COPY "/target/Vault-0.0.1-SNAPSHOT.jar" app.jar
CMD [ "java", "-jar", "app.jar" ]