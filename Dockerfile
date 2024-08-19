FROM openjdk:17
EXPOSE 8095
COPY "/target/Vault-0.0.1-SNAPSHOT.jar" vault-app.jar
ENV VAULT_ROOT_TOKEN=s.iCibD9ZpNFGXQG5UFqwDro19
CMD [ "java", "-jar", "vault-app.jar" ]