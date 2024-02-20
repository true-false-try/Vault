FROM openjdk:17
EXPOSE 8094
COPY "/target/Vault-0.0.1-SNAPSHOT.jar" vault-app.jar
CMD [ "java", "-jar", "vault-app.jar" ]